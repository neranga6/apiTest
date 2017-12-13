package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Category)
class CategorySpec extends ConstraintUnitSpec{

    void setup() {
		mockForConstraintsTests(Category, [new Category()])
    }

    void teardown() {
        // Tear down logic here
    }
	
	@Unroll("test Category all constraints #field is #error")
	def "test Category all constraints"() {
		
		when:
		def obj = new Category("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error               | field        	| val
		'nullable'          | 'name'	 	| null
		'blank'				| 'name'		| ""
		'valid'				| 'name'	    | "A valid category"			
		'maxSize'			| 'name'	    | getLongString(51)
		'valid'				| 'name'	    | getLongString(50)
		'valid'				| 'lastModByTimestamp' 	| new Date()
	}
	
	def "test persisting a valid Category"(){
		setup:
		mockDomain(Category)
		
		when:
		Category newCat = new Category(name:testCategoryName,
			lastModBy: testUser,
			lastModByTimestamp: new Date()).save()

		then:
		Category.findByName(testCategoryName)!=null
					
		where:
		testCategoryName = "Test Category"
		testUser = generateUser()	
			
	} 

}
