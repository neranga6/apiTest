package com.nationwide.edm.authoring

import org.junit.*
import grails.test.mixin.*

@TestFor(SearchController)
class SearchControllerTests extends ControllerTestBase {
	
	void testUnrecognizedSearchType() {
		params.searchType = 'blah'
		controller.index()
		assert flash.error == 'typeMismatch.searchType'
		assert "/" == response.redirectedUrl
	}

	void testEmptySearchType() {
		params.searchType = 'EMPTY'
		controller.index()
		assert flash.error == 'searchType.empty'
		assert "/" == response.redirectedUrl
	}
    
	void testSearchByModule() {
		params.searchType = 'MODULE_CONTENT'
		params.criteria = "blah"
		controller.index()
		assert "/module/list?searchType=MODULE_CONTENT&criteria=blah" == response.redirectedUrl
	}
	
	void testSearchByModuleId() {
		params.searchType = 'MODULE_ID'
		params.criteria = "567"
		controller.index()
		assert "/module/list?searchType=MODULE_ID&criteria=567" == response.redirectedUrl
	}
	
	void testSearchByLetterTemplateName() {
		params.searchType = 'LETTER_TEMPLATE_NAME'
		params.criteria = "some name"
		controller.index()
		assert "/letterTemplate/list?searchType=LETTER_TEMPLATE_NAME&criteria=some+name" == response.redirectedUrl
	}
	
	void testSearchByLetterTemplateId() {
		params.searchType = 'LETTER_TEMPLATE_ID'
		params.criteria = "890"
		controller.index()
		assert "/letterTemplate/list?searchType=LETTER_TEMPLATE_ID&criteria=890" == response.redirectedUrl
	}
	
	void testSearchByLetterTemplateDescription() {
		params.searchType = 'LETTER_TEMPLATE_DESCRIPTION'
		params.criteria = "customer billing change"
		controller.index()
		assert "/letterTemplate/list?searchType=LETTER_TEMPLATE_DESCRIPTION&criteria=customer+billing+change" == response.redirectedUrl
	}
	
	void testSearchByLetterTemplateCategory() {
		params.searchType = 'LETTER_TEMPLATE_CATEGORY'
		params.criteria = "claims"
		controller.index()
		assert "/letterTemplate/list?searchType=LETTER_TEMPLATE_CATEGORY&criteria=claims" == response.redirectedUrl
	}
}
