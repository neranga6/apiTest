package com.nationwide.edm.authoring

abstract class ControllerTestBase {

	User loggedInUser;
	User setupCreatingUser;
	User setupModifyingUser;
	def springSecurityServiceStub 

	
	def setupSpringSecurityStub() {		
		//Create mock SpringSecurityService
		springSecurityServiceStub = new Object()
		//Override the encodePassword method, which is used by the User object before data is inserted
		springSecurityServiceStub.metaClass.encodePassword = {String password =  "ENCODED_PASSWORD"}
		springSecurityServiceStub.metaClass.getCurrentUser = { ->			
			loggedInUser
		}
		springSecurityServiceStub.metaClass.toString = {
			"mockedSpringSecurityService.toString()"
		}			
		
	}
	
	def setupUsers() {
		//Create users for testing
		Role adminRole = ConstraintUnitSpec.generateRole(WritersToolConstants.ADMIN_ROLE)
		loggedInUser = ConstraintUnitSpec.generateUser("wolverine")
		setupCreatingUser = ConstraintUnitSpec.generateUser("cyclops")
		setupModifyingUser = ConstraintUnitSpec.generateUser("gambit")
	}

}
