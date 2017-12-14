package com.authoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public enum SearchType {
	
	EMPTY("-- select search type --"),
	MODULE_CONTENT("Module Content"),
	MODULE_ID("Module ID"),
	LETTER_TEMPLATE_NAME("Template Name"),
	LETTER_TEMPLATE_ID("Template ID"),
	LETTER_TEMPLATE_DESCRIPTION("Description (Audience & Intent)"),
	LETTER_TEMPLATE_CATEGORY("Template Category");

	private static final Log log = LogFactory.getLog(SearchType.class);
	private final String description;

	SearchType(String desc) {
		description = desc;
	}
	
	public String getDescription() {
		return description;
	}
	
	static SearchType createSearchType(String rawSearchType) {
		SearchType searchType;
		
		try {
			searchType = rawSearchType;
		} catch (IllegalArgumentException e) {
			log.warn "Problem when parsing SearchType from String input: \"${rawSearchType}\"";
		}
		
		return searchType;
	}

}
