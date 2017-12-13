package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LetterTemplate)
class LetterTemplateSpec extends ConstraintUnitSpec{

    void setup() {
        //mock a person with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(LetterTemplate, [new LetterTemplate()])
    }

    void teardown() {
        // Tear down logic here
    }
	
	def "create and retrieve a complete letter template"() {
		setup:
		mockDomain(LetterTemplate)
		mockDomain(Ingredient)
		mockDomain(Category)
		mockDomain(Model)
			
		when:
		Category testCategory = generateCategory()
		Ingredient testIngredient = generateIngredient()
		User testUser = generateUser()
		Date testDate = new Date()
		Model testModel = generateModel()
		
		LetterTemplate letterTemplate = new LetterTemplate(
			name:validName,
			description:validDescription,
			category:testCategory,
			status:validStatus,
			lastModTimestamp: testDate,
			createTimestamp:testDate,
			model:testModel
			)
		
		letterTemplate.lastModBy = testUser
		letterTemplate.createdBy = testUser
		letterTemplate.save()
		def template = LetterTemplate.findByName(validName)
			
		then:
	    template != null
			
			
		where:
	    validName = "Test template 1"
		validDescription = "A valid test description" 
		validStatus = "Draft"

	}

	@Unroll("test LetterTemplate all constraints #field is #error")
	def "test LetterTemplate all constraints"() {
		
		when:
		def obj = new LetterTemplate("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error               | field        	| val
		'nullable'          | 'name'	 	| null
		'blank'				| 'name'		| ""
		'valid'				| 'name'	    | "A good named template"
		'valid'				| 'description'	    | null
		'maxSize'			| 'description'	    | getLongString(501)
		'valid'				| 'description'	    | getLongString(500)
		'valid'				| 'description'	    | ""
		'valid'				| 'description'	    | "A very nice description"
		'valid'				| 'status'	    | "Draft"
		'inList'			| 'status'	    | "My own status"
		'valid'				| 'lastModTimestamp' 	| new Date()
		'valid'				| 'createTimestamp' 	| new Date()		
	}
	
	
}
