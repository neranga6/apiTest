package com.authoring

import grails.test.mixin.*

import org.junit.*
import org.springframework.dao.DataIntegrityViolationException

@TestFor(LetterTemplateController)
@Mock([LetterTemplate, Category, Model, Role, User, Module, Section, SectionGroup, Comment, Ingredient])
class LetterTemplateControllerTests extends ControllerTestBase{
			
	@Before
	void setup() {
		setupUsers()
		setupSpringSecurityStub()
		controller.springSecurityService = springSecurityServiceStub
		controller.letterTemplateService = new LetterTemplateService()
	}
	
    def populateValidParams(params) {
      assert params != null
      params["name"] = 'Recommendation'
      params["description"] = 'This is a valid letter of recommendation'
      params["category"] = new Category(name:"Car Repair")
      params["status"] = 'Draft'
      params["model"] = new Model(templateStyle : "Left Nav", imageLocation:"C://Images")
	  params["createdBy"] = setupCreatingUser
	  params["lastModBy"] = setupModifyingUser
    }

    void testIndex() {
        controller.index()
        assert "/letterTemplate/list" == response.redirectedUrl
    }

    void testListNoLetterTemplates() {
        def model = controller.list()

        assert model.letterTemplateInstanceList.size() == 0
        assert model.letterTemplateInstanceTotal == 0
    }
	
	void testListWithThreeLetterTemplates() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second letter")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "third letter")
		
		def model = controller.list()

