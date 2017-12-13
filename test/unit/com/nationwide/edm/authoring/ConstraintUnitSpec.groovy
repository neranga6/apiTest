package com.nationwide.edm.authoring

import spock.lang.Specification

abstract class ConstraintUnitSpec extends Specification {

	String getLongString(Integer length) {
		def longString = ""
		length.times { longString += "a" }
		longString
	}
	
	void validateConstraints(obj, field, error) {
		def validated = obj.validate()
		if (error && error != 'valid') {
			assert !validated
			assert obj.errors[field]
			assert error == obj.errors[field]
		} else {
			assert !obj.errors[field]
		}
	}
	
	Category generateCategory() {
		new Category(name:"Test Category name",
			lastModBy: generateUser(), 
			lastModByTimestamp: new Date())
	}
	
	Ingredient generateIngredient() {
		new Ingredient(sequence:1,comment:"This is a fantastic ingredient")
	}
	
	static User generateUser() {
		new User(username:"mrspock",password:"enterprise", firstName:"Leonard",lastName:"Nimoy")
	}
	
	static User generateUser(String name) {
		//Create a stubbed out SpringSecurityService for unit tests
		def springSecurityService = new Object()
		//Override the encodePassword method, which is used by the User object before data is inserted
		springSecurityService.metaClass.encodePassword = {String password =  "ENCODED_PASSWORD"}		
		User aUser = new User(username:name, password:"enterprise", firstName:"Leonard",lastName:"Nimoy")		
		aUser.springSecurityService = springSecurityService		
		aUser
	}
	
	static Role generateRole(String role) {
		new Role(authority:role)
	}

	static Model generateModel() {
		new Model(templateStyle:"Coupon",imageLocation:"\\images\\someimage.jpg")
	}
	
	/**
	 * Create a simple module with no Ingredients or LetterTemplate associations.
	 */
	static Module createSampleModule(username = "wolverine", moduleContent = "some module") {
		def user = generateUser(username)
		assert user.save(flush:true) != null
		def sectionGroup = new SectionGroup(groupName:"Advocacy Message", sequence: 2, lastModBy: user)
		assert sectionGroup.save() != null
		def section = new Section(sectionName:"Tip", sequence: 3, lastModBy: user, group: sectionGroup)
		assert section.save() != null
		
		def module = new Module(content : moduleContent, status : "Draft", section : section, createdBy: user, lastModBy: user)
		def comment1 = new Comment(comment: "sample comment #1", lastModBy: user, module: module, lastUpdated: new Date())
		comment1.save()
		
		def comment2 = new Comment(comment: "sample comment #2", lastModBy: user, module: module, lastUpdated: new Date() + 15)
		comment2.save()

		assert comment1.save() != null
		module.comments = [comment1, comment2]
		assert module.save() != null
		assert module.id > 0
		module
	}
	
	/**
	 * Create a sample, valid LetterTemplate instance.  Can pass in an optional username and letter name.
	 */
	static LetterTemplate createSampleLetterTemplate(username = "wolverine", letterTemplateName = "sample letter #1", categoryName = "sample category", description = "sample description") {
		def module = createSampleModule(username)
		
		def category = new Category(name: categoryName, lastModBy: module.lastModBy)
		category.save()
		
		def model = generateModel()
		model.save()
		
		def letterTemplate = new LetterTemplate(name: letterTemplateName, status: "Final", category: category, lastModBy: module.lastModBy, createdBy: module.createdBy, model: model, description: description)
		
		def ingredient1 = new Ingredient(comment: "sample ingredient #1", lastModBy: module.lastModBy, sequence: 1, letter: letterTemplate, module: module)
		ingredient1.save()
		
		def ingredient2 = new Ingredient(comment: "sample ingredient #2", lastModBy: module.lastModBy, sequence: 2, letter: letterTemplate, module: module)
		ingredient2.save()
		
		def ingredient3 = new Ingredient(comment: "sample ingredient #3", lastModBy: module.lastModBy, sequence: 3, letter: letterTemplate, module: module)
		ingredient3.save()
		
		letterTemplate.recipe = [ingredient1, ingredient2, ingredient3]
		letterTemplate.save()
		
		letterTemplate
	}
	
}
