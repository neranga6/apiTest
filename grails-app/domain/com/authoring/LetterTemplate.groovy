package com.authoring

class LetterTemplate {
	
	String name
	String description
	Category category
	String status
	Model model	
	User createdBy
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
//		static mapping = {
//			id generator:'assigned'
//		}
	
	static hasMany = [recipe:Ingredient, comments: LetterTemplateComment]
	
    static constraints = {
		name blank:false 
		description blank:true, nullable:true, maxSize:500
		status inList:["Draft","Review","Final"]
		category nullable:true
	}


	String toString() {
		name
	}
}