		assert model.letterTemplateInstanceList.size() == 3
		assert model.letterTemplateInstanceTotal == 3
	}

	void testListByLetterTemplateId() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second letter")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "third letter")
		
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "3"
		def model = controller.list()

		assert model.letterTemplateInstanceList.size() == 1
		assert model.letterTemplateInstanceTotal == 1
		assert model.letterTemplateInstanceList[0].name == "third letter"
	}
	
	void testListByLetterTemplateIdNotFound() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "3"
		def model = controller.list()

		assert response.redirectedUrl == '/letterTemplate/list'
	}
	
	void testListByLetterTemplateIdWrongIdDataType() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "abcde"
		def model = controller.list()

		assert response.redirectedUrl == '/letterTemplate/list'
	}
	
	void testListByLetterTemplateName() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter")
		
		params.searchType = "LETTER_TEMPLATE_NAME"
		params.criteria = "Letter"
		def model = controller.list()

		assert model.letterTemplateInstanceList.size() == 2
		assert model.letterTemplateInstanceTotal == 2
		assert model.letterTemplateInstanceList[0].name == "first letter"
		assert model.letterTemplateInstanceList[1].name == "my third letter"
	}
	
	void testListByLetterTemplateDescription() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter", "claims IT", "payment")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one", "billing", "bill form")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter", "sales", "quote")
		ConstraintUnitSpec.createSampleLetterTemplate("nightcrawler", "a 4th letter", "claims business", "CSR form")
		
		params.searchType = "LETTER_TEMPLATE_DESCRIPTION"
		params.criteria = "Form"
		def model = controller.list()

		assert model.letterTemplateInstanceList.size() == 2
		assert model.letterTemplateInstanceTotal == 2
		assert model.letterTemplateInstanceList[0].name == "second one"
		assert model.letterTemplateInstanceList[1].name == "a 4th letter"
	}
	
	void testListByLetterTemplateCategoryName() {
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter", "claims IT")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one", "billing")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter", "sales")
		ConstraintUnitSpec.createSampleLetterTemplate("nightcrawler", "a 4th letter", "claims business")
		
		params.searchType = "LETTER_TEMPLATE_CATEGORY"
		params.criteria = "Claims"
		def model = controller.list()

		assert model.letterTemplateInstanceList.size() == 2
		assert model.letterTemplateInstanceTotal == 2
		assert model.letterTemplateInstanceList[0].name == "first letter"
		assert model.letterTemplateInstanceList[1].name == "a 4th letter"
	}
	
    void testCreateFromExisting() {
	   
	   Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
		
	   populateValidParams(params)
	   def existingLetterTemplate = new LetterTemplate(params)
	   def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one')
	   def ingredient2 = new Ingredient(sequence : 2, comment : 'Comment two')
	   existingLetterTemplate.addToRecipe(ingredient1)
	   existingLetterTemplate.addToRecipe(ingredient2)
	   existingLetterTemplate.model = modelTemplate
	   
	   assert existingLetterTemplate.save() != null
	   
	   Category selectedCategory = new Category(name : 'Some Category', lastModBy : setupModifyingUser).save()
	   selectedCategory.save()
	 	 
	   params["letterTemplateGroup"] = 'existing'
	   params["modelExistingTemplate"] = '1'
	   params["templateName"] = 'Uber Template'
	   params["templateDescription"] = 'Some description'
	   params["categoryId"] = '1'
	 
       def model = controller.create()
	 
	   assert response.redirectedUrl == '/letterTemplate/editStructure/2?name=Recommendation&description=This+is+a+valid+letter+of+recommendation&category=Car+Repair&status=Draft&model=Left+Nav&createdBy=cyclops&lastModBy=gambit&letterTemplateGroup=existing&modelExistingTemplate=1&templateName=Uber+Template&templateDescription=Some+description&categoryId=1'
    }
	
	void testCreateNew() {
		
		Category selectedCategory = new Category(name : 'Some Category', lastModBy : setupModifyingUser).save()
		selectedCategory.save()
		
		Model modelTemplate = new Model(id: 1, templateStyle : 'Left Bar', imageLocation : 'My hard drive').save()
		
		populateValidParams(params)
		params["letterTemplateGroup"] = 'new'
		params["modelExistingTemplate"] = '1'
		params["templateName"] = 'Uber Template'
		params["templateDescription"] = 'Some description'
		params["categoryId"] = '1'
		params["newModelTemplate"] = '1'
		def model = controller.create()
 
		assert response.redirectedUrl == '/letterTemplate/editStructure/1?name=Recommendation&description=This+is+a+valid+letter+of+recommendation&category=Car+Repair&status=Draft&model=Left+Nav&createdBy=cyclops&lastModBy=gambit&letterTemplateGroup=new&modelExistingTemplate=1&templateName=Uber+Template&templateDescription=Some+description&categoryId=1&newModelTemplate=1'
	 }
	
	void testRecipe(){
		
		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
		 
		populateValidParams(params)
		def existingLetterTemplate = new LetterTemplate(params)
	
		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
		def bodyGroup = new SectionGroup(groupName : "Body", sequence : 1, lastModBy : setupModifyingUser).save()
		
		assert logoGroup != null
		assert bodyGroup != null
		
		String sectionName
		Integer sequence
		User lastModBy
		def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
		def bodySection = new Section(sectionName : "Some other section", sequence : 1, lastModyBy : setupModifyingUser, group : bodyGroup).save()
				
		def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
		def module2 = new Module(content: "Some other plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : bodySection).save()
		
		
		def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1)
		def ingredient2 = new Ingredient(sequence : 2, comment : 'Comment two', module : module2)
		existingLetterTemplate.addToRecipe(ingredient1)
		existingLetterTemplate.addToRecipe(ingredient2)
		existingLetterTemplate.model = modelTemplate
		existingLetterTemplate.createdBy = setupCreatingUser
		existingLetterTemplate.lastModBy = setupModifyingUser
			
		assert existingLetterTemplate.save() != null
		
		params["id"] = '1'
		params["group"] = 'logo'
				
		def model = controller.recipe()
		
		assert response.text == '{"modulesAndSections":{"sections":[{"id":1,"name":"Some section"}],"attachedModules":[{"ingredientId":1,"moduleId":1,"moduleContent":"Some plain text content"}]}}'
	}
	
	void testPreview(){
		
		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
		 
		populateValidParams(params)
		def existingLetterTemplate = new LetterTemplate(params)
	
		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
	
		
		String sectionName
		Integer sequence
		User lastModBy
		def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
						
		def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
			
		assert module1 != null
			
		existingLetterTemplate.createdBy = setupCreatingUser
		existingLetterTemplate.lastModBy = setupModifyingUser
		existingLetterTemplate.model = modelTemplate
		existingLetterTemplate.save()
		def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1, lastModBy : setupModifyingUser, letter : existingLetterTemplate).save()
			
		existingLetterTemplate.addToRecipe(ingredient1).save() != null
	
				
		params["id"] = '1'
		
		controller.preview()
		assert controller.modelAndView.getViewName() == '/letterTemplate/previewLeftBar'
		assert controller.modelAndView.model.letterTemplateInstance != null
		assert controller.modelAndView.model.logoList.size() == 1
	
	}
	
	void testEditStructure(){
		
		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
		 
		populateValidParams(params)
		def existingLetterTemplate = new LetterTemplate(params)
	
		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
	
		
		String sectionName
		Integer sequence
		User lastModBy
		def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
						
		def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
			
		assert module1 != null
			
		existingLetterTemplate.createdBy = setupCreatingUser
		existingLetterTemplate.lastModBy = setupModifyingUser
		existingLetterTemplate.model = modelTemplate
		existingLetterTemplate.save()
		def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1, lastModBy : setupModifyingUser, letter : existingLetterTemplate).save()
			
		existingLetterTemplate.addToRecipe(ingredient1).save() != null
	
				
		params["id"] = '1'
		
		controller.editStructure()
		assert controller.modelAndView.getViewName() == '/letterTemplate/createLeftBar'
		assert controller.modelAndView.model.letterTemplateInstance != null
		assert controller.modelAndView.model.logoList.size() == 1
	
	}


    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplate/list'


        populateValidParams(params)
        def letterTemplate = new LetterTemplate(params)

        assert letterTemplate.save() != null

        params.id = letterTemplate.id

        def model = controller.show()

        assert model.letterTemplateInstance == letterTemplate
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplate/list'

        populateValidParams(params)
        def letterTemplate = new LetterTemplate(params)

        assert letterTemplate.save() != null

        params.id = letterTemplate.id

        def model = controller.edit()

        assert model.letterTemplateInstance == letterTemplate
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplate/list'

        response.reset()


        populateValidParams(params)
        def letterTemplate = new LetterTemplate(params)

        assert letterTemplate.save() != null

        // test invalid parameters in update
        params.id = letterTemplate.id
        params["status"] = "blah"

        controller.update()

        assert view == "/letterTemplate/edit"
        assert model.letterTemplateInstance != null

        letterTemplate.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/letterTemplate/review/$letterTemplate.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        letterTemplate.clearErrors()

        populateValidParams(params)
        params.id = letterTemplate.id
        params.version = -1
        controller.update()

        assert view == "/letterTemplate/edit"
        assert model.letterTemplateInstance != null
        assert model.letterTemplateInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/letterTemplate/list'

        response.reset()

        populateValidParams(params)
        def letterTemplate = new LetterTemplate(params)

        assert letterTemplate.save() != null
        assert LetterTemplate.count() == 1

        params.id = letterTemplate.id

        controller.delete()

        assert LetterTemplate.count() == 0
        assert LetterTemplate.get(letterTemplate.id) == null
        assert response.redirectedUrl == '/letterTemplate/list'
    }
	
	void testUpdateRecipeOrder() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		params.recipe = "3,1,2"
		controller.updateRecipe()
		
		assert response.text == '{"success":true}'
		assert Ingredient.get(3).sequence == 1
		assert Ingredient.get(1).sequence == 2
		assert Ingredient.get(2).sequence == 3
	}
	
	void testRecipeOrderNotChangedWhenIngredientIdIsInvalid() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		params.recipe = "3,1,500,2"
		
		controller.updateRecipe()
		
		assert response.text == '{"success":false}'
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2).sequence == 2
		assert Ingredient.get(3).sequence == 3
	}
	
	void testUpdateRecipeReturnsFalseAfterDatabaseException() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		params.recipe = "3,1,2"
		
		// force a save of IngredientId #2 to throw an exception
		Ingredient.get(2).metaClass.save { Map args ->
			throw new DataIntegrityViolationException("something terrible happened")
		}
		
		controller.updateRecipe()
		
		assert response.text == '{"success":false}'
	}
	
	/**
	 * Here we have 3 ingredients, remove the middle one, and make sure the sequence is updated correctly.
	 */
	void testDeleteIngredientMiddle() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		params.deleteIngredientId = "2"
		params.recipe = "1,3"
		
		assert LetterTemplate.get(1).recipe.size() == 3
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2).sequence == 2
		assert Ingredient.get(3).sequence == 3
		
		controller.deleteIngredient()
		
		// the 2nd item is now gone, and the 3rd has its sequence updated
		assert response.text == '{"success":true}'
		assert LetterTemplate.get(1).recipe.size() == 2
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2) == null
		assert Ingredient.get(3).sequence == 2
	}
	
	/**
	 * Here we have 1 ingredient that we remove, and make sure that the sequence is empty.
	 */
	void testDeleteLastIngredient() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		
		// adjust the test data to have only one recipe
		Ingredient ingredient = Ingredient.get(2)
		ingredient.delete(flush: true)

		ingredient = Ingredient.get(3)
		ingredient.delete(flush: true)
		
		assert LetterTemplate.get(1).recipe.size() == 1
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2) == null
		assert Ingredient.get(3) == null

		params.deleteIngredientId = "1"
		params.recipe = ""
		
		controller.deleteIngredient()
		
		assert response.text == '{"success":true}'
		assert LetterTemplate.get(1).recipe.size() == 0
		assert Ingredient.get(1) == null
	}
	
	/**
	 * Here we have 3 ingredients, try and fail to remove the middle one, and make sure nothing is changed. 
	 */
	void testRollbackIngredientAfterDatabaseException() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		params.deleteIngredientId = "2"
		params.recipe = "1,3"
		
		// force a delete to throw an exception
		Ingredient.get(2).metaClass.delete { Map args ->
			throw new DataIntegrityViolationException("something terrible happened")
		}
		
		assert LetterTemplate.get(1).recipe.size() == 3
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2).sequence == 2
		assert Ingredient.get(3).sequence == 3
		
		controller.deleteIngredient()
		
		// the 2nd item is still there and recipe should be unchanged
		assert response.text == '{"success":false}'
		assert LetterTemplate.get(1).recipe.size() == 3
		assert Ingredient.get(1).sequence == 1
		assert Ingredient.get(2).sequence == 2
		assert Ingredient.get(3).sequence == 3
	}
	
	/**
	 * The letterTemplateId is required and always fails when blank.
	 */
	void testAddIngredientWithNoLetterTemplateIdFails() {
		params.moduleId = 1
		def result = controller.addIngredient()
		assert response.text == '{"success":false}'
	}
	
	/**
	 * The moduleId is required and always fails when blank.
	 */
	void testAddIngredientWithNoModuleIdFails() {
		params.letterTemplateId = 1
		def result = controller.addIngredient()
		assert response.text == '{"success":false}'
	}
	
	/**
	 * This is the happy path for creating an ingredient.
	 */
	void testAddIngredient() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		assert letterTemplate.recipe.size() == 3
		assert Module.get(1).ingredients.size() == 3
		
		params.letterTemplateId = 1
		params.moduleId = 1
		
		controller.addIngredient()
		
		def newIngredient = Ingredient.get(4)
		assert newIngredient != null
		assert response.text == '{"success":true,"ingredientId":4}'
		assert letterTemplate.recipe.size() == 4
		assert letterTemplate.recipe.contains(newIngredient)
		assert Module.get(1).ingredients.size() == 4
		assert Module.get(1).ingredients.contains(newIngredient)
		
		// The new ingredient should be at the end of the sequence
		assert newIngredient.sequence == 4
	}
	
	/**
	 * This is the unhappy path when trying to create an ingredient.
	 */
	void testAddIngredientWithDatabaseException() {
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		
		// force a save to throw an exception
		letterTemplate.metaClass.save { Map args ->
			throw new DataIntegrityViolationException("something terrible happened")
		}
		
		params.letterTemplateId = 1
		params.moduleId = 1
		
		controller.addIngredient()
		
		assert response.text == '{"success":false}'
	}
	
	void testAddingCommentsToLetterTemplate(){
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		
		params.id = "1"
		params.comment = "This is some comment"
		
		controller.addComment()
		
		assert response.getStatus() == 200
		def letter = LetterTemplate.get(1)
		assert letter?.comments.size() == 1
	}
	
	void testAddingCommentsToLetterTemplateWithBadRequestParameters(){
		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
		
		params.id = "2"
		params.comment = "This is some comment"
		
		controller.addComment()
		
		assert response.getStatus() == 404
		def letter = LetterTemplate.get(1)
		assert letter?.comments == null
	}
}
