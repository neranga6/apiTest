package com.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Section)
class SectionSpec extends ConstraintUnitSpec{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(Section, [new Section()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Section all constraints #field is #error")
	def "test Section all constraints"() {
		
		when:
		def obj = new Section("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field        		| val
		'nullable'             | 'sectionName'		| null
		'blank'                | 'sectionName'		| ''
		'valid'                | 'sectionName'		| 'Some section name'
		'nullable'             | 'lastModBy'        | null
		'valid'                | 'lastModBy'        | new User()
		'valid'                | 'lastUpdated'      | new Date()
		'valid'                | 'modules'          | null 
		'valid'                | 'modules'          | createModule(10)
		'nullable'             | 'group'            | null   
		'valid'                | 'group'            | new SectionGroup()         
		 
 	}
	
	def "test persisting a valid Section"() {
		setup:
		
		mockDomain(Role)
		mockDomain(User)
		mockDomain(SectionGroup)
		mockDomain(Section)
				
		when:
		
		Role adminRole = generateRole(WritersToolConstants.ADMIN_ROLE).save(flush:true)
		User biffle = generateUser("biffle").save(flush:true)
				
		SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : biffle).save()
		Section introSection = new Section(sectionName : "Intro", sequence : 1, lastModBy : biffle, group : logoGroup).save()
			
		then:
		Section.findBySectionName(testSectionName)
								
		where:
		testSectionName = "Intro"
		
		
	}
	
	private createModule(Integer count){
		def modules = []
		count.times {
			modules << new Module()
		}
		modules
	}
	
}