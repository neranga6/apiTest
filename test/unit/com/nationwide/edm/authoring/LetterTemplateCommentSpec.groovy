package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LetterTemplateComment)
class LetterTemplateCommentSpec extends ConstraintUnitSpec{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(LetterTemplateComment, [new LetterTemplateComment()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Comment all constraints #field is #error")
	def "test LetterTemplateComment all constraints"() {
		
		when:
		def obj = new LetterTemplateComment("$field": val)

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
		'valid'                | 'lastUpdated'          | new Date()
		'nullable'             | 'letterTemplate'       | null
		 'valid'               | 'letterTemplate'       | new LetterTemplate()
		
	 }
}
