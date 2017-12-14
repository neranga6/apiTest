package com.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(BusinessRule)
class BusinessRuleSpec extends ConstraintUnitSpec{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(BusinessRule, [new BusinessRule()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test BusinessRule all constraints #field is #error")
	def "test BusinessRule all constraints"() {
		
		when:
		def obj = new BusinessRule("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field        		| val
		'nullable'             | 'rule'		        | null
		'size'                 | 'rule'		        | getLongString(501)
		'valid'                | 'rule'		        | getLongString(500)
		'blank'                | 'rule'		        | ''
		'valid'                | 'rule'		        | 'a'
		'nullable'             | 'lastModBy'        | null
		'valid'                | 'lastModBy'        | new User()
		'valid'                | 'lastUpdated'      | new Date()
		'nullable'             | 'module'           | null
		'valid'                | 'module'           | new Module()
		
 	}
	
	def "test persisting a valid BusinessRule"() {
		setup:
		mockDomain(Role)
		mockDomain(User)
		mockDomain(SectionGroup)
		mockDomain(Section)
		mockDomain(Module)
		mockDomain(BusinessRule)
		
		when:
		Role adminRole = generateRole(WritersToolConstants.ADMIN_ROLE).save(flush:true)
		User biffle = generateUser("biffleg").save(flush:true)
		
		SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : biffle, lastUpdated : new Date()).save()
		Section introSection = new Section(sectionName : "Intro", sequence : 1, lastModBy : biffle, lastUpdated : new Date(), group : logoGroup).save()
		Module salutation = new Module(content : "Hello, Mr. Anderson", status : "Draft",
			createdBy : biffle, createdTimestamp : new Date(),
			lastModBy : biffle, lastUpdated : new Date(),
			section : introSection).save()
		BusinessRule businessRule = new BusinessRule(rule : "Just some business rule", lastModBy : biffle, lastUpdated : new Date(), module : salutation).save()
		
		then:
		BusinessRule.findByRule(testRule)
			
		where:
		testRule = "Just some business rule"
		
		
	}
}
