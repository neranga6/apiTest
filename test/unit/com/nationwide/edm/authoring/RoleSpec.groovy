package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Role)
class RoleSpec extends ConstraintUnitSpec{

    void setup() {
		mockForConstraintsTests(Role, [new Role(authority:"DUPLICATE")])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Role all constraints #field is #error")
	def "test Role all constraints"() {
		
		when:
		def obj = new Role("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error               | field        	| val
		'nullable'          | 'authority'	 	| null			
		'valid'				| 'authority'	    | "ROLE_ADMIN"
		'unique'			| 'authority'	    | "DUPLICATE"
	
	}
	
	def "test persisting a valid Role"() {
		setup:
		mockDomain(Role)
		mockDomain(User)		
		
		when:
		Role testRole = new Role(authority:testRoleName)
		//testRole.lastModBy = generateUser("cyclops")		
		testRole.save()
		
		then:
		Role.findByAuthority(testRoleName) != null
		
		where:
		testRoleName = "Admin"
	} 

}
