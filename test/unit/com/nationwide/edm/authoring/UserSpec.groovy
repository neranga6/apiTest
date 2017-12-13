package com.nationwide.edm.authoring

import static org.junit.Assert.*


import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(User)
@Mock([Role, UserRole])
class UserSpec extends ConstraintUnitSpec{

    void setup() {
		mockForConstraintsTests(User, [new User()])
    }

    void teardown() {
        // Tear down logic here
    }
	
	@Unroll("test User all constraints #field is #error")
	def "test User all constraints"() {
		
		when:
			def obj = new User("$field": val)

		then:
			validateConstraints(obj, field, error)

		//TODO: Figure out why Grails does not determine nullable
		where:
			error               | field        	| val
			'nullable'          | 'username'	 	| null
			'blank'				| 'username'		| ""
			'valid'				| 'username'	    | "validUser"
			//'valid'			| 'roles'			|createRoles(1)
			//'nullable'			| 'roles'			|null
			
	}
	
	private createRoles(Integer count) {
		def roles = []
		count.times {
			roles << new Role()
		}

		roles
	}
	
	def "find role for user"(){
		
		setup: 
		def userRole = new Role(authority:"ROLE_USER").save(flush:true, failOnError:true)
		def adminRole = new Role(authority:"ROLE_ADMIN").save()
		def cyclops = new User(username:"cyclops",firstName:"Scott",lastName:"Summers", password:"password",enabled:true,accountExpired:false,accountLocked:false,passwordExpired:false).save()
		cyclops.springSecurityService = Mock(grails.plugins.springsecurity.SpringSecurityService)
	      
		when:
		UserRole.create(cyclops,adminRole)
		UserRole.create(cyclops, userRole)
		
		then:
		
		cyclops.getUserRole() == 'ROLE_ADMIN'
		

		
	}

}
