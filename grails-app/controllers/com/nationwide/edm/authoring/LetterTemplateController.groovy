package com.nationwide.edm.authoring

import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService

import org.springframework.dao.DataIntegrityViolationException

class LetterTemplateController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	LetterTemplateService letterTemplateService
	def springSecurityService
	
	public SearchType readSearchType(String rawSearchType) {
		SearchType searchType = SearchType.createSearchType(rawSearchType)
		
		if (!searchType) {
			flash.error = message(code: "typeMismatch.searchType", args: [rawSearchType])
			redirect uri: '/'
		}
		
		return searchType
	}
	
	def index() {
		redirect(action: "list", params: params)
	}
	
	private LetterTemplate retrieveLetterTemplateById(letterTemplateId) {
		def letterTemplateInstance
		
		try {
			letterTemplateInstance = LetterTemplate.get(letterTemplateId)
		} catch(ex) {
			log.warn("User entered a letterTemplateId that could not be retrieved: \"${letterTemplateId}\"", ex)
		}
		
		return letterTemplateInstance
	}
	
	private List<LetterTemplate> retrieveLetterTemplatesByCategory(String categoryName) {
		def letterTemplateCriteria = LetterTemplate.createCriteria()
		
		def letterTemplates = letterTemplateCriteria.list(params) {
			category {
				// the percent symbols are required for the "like" operation
				ilike("name", "%${categoryName}%")
			}
		}
		
		return letterTemplates
	}
	
	private List<LetterTemplate> retrieveLetterTemplatesByFieldLike(String fieldName, String searchCriteria) {
		def letterTemplateCriteria = LetterTemplate.createCriteria()
		
		def letterTemplates = letterTemplateCriteria.list(params) {
			// the percent symbols are required for the "like" operation
			ilike(fieldName, "%${searchCriteria}%")
		}
		
		return letterTemplates
	}

	/**
	 * List all LetterTemplates.  If the parameter "letterTemplateId" is specified, then we return that LetterTemplate.
	 */
	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		SearchType searchType
		def criteria
		
		if(params.searchType) {
			searchType = readSearchType(params.searchType)
			criteria = params.criteria
		}
		
		List<LetterTemplate> letterTemplates = []
		int total = 0
		
		if(searchType == SearchType.LETTER_TEMPLATE_ID && criteria) {
			
			def letterTemplateId = params.criteria
			def letterTemplateInstance = retrieveLetterTemplateById(letterTemplateId)
			
			if (!letterTemplateInstance) {
				log.debug "${letterTemplateId} not found"
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), letterTemplateId])
				redirect(action: "list")
				return
			}
			
			letterTemplates.add(letterTemplateInstance)
			total = 1
			
		} else if(searchType == SearchType.LETTER_TEMPLATE_NAME && criteria) {
		
			def letterTemplateName = params.criteria
			letterTemplates = retrieveLetterTemplatesByFieldLike("name", letterTemplateName)
			total = letterTemplates.totalCount
			
		} else if(searchType == SearchType.LETTER_TEMPLATE_DESCRIPTION && criteria) {
			
			def letterTemplateDescription = params.criteria
			letterTemplates = retrieveLetterTemplatesByFieldLike("description", letterTemplateDescription)
			total = letterTemplates.totalCount
		
		} else if(searchType == SearchType.LETTER_TEMPLATE_CATEGORY && criteria) {
		
			def letterTemplateCategory = params.criteria
			letterTemplates = retrieveLetterTemplatesByCategory(letterTemplateCategory)
			total = letterTemplates.totalCount
			
		} else {
			letterTemplates = LetterTemplate.list(params)
			total = LetterTemplate.count()
		}
		
		[letterTemplateInstanceList: letterTemplates, letterTemplateInstanceTotal: total]
	}

	def create() {
		
		User currentUser = springSecurityService.getCurrentUser()
	
		LetterTemplate existingTemplate
		LetterTemplate newLetterTemplateInstance
		if(params.letterTemplateGroup == 'existing'){
			
			existingTemplate = LetterTemplate.get(params.modelExistingTemplate)
			
			if(!existingTemplate){
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.modelExistingTemplate])
				redirect(action: "createSelection")
				return
						
			}
			
			//Create a new instance from an existing
			newLetterTemplateInstance = cloneMe(existingTemplate)
			newLetterTemplateInstance.name = params.templateName
			newLetterTemplateInstance.description = params.templateDescription
			Category newCategory = Category.get(params.categoryId)
			if(newCategory){
				newLetterTemplateInstance.category = newCategory
			}
			newLetterTemplateInstance.status = 'Draft'
			
		} else {
			
			//Create a new instance
			newLetterTemplateInstance = new LetterTemplate()
			newLetterTemplateInstance.name = params.templateName
			newLetterTemplateInstance.description = params.templateDescription
			Category newCategory = Category.get(params.categoryId)
			if(newCategory){
				newLetterTemplateInstance.category = newCategory
			}
			Model model = Model.get(params.newModelTemplate)
			if(model){
				newLetterTemplateInstance.model = model
			}
			newLetterTemplateInstance.status = 'Draft'
	
			newLetterTemplateInstance.lastModBy = currentUser
			newLetterTemplateInstance.createdBy = currentUser
		}
							
		if (!newLetterTemplateInstance.save(flush: true)) {
			render(view: "createSelection", model: [letterTemplateInstance: newLetterTemplateInstance])
			return
		}
		
		params.id = newLetterTemplateInstance.id
		
		redirect(action: "editStructure", params: params)
	
	}
	
	private Map parseAndOrderIngredients(LetterTemplate letter) {
		
		Map ingredients = new HashMap()
		def listOfIngredients = letter.recipe
		
		def logoList = new ArrayList()
		def refInfoList = new ArrayList()
		def addressList = new ArrayList()
		def titleList = new ArrayList()
		def bodyList = new ArrayList()
		def closingList = new ArrayList()
		def couponList = new ArrayList()
		def advMessageList = new ArrayList()
		
		for (Ingredient ingredient in listOfIngredients){
			SectionGroup foundGroup = ingredient?.module?.section?.group
			if(foundGroup){
			
				if (foundGroup.groupName == WritersToolConstants.LOGO){
					logoList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.REFERENCE_INFORMATION){
					refInfoList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.ADDRESS){
					addressList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.TITLE){
					titleList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.BODY){
					bodyList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.CLOSING){
					closingList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.COUPON){
					couponList.add(ingredient)
				}
				if (foundGroup.groupName == WritersToolConstants.ADVOCACY_MESSAGE){
					advMessageList.add(ingredient)
				}
			}
		}
		
		logoList.sort{it.sequence}
		refInfoList.sort{it.sequence}
		addressList.sort{it.sequence}
		titleList.sort{it.sequence}
		bodyList.sort{it.sequence}
		closingList.sort{it.sequence}
		couponList.sort{it.sequence}
		advMessageList.sort{it.sequence}
				
		ingredients.put('logoList', logoList)
		ingredients.put('refInfoList', refInfoList)
		ingredients.put('addressList', addressList)
		ingredients.put('titleList', titleList)
		ingredients.put('bodyList', bodyList)
		ingredients.put('closingList', closingList)
		ingredients.put('couponList', couponList)
		ingredients.put('advMessageList', advMessageList)
		
		return ingredients
	
	}
	
	private String determineModelToRender(LetterTemplate letter, String activity){
		
		def modelToRender = ""
	
		if(letter.model?.templateStyle == WritersToolConstants.LEFT_BAR){
			modelToRender = activity + 'LeftBar'
		}
		if(letter.model?.templateStyle == WritersToolConstants.CENTERED){
			modelToRender = activity + 'Centered'
		}
		if(letter.model?.templateStyle == WritersToolConstants.COUPON_TEAROFF){
			modelToRender = activity + 'Coupon'
		}
		
		return modelToRender
		
	}
	
	
	private LetterTemplate cloneMe(LetterTemplate instanceToClone) {
		
		LetterTemplate newLetterTemplate = new LetterTemplate()
		newLetterTemplate.model = instanceToClone.model
		newLetterTemplate.createdBy = instanceToClone.createdBy
		newLetterTemplate.lastModBy = instanceToClone.lastModBy
				
			
		instanceToClone.recipe?.each {
			newLetterTemplate.addToRecipe(it.getInstance())
		}
						
		return newLetterTemplate
   }
	
	//pass through to display createSelection.gsp
	def createSelection() {}
	
	def show() {
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}

		[letterTemplateInstance: letterTemplateInstance]
	}

	def edit() {
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}

		[letterTemplateInstance: letterTemplateInstance]
	}

	def update() {
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (letterTemplateInstance.version > version) {
				letterTemplateInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'letterTemplate.label', default: 'LetterTemplate')] as Object[],
						  "Another user has updated this LetterTemplate while you were editing")
				render(view: "edit", model: [letterTemplateInstance: letterTemplateInstance])
				return
			}
		}

		letterTemplateInstance.properties = params

		if (!letterTemplateInstance.save(flush: true)) {
			render(view: "edit", model: [letterTemplateInstance: letterTemplateInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), letterTemplateInstance.id])
		redirect(action: "review", id: letterTemplateInstance.id)
	}

	def delete() {
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}

		try {
			letterTemplateInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "show", id: params.id)
		}
	}
	
	private getLetterTemplateInstance() {
	
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}
		
		
		def commentInstanceList = letterTemplateInstance.comments?.sort{ a, b ->
			return b.lastUpdated <=> a.lastUpdated
			
		}
		
		def ingredients = parseAndOrderIngredients(letterTemplateInstance)
		
		def sortedModules = []
		
		//The order of these statements is important, it's what controls the order displayed to the user
		//for report.gsp and review.gsp
		
		def logoModules = ingredients.get('logoList')
		if(logoModules){
			sortedModules.addAll(logoModules.module)
		}
		def refInfoModules = ingredients.get('refInfoList')
		if(refInfoModules){
			sortedModules.addAll(refInfoModules.module)
		}
		def addressModules = ingredients.get('addressList')
		if(addressModules){
			sortedModules.addAll(addressModules.module)
		}
		def titleModules = ingredients.get('titleList')
		if(titleModules){
			sortedModules.addAll(titleModules.module)
		}
		def bodyModules = ingredients.get('bodyList')
		if(bodyModules){
			sortedModules.addAll(bodyModules.module)
		}
		def closingModules = ingredients.get('closingList')
		if(closingModules){
			sortedModules.addAll(closingModules.module)
		}
		def couponModules = ingredients.get('couponList')
		if(couponModules){
			sortedModules.addAll(couponModules.module)
		}
		def advMessageModules = ingredients.get('advMessageList')
		if(advMessageModules){
			sortedModules.addAll(advMessageModules.module)
		}
		
				
		[letterTemplateInstance: letterTemplateInstance, commentInstanceList : commentInstanceList, modules : sortedModules]
	}
	
	def preview() {

		getTemplateAndIngredients(params, "preview")

	}
	
	/**
	 * Takes the letter ID and displays the letter edit structure screen
	 */
	def editStructure() {
		
		getTemplateAndIngredients(params, "create")
		
	}
	/**
	 * Takes the letter ID and prompts user to download word doc
	 */
	def exportToWord() {
		
		getTemplateAndIngredients(params, "printView")
		
	}
	
	/**
	 * Review & edit letter
	 */
	def review() {
		getLetterTemplateInstance()
	}
	
	/**
	 * Takes the letter ID and generates a report for it.
	 */
	def report() {
		getLetterTemplateInstance()
	}
	
	def recipe() {
				
		def letter = LetterTemplate.get(params.id)
		def ingredients
		def attachedModules
		def sections
		List sectionDetailList
		def selectedModulesAndSections = new HashMap()
		
		if(letter){
			
			ingredients = parseAndOrderIngredients(letter)
			
			if(params.group == 'body'){
				attachedModules = getModulesFromIngredients(ingredients.get('bodyList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.BODY).sections
				
				
			}
			if(params.group == 'title'){
				attachedModules = getModulesFromIngredients(ingredients.get('titleList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.TITLE).sections
				
				
			}
			if(params.group == 'logo'){
				attachedModules = getModulesFromIngredients(ingredients.get('logoList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.LOGO).sections
				
				
			}
			if(params.group == 'referenceInformation'){
				attachedModules = getModulesFromIngredients(ingredients.get('refInfoList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.REFERENCE_INFORMATION).sections
				
				
			}
			if(params.group == 'advocacy'){
				attachedModules = getModulesFromIngredients(ingredients.get('advMessageList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.ADVOCACY_MESSAGE).sections
				
				
			}
			if(params.group == 'closing'){
				attachedModules = getModulesFromIngredients(ingredients.get('closingList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.CLOSING).sections
				
				
			}
			if(params.group == 'address'){
				attachedModules = getModulesFromIngredients(ingredients.get('addressList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.ADDRESS).sections
				
				
			}
			if(params.group == 'coupon'){
				attachedModules = getModulesFromIngredients(ingredients.get('couponList'))
				sections = SectionGroup.findByGroupName(WritersToolConstants.COUPON).sections
				
				
			}
	
			if(sections){
				
				sectionDetailList = sections.collect{section ->
					
					return [id : section.id, name : section.sectionName]
				}
			}
			
		}
		sectionDetailList.sort{it.id}
	
		def moduleAndSectionList = new HashMap()

		selectedModulesAndSections.put("attachedModules", attachedModules)
		selectedModulesAndSections.put("sections", sectionDetailList)
		
		moduleAndSectionList.put("modulesAndSections", selectedModulesAndSections)
	
		render moduleAndSectionList as JSON
	}
	
	private getTemplateAndIngredients(params, activity){
				
		def letterTemplateInstance = LetterTemplate.get(params.id)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), params.id])
			redirect(action: "list")
			return
		}
		
		Map ingredients = parseAndOrderIngredients(letterTemplateInstance)
		def modelToRender = determineModelToRender(letterTemplateInstance, activity)
		
		if(modelToRender.contains("printView")){
			render(template: modelToRender, model: [letterTemplateInstance: letterTemplateInstance, logoList : ingredients.get('logoList') ,refInfoList : ingredients.get('refInfoList')
										   ,addressList : ingredients.get('addressList'),titleList : ingredients.get('titleList'),bodyList : ingredients.get('bodyList')
										   ,closingList : ingredients.get('closingList'),couponList : ingredients.get('couponList') ,advMessageList : ingredients.get('advMessageList') ])
		}else{				
			render(view: modelToRender, model: [letterTemplateInstance: letterTemplateInstance, logoList : ingredients.get('logoList') ,refInfoList : ingredients.get('refInfoList')
											   ,addressList : ingredients.get('addressList'),titleList : ingredients.get('titleList'),bodyList : ingredients.get('bodyList')
											   ,closingList : ingredients.get('closingList'),couponList : ingredients.get('couponList') ,advMessageList : ingredients.get('advMessageList') ])
		}
	}
	
	private List getModulesFromIngredients(List ingredients){
		
		def ingredientInfo
		
		ingredientInfo = ingredients.collect{ingredient ->
			
			return [ingredientId : ingredient.id, moduleId : ingredient.module?.id, moduleContent : ingredient.module?.content]
		
		}
		return ingredientInfo
	}
	
	def sectionModules(){
	
		def modules
		def moduleList = new HashMap()
		def collectedModuleInfo
		def section  = Section.get(params.sectionId)
		if(section){
			modules = section.modules
			if(modules){
				collectedModuleInfo = modules.collect{module ->
					
					return [id : module.id, content : module.content]
				
				}
				collectedModuleInfo.sort{it.id}
			}
		}
			
		moduleList.put('moduleList', collectedModuleInfo)
		render moduleList as JSON
	}
	
	
	def updateRecipe() {
		render(contentType: "text/json") {
			success = letterTemplateService.updateIngredientSequence(params.recipe)
		}
	}
	
	/**
	 * Delete an Ingredient from a LetterTemplate.
	 */
	def deleteIngredient() {
		render(contentType: "text/json") {
			success = letterTemplateService.deleteIngredient(params.deleteIngredientId, params.recipe)
		}
	}
	
	
	/**
	 * Add an Ingredient to a LetterTemplate.
	 */
	def addIngredient() {

		User currentUser = springSecurityService.getCurrentUser()
		
		render(contentType: "text/json") {
			letterTemplateService.addIngredient(params.letterTemplateId, params.moduleId, currentUser)
		}
	}
	
	/**
	 * Get last 5 letters for display on main screen.
	 */
	def lastFiveLettersAdded(){
						
		def letters = LetterTemplate.list(max: 5, sort: 'id', order: 'desc')
		def letterInfo
		def letterMap = new HashMap()
		
		letterInfo = letters.collect{letterTemplate ->
			
			return [id : letterTemplate.id, name : letterTemplate.name]
		
		}
		
		letterMap.put("letters", letterInfo)
		
		render letterMap as JSON
	}
	
	/**
	 * Add comment to letter templates
	 */
	def addComment(){
		
		//User user = User.findByUsername('welchb4')
		User currentUser = springSecurityService.getCurrentUser()
		
		def status = 404
		def foundLetter = LetterTemplate.get(params.id)
		def newComment = params.comment
		
		if(foundLetter){
			
			if(newComment){
				def comment  = new LetterTemplateComment(comment: newComment, lastModBy : currentUser)
				foundLetter.addToComments(comment).save(flush: true)
				status = 200
			}else{
			// don't want to display an error to the user if they did not provide a comment
				status = 202
			}
		}
		
		if(status == 404){
			log.error("Problem when saving comments to letter ${params.id}")
		}
		
		render(status:status)
	}
	
	def getReport(){
		
		getReportTemplate(params.letterTemplateNumber)
	}
	
	private void getReportTemplate(letterId){
		
		if(!letterId.isLong()){
			flash.message = message(code: 'typeMismatch.java.lang.Long', args: ["{TN#}"])
			redirect(action: "itReport")
			return
		}
		def letterTemplateInstance = LetterTemplate.get(letterId)
		if (!letterTemplateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplate.label', default: 'LetterTemplate'), letterId])
			redirect(action: "itReport")
			return
		}
		
		Map ingredients = parseAndOrderIngredients(letterTemplateInstance)
		def modelToRender = determineModelToRender(letterTemplateInstance, "printView")
		
		render(template: modelToRender, model: [letterTemplateInstance: letterTemplateInstance, logoList : ingredients.get('logoList') ,refInfoList : ingredients.get('refInfoList')
									   ,addressList : ingredients.get('addressList'),titleList : ingredients.get('titleList'),bodyList : ingredients.get('bodyList')
									   ,closingList : ingredients.get('closingList'),couponList : ingredients.get('couponList') ,advMessageList : ingredients.get('advMessageList') ])
	}
}
