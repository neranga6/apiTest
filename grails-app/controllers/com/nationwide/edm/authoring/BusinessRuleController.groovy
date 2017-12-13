package com.nationwide.edm.authoring

class BusinessRuleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def businessRuleService
	def springSecurityService
	
	private void setFlashMessage(code, id) {
		flash.message = message(code: code, args: [message(code: 'businessRule.label', default: 'BusinessRule'), id])
	}
	
	private BusinessRule getBusinessRule(id) {
		def businessRule = BusinessRule.get(id)
		if (!businessRule) {
			setFlashMessage('default.not.found.message', id)
			redirect(action: "list")
		}
		businessRule
	}

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [businessRuleInstanceList: BusinessRule.list(params), businessRuleInstanceTotal: BusinessRule.count()]
    }
	
	/**
	 * Get all the business rules for one module.
	 */
	def listForModule() {
		def moduleInstance = Module.get(params.id)
		if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
			redirect(action: "list")
			return
		}
		
		def businessRuleInstanceList = moduleInstance.businessRules?.sort{
			it.lastUpdated
		}
		
		[businessRuleInstanceList: businessRuleInstanceList, businessRuleInstanceTotal: businessRuleInstanceList?.size() ?: 0]
	}

    def create() {
        [businessRuleInstance: businessRuleService.createBusinessRule(params)]
    }

    def save() {
		
		User currentUser = springSecurityService.getCurrentUser()
		params["lastModBy"]=currentUser
		
        def businessRuleInstance = businessRuleService.createBusinessRule(params)
        if (!businessRuleService.saveBusinessRule(businessRuleInstance)) {
            render(view: "create", model: [businessRuleInstance: businessRuleInstance])
            return
        }

		setFlashMessage('default.created.message', businessRuleInstance.id)
        redirect(action: "show", id: businessRuleInstance.id)
    }

    def show() {
        def businessRuleInstance = getBusinessRule(params.id)
        if (!businessRuleInstance) {
            return
        }

        [businessRuleInstance: businessRuleInstance]
    }

    def edit() {
        def businessRuleInstance = getBusinessRule(params.id)
        if (!businessRuleInstance) {
            return
        }

        [businessRuleInstance: businessRuleInstance]
    }

    def update() {
        def businessRuleInstance = getBusinessRule(params.id)
        if (!businessRuleInstance) {
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (businessRuleInstance.version > version) {
                businessRuleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'businessRule.label', default: 'BusinessRule')] as Object[],
                          "Another user has updated this BusinessRule while you were editing")
                render(view: "edit", model: [businessRuleInstance: businessRuleInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
		businessRuleInstance.properties = params
		businessRuleInstance.lastModBy = currentUser     

        if (!businessRuleService.saveBusinessRule(businessRuleInstance)) {
            render(view: "edit", model: [businessRuleInstance: businessRuleInstance])
            return
        }

		setFlashMessage('businessRuleUpdate.label', businessRuleInstance.module?.id)
        redirect(action: "list")
    }
	
    def delete() {
        def businessRuleInstance = getBusinessRule(params.id)
        if (!businessRuleInstance) {
            return
        }

		if(businessRuleService.deleteBusinessRule(businessRuleInstance)) {
			setFlashMessage('default.deleted.message', params.id)
			redirect(action: "list")
		} else {
			setFlashMessage('default.not.deleted.message', params.id)
			redirect(action: "show", id: params.id)
		}
    }
}
