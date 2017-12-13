package com.nationwide.edm.authoring

import org.junit.*

import grails.test.mixin.*

@TestFor(BusinessRuleController)
@Mock([BusinessRule, BusinessRuleService])
class BusinessRuleControllerTests extends ControllerTestBase{
	
	@Before
	void setupTest() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
	}
	
    def populateValidParams(params) {
        assert params != null
        params["rule"] = 'Some business rule'
        //params["lastModBy"] = new User(name:'cyclops')
        params["module"] = new Module(content:'some content')
    }

    void testIndex() {
        controller.index()
        assert "/businessRule/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.businessRuleInstanceList.size() == 0
        assert model.businessRuleInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.businessRuleInstance != null
    }

    void testSave() {
        controller.save()

        assert model.businessRuleInstance != null
        assert view == '/businessRule/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/businessRule/show/1'
        assert controller.flash.message != null
        assert BusinessRule.count() == 1
		
		def businessRule = BusinessRule.get(1)
		assert businessRule.lastModBy.username == loggedInUser.username
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/businessRule/list'

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser

        populateValidParams(params)
        def businessRule = new BusinessRule(params)

        assert businessRule.save() != null

        params.id = businessRule.id

        def model = controller.show()

        assert model.businessRuleInstance == businessRule
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/businessRule/list'

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser

        populateValidParams(params)
        def businessRule = new BusinessRule(params)

        assert businessRule.save() != null

        params.id = businessRule.id

        def model = controller.edit()

        assert model.businessRuleInstance == businessRule
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/businessRule/list'

        response.reset()

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser

        populateValidParams(params)
        def businessRule = new BusinessRule(params)

        assert businessRule.save() != null

        // test invalid parameters in update
        params.id = businessRule.id
        params.rule = ""

        controller.update()

        assert view == "/businessRule/edit"
        assert model.businessRuleInstance != null

        businessRule.clearErrors()

        populateValidParams(params)
		params.rule =  "Modified rule"
		
        controller.update()

        assert response.redirectedUrl == "/businessRule/list"
        assert flash.message != null
		assert model.businessRuleInstance.lastModBy.username == loggedInUser.username

        //test outdated version number
        response.reset()
        businessRule.clearErrors()

        populateValidParams(params)
        params.id = businessRule.id
        params.version = -1
        controller.update()

        assert view == "/businessRule/edit"
        assert model.businessRuleInstance != null
        assert model.businessRuleInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/businessRule/list'

        response.reset()
		
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser

        populateValidParams(params)
        def businessRule = new BusinessRule(params)

        assert businessRule.save() != null
        assert BusinessRule.count() == 1

        params.id = businessRule.id

        controller.delete()

        assert BusinessRule.count() == 0
        assert BusinessRule.get(businessRule.id) == null
        assert response.redirectedUrl == '/businessRule/list'
    }
}
