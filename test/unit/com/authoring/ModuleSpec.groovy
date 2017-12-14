package com.authoring

import spock.lang.Unroll
import grails.test.mixin.*


/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Module)
class ModuleSpec extends ConstraintUnitSpec{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(Module, [new Module()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Module all constraints #field is #error")
	def "test Module all constraints"() {
		
		when:
		def obj = new Module("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field        		| val
		'nullable'             | 'content'			| null
		'blank'                | 'content'		    | ''
		'valid'                | 'content'		    | 'a'
		'inList'			   | 'status'	        | "FooBar"
		'nullable'             | 'status'           | null
		'valid'                | 'status'		    | 'Draft'
		'nullable'             | 'lastModBy'        | null
		'valid'                | 'lastModBy'        | new User()
		'valid'                | 'lastUpdated'      | new Date()
		'valid'                | 'createdTimestamp' | new Date()
		'nullable'             | 'createdBy'        | null
		'valid'                | 'createdBy'        | new User()
		'valid'                | 'comments'         | null 
		'valid'                | 'comments'         | createComment(10)
		'valid'                | 'businessRules'    | null
		'valid'                | 'businessRules'    | createBusinessRule(10)
		'valid'                | 'ingredients'      | null
		'valid'                | 'ingredients'      | createIngredient(10)

		 
 	}
	
	def "test persisting a valid Module"() {
		setup:
		mockDomain(Role)
		mockDomain(User)
		mockDomain(SectionGroup)
		mockDomain(Section)
				
		when:
		Role adminRole = generateRole(WritersToolConstants.ADMIN_ROLE).save(flush:true)
		User biffle = generateUser("biffle").save(flush:true)
		
		SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : biffle, lastUpdated : new Date()).save()
		Section introSection = new Section(sectionName : "Intro", sequence : 1, lastModBy : biffle, lastUpdated : new Date(), group : logoGroup).save()
		Module salutation = new Module(content : "Hello, Mr. Anderson", status : "Draft",
			createdBy : biffle, createdTimestamp : new Date(),
			lastModBy : biffle, lastUpdated : new Date(),
			section : introSection).save()
			
		then:
		Module.findByContent(contentName)
								
		where:
		contentName = "Hello, Mr. Anderson"
	}
	
	def "get unique letters for module"() {
		setup:
		final int LETTER_ID = 25
		mockDomain(LetterTemplate)
		mockDomain(Ingredient)
		mockDomain(Category)
		mockDomain(Module)
		mockDomain(Section)
		
		when:
		
		Category testCategory = generateCategory()
		User testUser = generateUser()
		Date testDate = new Date()
		
		Section section = new Section(sectionName:"Tip", sequence: 3, lastModBy:testUser)
		Module module = new Module(content: "Hello, Mr. Anderson", status: "Draft", section: section, createdBy: testUser, lastModBy: testUser)
		
		LetterTemplate letterTemplate = new LetterTemplate(
			name: "some name",
			description: "description",
			category: testCategory,
			status:"Draft",
			lastModTimestamp: testDate,
			createTimestamp: testDate
		)
		
		letterTemplate.lastModBy = testUser
		letterTemplate.createdBy = testUser
		
		// attach multiple ingredients to a single letter, all associated with the same module
		Ingredient ingredient1 = new Ingredient(sequence:1, comment:"A", lastModBy:testUser, module:module, letter: letterTemplate)
		Ingredient ingredient2 = new Ingredient(sequence:1, comment:"B", lastModBy:testUser, module:module, letter: letterTemplate)
		Ingredient ingredient3 = new Ingredient(sequence:1, comment:"C", lastModBy:testUser, module:module, letter: letterTemplate)
		
		letterTemplate.recipe = [ingredient1, ingredient2, ingredient3]
		letterTemplate.id = LETTER_ID
		letterTemplate.save(flush: true)
		
		module.ingredients = [ingredient1, ingredient2, ingredient3]
		module.save(flush: true)
		
		then:
		module.letterCount == 1
		module.uniqueLetters == [letterTemplate] as Set
	}
	
	private createComment(Integer count){
		def comments = []
		count.times {
			comments << new Comment()
		}
		comments
	}
	
	private createBusinessRule(Integer count){
		def businessRules = []
		count.times {
			businessRules << new BusinessRule()
		}
		businessRules
	}
	
	private createIngredient(Integer count){
		def ingredients = []
		count.times {
			ingredients << new Ingredient()
		}
		ingredients
	}
	
}