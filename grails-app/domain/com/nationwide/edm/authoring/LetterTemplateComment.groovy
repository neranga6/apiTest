package com.nationwide.edm.authoring


class LetterTemplateComment {

    String comment
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
	static belongsTo = [letterTemplate : LetterTemplate]
	
    static constraints = {
		comment blank : false, size: 0..500
    }
	
	String toString() {
		comment
	}
	
	static mapping = {
		// "Comment" is a reserved word in Oracle, so change the underlying column name
		comment column: "WRITERS_COMMENT"
	}
}
