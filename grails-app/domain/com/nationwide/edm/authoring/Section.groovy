package com.nationwide.edm.authoring

import java.util.Date;

class Section {

	String sectionName
	User lastModBy
	Date lastUpdated
	Date dateCreated
	
	static belongsTo = [group : SectionGroup]
	static hasMany = [modules : Module]
	
    static constraints = {
		sectionName blank:false
		modules nullable : true
    }
	
	String toString() {
		sectionName
	}
}
