package com.nationwide.edm.authoring

import static org.junit.Assert.*

import java.util.Date;

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Ingredient)
@Mock([LetterTemplate, Category, Model, Role, User, Module, Section, SectionGroup, Comment, Ingredient])
class IngredientSpec  extends ConstraintUnitSpec{

    void setup() {
        mockForConstraintsTests(Ingredient, [new Ingredient()])
    }

    void teardown() {
        // Tear down logic here
    }
	
	/*def "create and retrieve a complete ingredient"() {
		setup:
		mockDomain(Ingredient)
		
		when:
		Ingredient ingredient = new Ingredient(order:1, 
			comment:testComment)			
		ingredient.lastModBy = generateUser()
		ingredient.lastModTimestamp = new Date()
		ingredient.addToModule(new Module(content:"Some content",status:"Complete"))
		ingredient.addToLetter(new LetterTemplate())
		ingredient.save()
			
			
		then:
			Ingredient.findByComment(testComment) != null
			
		where:
			testComment = "This is an aweseome test comment"
	}*/
	
	
	@Unroll("test  Ingredient all constraints #field is #error")
	def "test Ingredient all constraints"() {
		
		when:
			def obj = new Ingredient("$field": val)

		then:
			validateConstraints(obj, field, error)
			
			
		where:
			error               | field        	| val
			'nullable'          | 'sequence'	 	| null
			'valid'				| 'sequence'	    | 1
			'valid'				| 'comment'	    | null
			'maxSize'			| 'comment'	    | getLongString(501)
			'valid'				| 'comment'	    | getLongString(500)
			'valid'				| 'comment'	    | ""
			'valid'				| 'comment'	    | "A very nice comment"	
			'valid'				| 'lastModTimestamp' 	| new Date()
		
		
		
	}
	
	def "deleting an ingredient should cascade down to the LetterTemplate's recipe"() {
		when: 
			LetterTemplate letterTemplate = createSampleLetterTemplate()
			Ingredient ingredient = Ingredient.get(1)
			ingredient.delete(flush: true)
		
		then: 
			Ingredient.get(1) == null
			! letterTemplate.recipe.contains(Ingredient.get(1))
			letterTemplate.recipe.contains(Ingredient.get(2))
			letterTemplate.recipe.contains(Ingredient.get(3))
			letterTemplate.recipe.size() == 2
	}
}
