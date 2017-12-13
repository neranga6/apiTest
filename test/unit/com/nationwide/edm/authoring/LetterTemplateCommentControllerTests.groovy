package com.nationwide.edm.authoring



import grails.test.mixin.*

import org.junit.*


import org.junit.*
import grails.test.mixin.*

@TestFor(LetterTemplateCommentController)
@Mock([LetterTemplateComment, User, LetterTemplate])
class LetterTemplateCommentControllerTests extends ControllerTestBase{
	
	String comment
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
	@Before
	void setupTest() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
	}

    def populateValidParams(params) {
      assert params != null
      params["comment"] = 'some comment'
      params["lastModBy"] = setupModifyingUser
      params["letterTemplate"] = new LetterTemplate(name:'Some letter template')
    }

    void testIndex() {
        controller.index()
        assert "/letterTemplateComment/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.letterTemplateCommentInstanceList.size() == 0
        assert model.letterTemplateCommentInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.letterTemplateCommentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.letterTemplateCommentInstance != null
        assert view == '/letterTemplateComment/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/letterTemplateComment/show/1'
        assert controller.flash.message != null
        assert LetterTemplateComment.count() == 1
		
		def letterTemplateComment = LetterTemplateComment.get(1)
		assert letterTemplateComment.lastModBy.username == loggedInUser.username
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplateComment/list'


        populateValidParams(params)
        def letterTemplateComment = new LetterTemplateComment(params)

        assert letterTemplateComment.save() != null

        params.id = letterTemplateComment.id

        def model = controller.show()

        assert model.letterTemplateCommentInstance == letterTemplateComment
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplateComment/list'


        populateValidParams(params)
        def letterTemplateComment = new LetterTemplateComment(params)

        assert letterTemplateComment.save() != null

        params.id = letterTemplateComment.id

        def model = controller.edit()

        assert model.letterTemplateCommentInstance == letterTemplateComment
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplateComment/list'

        response.reset()


        populateValidParams(params)
        def letterTemplateComment = new LetterTemplateComment(params)

        assert letterTemplateComment.save() != null

        // test invalid parameters in update
        params.id = letterTemplateComment.id
        //TODO: add invalid values to params object
		params.comment = ""

        controller.update()

        assert view == "/letterTemplateComment/edit"
        assert model.letterTemplateCommentInstance != null

        letterTemplateComment.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/letterTemplateComment/list"
        assert flash.message != null
		assert model.letterTemplateCommentInstance.lastModBy.username == loggedInUser.username
		

        //test outdated version number
        response.reset()
        letterTemplateComment.clearErrors()

        populateValidParams(params)
        params.id = letterTemplateComment.id
        params.version = -1
        controller.update()

        assert view == "/letterTemplateComment/edit"
        assert model.letterTemplateCommentInstance != null
        assert model.letterTemplateCommentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplateComment/list'

        response.reset()

        populateValidParams(params)
        def letterTemplateComment = new LetterTemplateComment(params)

        assert letterTemplateComment.save() != null
        assert LetterTemplateComment.count() == 1

        params.id = letterTemplateComment.id

        controller.delete()

        assert LetterTemplateComment.count() == 0
        assert LetterTemplateComment.get(letterTemplateComment.id) == null
        assert response.redirectedUrl == '/letterTemplateComment/list'
    }
}
