package com.authoring



import org.junit.*
import grails.test.mixin.*

@TestFor(IngredientController)
@Mock([Ingredient, LetterTemplate])
class IngredientControllerTests extends ControllerTestBase{
	
	@Before
	void setupTest() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
	}


    def populateValidParams(params) {
      assert params != null
	  
	  params["sequence"] = 3
	  params["comment"] = "Just Some Comment"
	  params["lastModBy"] = setupModifyingUser
	  params["module"] = new Module(content:'some content')
	  params["letter"] = new LetterTemplate(name:'some letter name')
    }

    void testIndex() {
        controller.index()
        assert "/ingredient/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ingredientInstanceList.size() == 0
        assert model.ingredientInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.ingredientInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ingredientInstance != null
        assert view == '/ingredient/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/ingredient/show/1'
        assert controller.flash.message != null
        assert Ingredient.count() == 1
		
		def ingredient = Ingredient.get(1)
		assert ingredient.lastModBy.username == loggedInUser.username
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/ingredient/list'


        populateValidParams(params)
        def ingredient = new Ingredient(params)

        assert ingredient.save() != null

        params.id = ingredient.id

        def model = controller.show()

        assert model.ingredientInstance == ingredient
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/ingredient/list'


        populateValidParams(params)
        def ingredient = new Ingredient(params)

        assert ingredient.save() != null

        params.id = ingredient.id

        def model = controller.edit()

        assert model.ingredientInstance == ingredient
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/ingredient/list'

        response.reset()


        populateValidParams(params)
        def ingredient = new Ingredient(params)

        assert ingredient.save() != null

        // test invalid parameters in update
        params.id = ingredient.id
        //TODO: add invalid values to params object
		params.module = null

        controller.update()

        assert view == "/ingredient/edit"
        assert model.ingredientInstance != null

        ingredient.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/ingredient/show/$ingredient.id"
        assert flash.message != null
		assert model.ingredientInstance.lastModBy.username == loggedInUser.username

        //test outdated version number
        response.reset()
        ingredient.clearErrors()

        populateValidParams(params)
        params.id = ingredient.id
        params.version = -1
        controller.update()

        assert view == "/ingredient/edit"
        assert model.ingredientInstance != null
        assert model.ingredientInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/ingredient/list'

        response.reset()

        populateValidParams(params)
        def ingredient = new Ingredient(params)

        assert ingredient.save() != null
        assert Ingredient.count() == 1

        params.id = ingredient.id

        controller.delete()

        assert Ingredient.count() == 0
        assert Ingredient.get(ingredient.id) == null
        assert response.redirectedUrl == '/ingredient/list'
    }
}
