package com.nationwide.edm.authoring

import org.junit.*
import grails.test.mixin.*

@TestFor(SectionController)
@Mock(Section)
class SectionControllerTests extends ControllerTestBase{
	
	@Before
	void setupTest() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
	}

    def populateValidParams(params) {
      assert params != null
	  params["group"] = new SectionGroup(groupName:"Advocacy Message", sequence: 1)
	  params["lastModBy"] = new User(name:'cyclops')
	  params["sequence"] = 3
      params["sectionName"] = "Tip"
    }

    void testIndex() {
        controller.index()
        assert "/section/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.sectionInstanceList.size() == 0
        assert model.sectionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.sectionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.sectionInstance != null
        assert view == '/section/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/section/show/1'
        assert controller.flash.message != null
        assert Section.count() == 1
		
		def section	= Section.get(1)
		assert section.lastModBy.username == loggedInUser.username
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/section/list'


        populateValidParams(params)
        def section = new Section(params)

        assert section.save() != null

        params.id = section.id

        def model = controller.show()

        assert model.sectionInstance == section
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/section/list'


        populateValidParams(params)
        def section = new Section(params)

        assert section.save() != null

        params.id = section.id

        def model = controller.edit()

        assert model.sectionInstance == section
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/section/list'

        response.reset()


        populateValidParams(params)
        def section = new Section(params)

        assert section.save() != null

        // test invalid parameters in update
        params.id = section.id
		params["sectionName"] = ""

        controller.update()

        assert view == "/section/edit"
        assert model.sectionInstance != null

        section.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/section/list"
        assert flash.message != null
		assert model.sectionInstance.lastModBy.username == loggedInUser.username

        //test outdated version number
        response.reset()
        section.clearErrors()

        populateValidParams(params)
        params.id = section.id
        params.version = -1
        controller.update()

        assert view == "/section/edit"
        assert model.sectionInstance != null
        assert model.sectionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/section/list'

        response.reset()

        populateValidParams(params)
        def section = new Section(params)

        assert section.save() != null
        assert Section.count() == 1

        params.id = section.id

        controller.delete()

        assert Section.count() == 0
        assert Section.get(section.id) == null
        assert response.redirectedUrl == '/section/list'
    }
}
