package com.nationwide.edm.authoring

import static org.junit.Assert.*

import spock.lang.Unroll

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Model)
class ModelSpec extends ConstraintUnitSpec{

    void setup() {
        mockForConstraintsTests(Model)
    }

    void teardown() {
        // Tear down logic here
    }
	
	@Unroll("test Model all constraints #field is #error")
	def "test Model all constraints"() {
		
		when:
		def obj = new Model("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error               | field        			| val
		'nullable'          | 'templateStyle'	 	| null
		'blank'				| 'templateStyle'		| ''
		'valid'				| 'templateStyle'	    | "Coupon"
		
	}

}
