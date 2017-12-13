package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import grails.plugin.spock.ControllerSpec

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(AdminController)
class AdminControllerSpec extends ControllerSpec {
	
	def 'index action'() {
		// this is a workaround due to a bug: http://jira.grails.org/browse/GRAILS-7969
		controller.metaClass.render = { Map args -> controller.metaClass.renderArgs = args }
		
		when:
		controller.index()
	
		then:
		renderArgs.view == "admin"
	}
}
