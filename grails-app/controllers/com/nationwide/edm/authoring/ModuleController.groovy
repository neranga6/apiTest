package com.nationwide.edm.authoring

import java.util.List;

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

class ModuleController {
	def commentService
	def businessRuleService
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	public SearchType readSearchType(String rawSearchType) {
		SearchType searchType = SearchType.createSearchType(rawSearchType)
		
		if (!searchType) {
			flash.error = message(code: "typeMismatch.searchType", args: [rawSearchType])
			redirect uri: '/'
		}
		
		return searchType
	}
	
	private Module retrieveModuleById(moduleId) {
		def moduleInstance
		
		try {
			moduleInstance = Module.get(moduleId)
		} catch(ex) {
			log.warn("User entered a moduleId that could not be retrieved: \"${moduleId}\"", ex)
		}
		
		return moduleInstance
	}
	
	private List<Module> retrieveModuleByFieldLike(String fieldName, String searchCriteria) {
		def moduleCriteria = Module.createCriteria()
		
		def modules = moduleCriteria.list(params) {
			// the percent symbols are required for the "like" operation
			ilike(fieldName, "%${searchCriteria}%")
		}
		
		return modules
	}
		
    def index() {
        redirect(action: "list", params: params)
    }

	/**
	 * List all Modules.  If the parameter "content" is specified, then we return the modules with that content (case insensitive).
	 */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		SearchType searchType
		def criteria
		
		if(params.searchType) {
			searchType = readSearchType(params.searchType)
			criteria = params.criteria
		}
		
		def modules = []
		int total = 0
		
		if(searchType == SearchType.MODULE_ID && criteria) {
			def moduleId = criteria
			def moduleInstance = retrieveModuleById(moduleId)
			if (!moduleInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), criteria])
				redirect(action: "list")
				return
			}
			
			modules.add(moduleInstance)
			total = 1
		} else if(searchType == SearchType.MODULE_CONTENT && criteria) {
			def content = criteria
			
			modules = retrieveModuleByFieldLike("content", content)
			total = modules.totalCount
		} else {
			modules = Module.list(params)
			total = Module.count
		}
		
        [moduleInstanceList: modules, moduleInstanceTotal: total]
    }
	
    def create() {
        [moduleInstance: new Module(params)]
    }

    def save() {
		
		User currentUser = springSecurityService.getCurrentUser()
		params["lastModBy"]=currentUser
		
		def moduleInstance = new Module(params)
		
		if (moduleInstance.createdBy == null) {
			moduleInstance.createdBy = currentUser
		}		
		moduleInstance.lastModBy = currentUser

		
		if (!moduleInstance.save(flush: true)) {
            render(view: "create", model: [moduleInstance: moduleInstance])
            return
        }
	
		
		if(params.comment){
			addComment(params, moduleInstance, currentUser)
		}
				
		if(params.rule){
			addRule(params, moduleInstance, currentUser)
		}
				
		flash.message = message(code: 'default.created.message', args: [message(code: 'module.label', default: 'Module'), moduleInstance.id])
        redirect(action: "edit", id: moduleInstance.id)
    }

    def show() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        [moduleInstance: moduleInstance]
    }

    def edit() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

		
		//If the User is a Reviewer or Admin, add Final to the status drop down list
		if (SpringSecurityUtils.ifAllGranted("ROLE_REVIEWER")) {			
			moduleInstance.statusValues << "Final"						
		//The User is a Writer
		}else {
			//If the status is Review or Final, set the display to read only on the screen			
			if (moduleInstance.status in ["Review","Final"])
				moduleInstance.statusEnabled = false
		}	

        [moduleInstance: moduleInstance]
    }

    def update() {
		
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (moduleInstance.version > version) {
                moduleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'module.label', default: 'Module')] as Object[],
                          "Another user has updated this Module while you were editing")
                render(view: "edit", model: [moduleInstance: moduleInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
		moduleInstance.properties = params
		moduleInstance.lastModBy = currentUser
		
        if (!moduleInstance.save(flush: true)) {
            render(view: "edit", model: [moduleInstance: moduleInstance])
            return
        }

		if(params.comment){
			addComment(params, moduleInstance, currentUser)
		}
		
		if(params.rule){
			addRule(params, moduleInstance, currentUser)
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'module.label', default: 'Module'), moduleInstance.id])
        redirect(action: "edit", id: moduleInstance.id)
    }

    def delete() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        try {
            moduleInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	/**
	 * This is the "Copy Module" functionality, which clones an existing Module 
	 * into a brand new one.  The Comment items are not copied over.
	 */
	def copy() {
		def moduleInstance = retrieveModuleById(params.id)
		if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
			redirect(action: "list")
			return
		}

		//Create a new instance from the initial 
		def newModuleInstance = cloneMe(moduleInstance)
		newModuleInstance.save(flush:true)

		//Notify the user that the Module has been copied and render the edit view
		flash.message = message(code:'module.copied.message', args:[newModuleInstance.id])
		render(view:"edit",model: [moduleInstance: newModuleInstance])		
	}
	

   /*
    * Clone a module from an existing Module.
    * 
    * Note that copying the collections as a whole will result in a shared collection
    * which GORM and Hibernate will not allow.
    */
   Module cloneMe(Module instanceToClone) {
	   User currentUser = springSecurityService.getCurrentUser()
   		Module newModule = new Module()
		newModule.status = instanceToClone.status
		newModule.content = instanceToClone.content
		newModule.createdBy = currentUser
		newModule.lastModBy = currentUser
		newModule.section = instanceToClone.section
		
		
		// BusinessRules are not shared; they're copied over to the new instance.
		instanceToClone.businessRules?.each{
			newModule.addToBusinessRules(it.getInstance())
		}
		
		// Ingredients are not copied when a module is copied because they should not be associated with a letter
		// 
//		instanceToClone.ingredients?.each {
//			newModule.addToIngredients(it.getInstance())
//		}
		
		// don't clone comments (new Module instance will have no associated comments)
				
		return newModule
   }
	
	def insertImage(){
		
	}
	
	def uploadImage(){
		
	}
	
	private void addComment(Object params, Module moduleInstance, User user){

		def commentInstance = commentService.createComment(params)
		commentInstance.module = moduleInstance
		
		commentInstance.lastModBy = user
		if (commentService.saveComment(commentInstance)) {
			moduleInstance.addToComments(commentInstance)
		}
		
	}
	
	private void addRule(Object params, Module moduleInstance, User user){
		
		def ruleInstance = businessRuleService.createBusinessRule(params)
		ruleInstance.module = moduleInstance
		
		ruleInstance.lastModBy = user
		if (businessRuleService.saveBusinessRule(ruleInstance)) {
			moduleInstance.addToBusinessRules(ruleInstance)
		}
		
	}
	
}
