package com.authoring

class Category{

	String name
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
    static constraints = {
		name blank:false, maxSize:50
    }
	
	String toString() {
		 name
	}
}
