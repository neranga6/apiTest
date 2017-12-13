package com.nationwide.edm.authoring

import java.util.Date;

class Ingredient {
	
	Integer sequence
	String comment
	User lastModBy
	Date lastUpdated
	Date dateCreated
	
	static belongsTo = [
		module:Module,
		letter:LetterTemplate
	]
	
    static constraints = {
		sequence min:1
		comment nullable:true, blank:true, maxSize:500	
    }
	
	Ingredient getInstance() {
		new Ingredient(sequence: this.sequence, comment: this.comment,  lastModBy: this.lastModBy, module : this.module)
	}
	
	/**
	 * When we delete an Ingredient, cascade it to the LetterTemplate#recipe relationship.
	 */
	def beforeDelete() {
		Ingredient ingredient = this
		LetterTemplate letterTemplate = ingredient.letter
		log.debug "beforeDelete of ingredient ${ingredient}"
		
		// FIXME this section should likely be wrapped in a Hibernate session, but it throws an exception
		// org.springframework.orm.hibernate3.HibernateSystemException: Illegal attempt to associate a collection with two open sessions; nested exception is org.hibernate.HibernateException: Illegal attempt to associate a collection with two open sessions
		 
		//LetterTemplate.withNewSession { session ->
			if(letterTemplate?.recipe != null) {
				letterTemplate.recipe.remove(ingredient)
				//letterTemplate.save()
				
				letterTemplate.errors.allErrors.each { println it }
				
			} else {
				log.debug "letterTemplate ${letterTemplate} had no recipe attached, so nothing to update"
			}
		//}
	}
	
	static mapping = {
		// "Comment" is a reserved word in Oracle, so change the underlying column name
		comment column: "WRITERS_COMMENT"
	}

}
