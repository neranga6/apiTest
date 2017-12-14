package com.authoring

import grails.test.mixin.*

import org.junit.*

@TestFor(ModuleController)
@Mock([Module, User, Section, SectionGroup, CommentService, Comment, BusinessRuleService, BusinessRule])
class ModuleControllerTests extends ControllerTestBase {
	
	@Before
	void setupTest() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
	}

	def populateValidParams(params) {
		  assert params != null
		  
		  params["content"] = 'Thank you for contacting us.  We will get back to your shortly'
		  params["status"] = 'Draft'

		  SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1,lastModBy: setupCreatingUser)
		
		  Section introSection = new Section(sectionName:"First Section",sequence: 1, group: logoGroup, lastModBy: setupCreatingUser)
		 
		  params["section"] = introSection
		  
	}	

    void testIndex() {
        controller.index()
        assert "/module/list" == response.redirectedUrl
    }

    void testList() {
		controller.springSecurityService = springSecurityServiceStub

        def model = controller.list()

        assert model.moduleInstanceList.size() == 0
        assert model.moduleInstanceTotal == 0
    }
	
	void testListSearchContent() {
		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
		
		params.searchType = "MODULE_CONTENT"
		params.criteria = "Some"
		def model = controller.list()

		assert model.moduleInstanceList.size() == 1
		assert model.moduleInstanceTotal == 1
		assert model.moduleInstanceList[0].content == "some module"
	}
	
	void testListSearchContentNoMatches() {
		ConstraintUnitSpec.createSampleModule()
		
		params.searchType = "MODULE_CONTENT"
		params.criteria = "AAABBBCCC"
		def model = controller.list()

		assert model.moduleInstanceList.size() == 0
		assert model.moduleInstanceTotal == 0
	}
	
	void testListSearchById() {
		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
		
		params.searchType = "MODULE_ID"
		params.criteria = "1"
		def model = controller.list()

		assert model.moduleInstanceList.size() == 1
		assert model.moduleInstanceTotal == 1
		assert model.moduleInstanceList[0].content == "some module"
	}
	
	void testListSearchByIdNotFound() {
		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
		
		params.searchType = "MODULE_ID"
		params.criteria = "50"
		controller.list()

		assert "/module/list" == response.redirectedUrl
	}

    void testCreate() {
		
       def model = controller.create()

       assert model.moduleInstance != null
    }

    void testSave() {
		
		controller.save()

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/module/edit/1'
        assert controller.flash.message != null
        assert Module.count() == 1
		
		def module = Module.get(1)
		assert module.lastModBy.username == loggedInUser.username
    }
	
	void testSaveWithCommentAdded(){
		
		controller.save()
		response.reset()
		populateValidParams(params)
		
		params["comment"] = 'This is just some test comment to make sure saving a comment works'
		
		controller.save()
		
		assert response.redirectedUrl == '/module/edit/1'
		assert controller.flash.message != null
		assert Module.count() == 1
		 
		def module = Module.get(1)
		assert module.comments != null
			
		
	}
	
	void testSaveWithBusinessRuleAdded(){
		
		controller.save()
		response.reset()
		populateValidParams(params)
		
		params["rule"] = 'X-men rule'
		
		controller.save()
		
		assert response.redirectedUrl == '/module/edit/1'
		assert controller.flash.message != null
		assert Module.count() == 1
		 
		def module = Module.get(1)
		assert module.businessRules != null
			
		
	}

    void testShow() {
		
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/module/list'


        populateValidParams(params)

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		
        def module = new Module(params)

        assert module.save() != null

        params.id = module.id

        def model = controller.show()

        assert model.moduleInstance == module
    }

    //TODO:  Fix this test!  Skipping this test because we can't mock Spring Security yet
	@Ignore
	void testEdit() {
		
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/module/list'


        populateValidParams(params)

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		
        def module = new Module(params)

        assert module.save() != null

        params.id = module.id

        def model = controller.edit()

        assert model.moduleInstance == module
    }
	
	void testEditInvalidId() {
		def sampleModule = ConstraintUnitSpec.createSampleModule()
		
		params.id = "aaa"
		def model = controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/module/list'
	}

    void testUpdate() {
		
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/module/list'

        response.reset()

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		
        populateValidParams(params)
        def module = new Module(params)
		
        assert module.save() != null

        // test invalid parameters in update
        params.id = module.id
        params.status = "Bad Status"

        controller.update()

        assert view == "/module/edit"
        assert model.moduleInstance != null

        module.clearErrors()

        populateValidParams(params)
		params["comment"] = 'This is just some test comment to make sure saving a comment works'
		params["rule"] = 'X-men rock!!!'
        controller.update()

        assert response.redirectedUrl == "/module/edit/$module.id"
        assert flash.message != null
		assert model.moduleInstance.comments != null
		assert model.moduleInstance.businessRules != null
		assert model.moduleInstance.lastModBy.username == loggedInUser.username

        //test outdated version number
        response.reset()
        module.clearErrors()

        populateValidParams(params)
        params.id = module.id
        params.version = -1
		
        controller.update()

        assert view == "/module/edit"
        assert model.moduleInstance != null
        assert model.moduleInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
		
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/module/list'

        response.reset()

		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
        populateValidParams(params)
        def module = new Module(params)

        assert module.save() != null
        assert Module.count() == 1

        params.id = module.id

        controller.delete()

        assert Module.count() == 0
        assert Module.get(module.id) == null
        assert response.redirectedUrl == '/module/list'
    }
	
	void testCopy() {
		
		//Check if record to copy is not found
		controller.copy()
		assert flash.message != null
		assert response.redirectedUrl == '/module/list'
		
		response.reset()
		
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		
		populateValidParams(params)
		def originalModule = new Module(params)
		assert originalModule.save() != null
		assert Module.count() == 1
		
		params.id = originalModule.id		
		def model = controller.copy()
		assert view == "/module/edit"
		
		def editedModel = controller.modelAndView.model.moduleInstance
		assert editedModel.id != originalModule.id
		assert editedModel.lastModBy.username == loggedInUser.username
		assert controller.flash.message != null
		assert Module.count() == 2
		
	}
}
