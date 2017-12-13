package com.nationwide.edm.authoring

import org.springframework.dao.DataIntegrityViolationException

class BusinessRuleService {

    def createBusinessRule(params) {
		new BusinessRule(params)
    }
	
	def saveBusinessRule(BusinessRule businessRuleInstance) {
		return businessRuleInstance.save(flush: true)
	}
	
	/**
	 * Delete a BusinessRule instance and return whether the operation was successful or not.
	 */
	boolean deleteBusinessRule(BusinessRule businessRuleInstance) {
		boolean deleteSuccess = false
		try {
			businessRuleInstance.delete(flush: true)
			deleteSuccess = true
		} catch (DataIntegrityViolationException e) {
			log.error("There was an error when deleting BusinessRule with ID ${businessRuleInstance.id}", e)
			deleteSuccess = false
		}
		return deleteSuccess
	}
}
