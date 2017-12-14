package com.authoring

class Module {
	
	String content
	String status
	User createdBy
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
	List statusValues = ["Draft","Review"]
	Boolean statusEnabled = true
	
	//statusVzlues and statusEnabled are used for display purpose only.  Do not store in the db
	static transients = ['statusValues','statusEnabled']	
	
	def getUniqueLetters() {
		(ingredients*.letter as Set)
	}
	
	int getLetterCount() {
		def ids = getUniqueLetters()
		(ids == null) ? 0 : ids.size()
	}
	
	static belongsTo = [section : Section]
	
	static hasMany = [comments : Comment, businessRules : BusinessRule, ingredients : Ingredient]
	
    static constraints = {
		content blank : false
		status inList:["Draft","Review","Final"]
		comments nullable : true
		businessRules nullable : true 
		ingredients nullable : true 
    }
	
	static mapping = {
		// "Module" is a keyword in Oracle, so change the underlying table name
//		id generator:'assigned'
		table "WRITERS_MODULE"
		
		// Oracle creates this as LONG by default, which is not text searchable.  This will create a "CLOB" datatype.
		// The longest content in legacy Access database is 12046 and a VARCHAR2 goes up to 4000.
		content sqlType: "clob"
	}
}
