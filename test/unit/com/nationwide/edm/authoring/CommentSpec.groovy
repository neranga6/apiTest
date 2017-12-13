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
@TestFor(Comment)
class CommentSpec extends ConstraintUnitSpec{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(Comment, [new Comment()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Comment all constraints #field is #error")
	def "test Comment all constraints"() {
		
		when:
		def obj = new Comment("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field        		| val
		'nullable'             | 'comment'		        | null
		'size'                 | 'comment'		        | getLongString(501)
		'valid'                | 'comment'		        | getLongString(500)
		'blank'                | 'comment'		        | ''
		'valid'                | 'comment'		        | 'a'
		'nullable'             | 'lastModBy'            | null
		'valid'                | 'lastModBy'            | new User()
		'valid'                | 'lastUpdated'     | new Date()
		'nullable'             | 'module'               | null
	   	'valid'                | 'module'               | new Module()
		
 	}
	
	def "test persisting a valid Comment"() {
		setup:
		mockDomain(Role)
		mockDomain(User)
		mockDomain(SectionGroup)
		mockDomain(Section)
		mockDomain(Module)
		mockDomain(Comment)
				
		when:
		
		Role adminRole = generateRole(WritersToolConstants.ADMIN_ROLE).save(flush:true)
		User biffle = generateUser("biffleg").save(flush:true)
				
		SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : biffle, lastUpdated : new Date()).save()
		Section introSection = new Section(sectionName : "Intro", sequence : 1, lastModBy : biffle, lastUpdated : new Date(), group : logoGroup).save()
		Module salutation = new Module(content : "Hello, Mr. Anderson", status : "Draft",
			createdBy : biffle, createdTimestamp : new Date(),
			lastModBy : biffle, lastUpdated : new Date(),
			section : introSection).save()
		Comment comment = new Comment(comment : "Just some comment", lastModBy : biffle, lastUpdated : new Date(), module : salutation).save()
		
		then:
		Comment.findByComment(testComment)
			
		where:
		testComment = "Just some comment"
		
		
	}
		
}

