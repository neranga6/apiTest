package com.nationwide.edm.authoring

import java.util.Date;

class SectionGroup {

	String groupName
	//We don't use sequence for anything, but we're keeping it in the database for reference
	//regarding the list order of Groups
	Integer sequence
	User lastModBy
	Date lastUpdated
	Date dateCreated

	static hasMany = [sections : Section]
	
//	static mapping = {
//		id generator:'assigned'
//	}
	
    static constraints = {
		groupName blank:false
		sequence range: 1..20
		sections nullable : true
    }
	
	String toString() {
		groupName
	}
}
