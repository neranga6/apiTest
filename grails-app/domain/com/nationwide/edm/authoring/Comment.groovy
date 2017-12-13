package com.nationwide.edm.authoring

import java.util.Date;

class Comment {

	String comment
	User lastModBy
	Date lastUpdated
	Date dateCreated
		
	static belongsTo = [module : Module]
	
    static constraints = {
		comment blank : false, size: 0..500
    }
	
	String toString() {
		comment
	}
	
	static mapping = {
		// "Comment" is a reserved word in Oracle, so change the underlying table and column names
		table "WRITERS_COMMENT"
		comment column: "WRITERS_COMMENT"
	}
}
