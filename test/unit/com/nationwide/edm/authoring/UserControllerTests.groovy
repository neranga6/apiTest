package com.nationwide.edm.authoring

import grails.plugins.springsecurity.SpringSecurityService
import grails.test.GrailsMock
import grails.test.mixin.*

import org.junit.*

@TestFor(UserController)
@Mock([User, Role, UserRole])
class UserControllerTests extends ControllerTestBase{
	

	
	@Before
	void setupTest() {
		setupRoles()
		setupSpringSecurityStub()
	}

    def populateValidParams(params) {
	  assert params != null
	  params["username"] = 'grahamj'
	  params["enabled"] = true
	  params["accountExpired"] = false
      params["accountLocked"] = false
	  params["passwordExpired"] = false
		  
    }
	
	
	def setupRoles(){
		new Role(authority:"ROLE_USER").save(flush:true, failOnError:true)
		new Role(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
		new Role(authority:"ROLE_WRITER").save(flush:true,failOnError:true)
		new Role(authority:"ROLE_REVIEWER").save(flush:true, failOnError:true)
	}
	


    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/user/list'

        response.reset()

        populateValidParams(params)
        def user = new User(params)
		
		user.springSecurityService = springSecurityServiceStub
		// Give user an isDirty method because @Mock doesn't; it does not need to do anything.
		user.metaClass.isDirty { }
		
        assert user.save() != null

        // test invalid parameters in update
        params.id = user.id
		params["username"] = ""

        controller.update()

        assert view == "/user/edit"
        assert model.userInstance != null
     

    }

//TODO - Fix this test, currently assert updatedUser.userRole == 'ROLE_WRITER' always returns 'ROLE_REVIEWER' when run with grails test-app unit: or with mvn
	// in STS it works fine
	//
//	void testUpdateRoles() {
//		controller.update()
//
//		assert flash.message != null
//		assert response.redirectedUrl == '/user/list'
//
//		response.reset()
//
//		populateValidParams(params)
//		def user = new User(params)
//		
//		user.springSecurityService = springSecurityServiceStub
//		// Give user an isDirty method because @Mock doesn't; it does not need to do anything.
//		user.metaClass.isDirty { }
//		
//		assert user.save() != null
//
//		// test invalid parameters in update
//		params.id = user.id
//		params["username"] = ""
//
//		controller.update()
//
//		assert view == "/user/edit"
//		assert model.userInstance != null
//	 
//		response.reset()
//	
//		params["id"] = user.id
//		params["username"] = 'grahamj'
//		params["userRoleSelection"] = 'ROLE_WRITER'
//		
//		controller.update()
//
//		assert response.redirectedUrl == "/user/list"
//		assert flash.message != null
//		
//		def updatedUser = User.findByUsername('grahamj')
//		assert updatedUser.authorities.size() == 1
//		assert updatedUser != null
//		assert updatedUser.username == 'grahamj'
//		assert updatedUser.userRole == 'ROLE_WRITER'
//
//	}
	  
}

