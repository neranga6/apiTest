package com.authoring

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
