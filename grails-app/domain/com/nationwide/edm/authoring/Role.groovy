package com.nationwide.edm.authoring

class Role {

	String authority
	Date lastUpdated
	Date dateCreated

	static mapping = {
		cache true
		// "Role" is a keyword in Oracle, so change the underlying table name
		table "WRITERS_ROLE"
	}

	static constraints = {
		authority blank: false, unique: true	 
	}
}
