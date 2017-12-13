package com.nationwide.edm.authoring

import java.util.Date;


class BusinessRule{

	String rule
	User lastModBy
	Date lastUpdated
	Date dateCreated
	
	static belongsTo = [module : Module]
	
    static constraints = {
		rule blank : false, size: 0..500
    }
	
	String toString() {
		rule
	}
	
	BusinessRule getInstance() {
		new BusinessRule(rule: this.rule, lastModBy: this.lastModBy)
	}
}
