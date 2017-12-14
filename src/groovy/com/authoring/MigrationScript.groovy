package com.authoring


import groovy.io.FileType
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import java.sql.SQLException

/**
Setup

It is necessary to set up Windows properly so that you can
access Access databases from Java.  To do so,

Go to Control Panel -> Administrative Tools -> Data Source (ODBC) ->
System DSN -> Add -> Microsoft Access Driver (.mdb) -> Finish ->
(add name such as SOA-Access-DB, optional description) ->
in the Database area, click the Select button, navigate to where your
local copy of the DB is using the Windows 3-style file dialog ->
I set the username and password under Advanced, although I'm not sure
this is actually necessary.

Windows 7 Users!!
Currently Microsoft does not have any 64 bit ODBC drivers available for the Office products.
However, as long as the application that you want to connect to is 32 bit, you can use the 32 bit ODBC drivers to create the DSN. To get to the 32 bit ODBC drivers, the 32 bit Data Source administrator must be used. The 32 bit Data Source administrator must be launched directly if you are using a 64 bit OS. The file should be located at the following path:
c:\windows\sysWOW64\odbcad32.exe


Then, modify the "def sql = Sql.newInstance line to contain
jdbc:odbc:{what you named the database in the Control Panel}
as the first parameter, the username/password as 2nd/3rd params,
and the driver as the fourth.

Also, delete any existing target database ("del devDB*" at the
command line) before running the script, to avoid duplicates in
the database.  The script will not work properly with a pre-existing
target database.

Requirement: jtds-1.2.2.jar (or compatible version) is in %GROOVY_HOME%\lib
	This is available from the enterprise repo
Requirement: use STS or the Grails Console.  This will ensure that the classpath
is already populated with the project's classes.  It would be possible to set up
the environment to allow simply running "groovy ReleaseScript", but the script will
not do that for you.

Run script with this command inside the Grails console:
<code>com.nationwide.edm.authoring.MigrationScript.main()</code>

Note: When accessing Access directly, columns DO appear to be case-sensitive
SQL Server seems to be indifferent to capitalization

Specific steps for handling custom ids. (The only tables we need to preserve ids for is Module and LetterTemplate.)
	1. In DataSource.groovy set data source to 'create-drop' (this drops the table, alternatively you could delete local files if you are using H2)
	2. Comment the static mapping for id generator in Module.groovy and LetterTemplate.groovy (this ensures that the id columns get set to auto-increment)
	3. Run grails console (this starts grails and drops the tables....for some reason it does not re-create them)
	4. Kill the grails console
	5. Now set DataSource.groovy to 'create'
	6. Run grails console (this starts grails and creates the tables)
	7. Kill the grails console
	8. Uncomment the static mapping for id generator in Module.groovy and LetterTemplate (this allows us to manually assign the ids)
	9. Set DataSource.groovy to '' (We're doing this because we don't want to remove the auto-increment on the ids, but it still lets us assign them manually)
	10. Run grails console
	11. Run the migration script
	12. Upon successful completion of the migration script make sure you remove the id generator in Module and LetterTemplate
*/
class MigrationScript {
	// Are you using Microsoft Access as the data source?
	// (A value of "false" indicates you are using MS SQL Server as the source.)
	public static final boolean msAccess = true;

	private static final Map sourceDatabaseSettings
	
	static {
		if(msAccess) {
			String odbcName = "OYS-Access-DB"
			sourceDatabaseSettings = [url:"jdbc:odbc:${odbcName}", driver: "sun.jdbc.odbc.JdbcOdbcDriver", username: null, password: null]
		} else {
			String server = "OHP305SQLW02.nwielab.net:1021"
			String databaseName = "SOA_Projects"
			sourceDatabaseSettings = [url:"jdbc:jtds:sqlserver://${server};DatabaseName=${databaseName}", driver: "net.sourceforge.jtds.jdbc.Driver", username: "SOA_Admin", password: "Password1"]
		}
	}
	
	public static void main(String[] args) {
		Sql sourceSql = null
		try {
			sourceSql = Sql.newInstance(sourceDatabaseSettings.url, sourceDatabaseSettings.username, sourceDatabaseSettings.password, sourceDatabaseSettings.driver)
			migrateOYSData(sourceSql);
		} catch(Exception ex) {
			println "Exception while migrating OYS data"
			println ex
		} finally {
			sourceSql?.close()
			println ("Closed SQL connection")
		}
		println "Migration Completed."
	}
	
	public static void migrateOYSData(Sql sql) {
		
		println "Entering migrateOYSData"
		
		loadFixedTableData();
		
		def userRole = new Role(authority:"ROLE_USER").save(flush:true)
		def adminRole = new Role(authority:"ROLE_ADMIN").save(flush:true)
		def writerRole = new Role(authority:"ROLE_WRITER").save(flush:true)
		def reviewerRole = new Role(authority:"ROLE_REVIEWER").save(flush:true)
		
		println 'Roles were created'
		
		migrateTableData(sql, "tblUsers") { userRow ->
			createUser(userRow, adminRole, writerRole, reviewerRole)
		}
		

		User defaultUser = User.findByUsername("admin")
		println ("Default user for lastModBy field is ${defaultUser}")
		
		migrateTableData(sql, "tblCategories") { categoryRow ->
			createCategory(categoryRow, defaultUser)
		}
		
		migrateTableData(sql, "tblGroups") { groupRow ->
			createGroup(groupRow, defaultUser)
		}
		
		migrateTableData(sql, "tblSection") { sectionRow ->
			createSection(sectionRow, defaultUser)
		}
		
		migrateTableData(sql, "tblTemplates") { templateRow ->
			createModel(templateRow)
		}
		
		migrateTableData(sql, "tblModules") { moduleRow ->
			createModule(moduleRow, sql)
		}
		
		migrateTableData(sql, "tblLetters") { letterRow ->
			createLetterTemplate(letterRow, sql, defaultUser)
		}
		

	}
	
	/**
	* Execute "SELECT" SQL query against a particular database table, passing each row into a closure.
	*/
   private static void migrateTableData(Sql sql, String sourceTable, Closure migrationClosure) {
	   int successes = 0
	   int failures = 0
	   String sqlQuery = "select * from ${sourceTable}"
	   List<GroovyRowResult> list = sql.rows(sqlQuery)
	   
	   println "Migrating ${list.size()} rows from ${sourceTable}"
	   
	   list.each { row ->
		   migrationClosure.call(row) ? successes++ : failures++
	   }
	   println("Finished migrating ${sourceTable} data")
	   println("${sourceTable} data Successes: ${successes} and Failures: ${failures}")
   }
	
  
	private static loadFixedTableData(){

		println 'Loading images from C:\\Projects\\WritersTool\\Images....'
		def imageFileList = []
		
		def dir = new File("C:\\Projects\\WritersTool\\Images")
		dir.eachFileRecurse(FileType.FILES){file ->
			imageFileList << file
		}
		
	
		imageFileList.each {
		
			def tokenizeList = it.path.tokenize('\\')
			def fullFileName
			def fileExtension
			tokenizeList.each {
				if(it.contains(".")){
					fullFileName = it
				}	
			}
			def image = new Image(originalFilename : fullFileName, thumbnailFilename : fullFileName, newFilename : fullFileName, imageBytes : it.getBytes(), fileSize : it.size())
		
			if(image){
				if(image.validate()){
					image.save(flush:true)
				} else {
					println image.errors
					println("Error in validation for image: ${fullFileName}")
				}
			}
		
		}
		
		println 'Done loading images....'
	}
		 
	private static boolean createUser(GroovyRowResult row, Role adminRole, Role writerRole, Role reviewerRole){
		boolean success = false
		
		try {
			User user = new User(
				username: row.UserName,
				firstName: row.FirstName,
				lastName: row.LastName,
				password: row.Password,
				enabled: true
			)
			if(row.Status == 'D'){
			
				user.enabled = false
			}
			
					
			if (user.validate()) {
				success = user.save(flush:true)
				
				if(row.Status == 'A'){

						UserRole.create(user,adminRole)
					}
				if(row.Status == 'W'){

						UserRole.create(user,writerRole)
					}
				if(row.Status == 'R'){

						UserRole.create(user,reviewerRole)
					}
			} else {
				println user.errors
				println("Error in validation for user: ${row.UserName}")
			}
		} catch (SQLException e) {
			println("SQL exception while creating user", e)
		}
		return success
	}
	
	private static boolean createCategory(GroovyRowResult categoryRow, User defaultUser){
		
		boolean success = false
	
		try{
			Category category = new Category(name : categoryRow.Category, lastModBy:defaultUser)
			
			if (category.validate()) {
				success = category.save(flush:true)
			} else {
				println category.errors
				println("Error in validation for category: ${categoryRow.Category}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Category", e)
		}
		return success
		
		
	}
	
	private static boolean createGroup(GroovyRowResult groupRow, User defaultUser){
		
		boolean success = false
			
		try{
			SectionGroup group = new SectionGroup(groupName : groupRow.GroupName, sequence : groupRow.Sequence, lastModBy : defaultUser )
			group.id = groupRow.ID
			if (group.validate()) {
				success = group.save(flush:true)
			} else {
				println group.errors
				println("Error in validation for SectionGroup: ${groupRow.GroupName}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Group", e)
		}
		return success
	}
	
	private static boolean createSection(GroovyRowResult sectionRow, User defaultUser){

		boolean success = false
		SectionGroup group
		
		if(sectionRow.GroupID){
			group = SectionGroup.get(sectionRow.GroupID)
		}
		
		if(group){
			try{
				Section section = new Section(sectionName : sectionRow.Section, group : group, lastModBy : defaultUser )
					
				if (section.validate()) {
					success = section.save(flush:true)
				} else {
					println section.errors
					println("Error in validation for Section: ${sectionRow.Section}")
				}
				
				
			} catch (SQLException e) {
				println("SQL exception while creating new Group", e)
			}
		}else{
			println("No group found for group sequence ${sectionRow.GroupID}")
		}
		
		return success
		
	}
	
	private static boolean createModel(GroovyRowResult templateRow){
		boolean success =  false
		try{
			Model model = new Model(templateStyle: templateRow.templatename)
			
			if(model.validate()){
				success = model.save(flush:true)
			}else {
				println model.errors
				println("Error in validation of model: ${templateRow.templatename}")
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Model", e)
		}
		return success
	}
		
	private static boolean createModule(GroovyRowResult moduleRow, Sql sql){
		
		boolean success = false
		String sectionQuery = "select Section from tblSection where ID = ${moduleRow.SectionId}"
		def row = sql.firstRow(sectionQuery)
		
		def section
		if(row){
			section = Section.findBySectionName(row.Section)
		}
		
		def status = "Draft"
		
		if(moduleRow.StatusId == 1){
			status = "Draft"
		}
		if(moduleRow.StatusId == 2){
			status == "Review"
		}
		if(moduleRow.StatusId == 3){
			status == "Final"
		}
		
		def dbCreatedBy = moduleRow.CreatedBy ?: "admin"
		def dbLastModBy = moduleRow.LastModBy ?: "admin"
				
		def createdBy = User.findByUsername(dbCreatedBy)
		def lastModBy = User.findByUsername(dbLastModBy)
		 
			
		def module
		if(section && moduleRow.Content){
			
			def imgTagList = moduleRow.Content.findAll(/<img[^>]+src="([^"]+)"[^>]+>/)
			def newContent
			if(imgTagList){
				def imgTagListSize = imgTagList.size()
				println "Modifying ${imgTagListSize} img tags for module : ${moduleRow.pkModuleID}"
				newContent = changeImageSrcAttributes(moduleRow.Content, imgTagList)			
			}else{
				newContent = moduleRow.Content
			}			
			
			module = new Module(content : newContent, status : status, section : section, createdBy : createdBy, lastModBy : lastModBy)
		}
		
// need to ask if we care about Date, Grails is handling this on the save() which do not be helping us
		try{
			if(module){
				module.id = moduleRow.pkModuleID
				
				String commentQuery = "select * from tblModuleComments where ID = ${moduleRow.pkModuleID}"
				String businessRuleQuery = "select * from tblModuleBusinessRules where ID = ${moduleRow.pkModuleID}"
				
				List<GroovyRowResult> commentList = sql.rows(commentQuery)
				List<GroovyRowResult> businessRuleList = sql.rows(businessRuleQuery)
					
				def moduleCommentListSize = commentList.size()
				def moduleCommentCount = 0
											
				commentList.each { commentRow ->
					
					def dbCommentLastModBy = commentRow.LastModBy ?: "admin"
					def commentLastModBy = User.findByUsername(dbCommentLastModBy)
					if(commentRow.CommentText){
						module.addToComments(new Comment(comment : commentRow.CommentText, lastModBy : commentLastModBy, module : module))
						moduleCommentCount++
					}
					
				}
				
				if(moduleCommentCount != moduleCommentListSize){
					println "Unable to migrate all module comments attached to module ${moduleRow.pkModuleID}"
				}
				
				def businessRuleListSize = businessRuleList.size()
				def businessRuleCount = 0
				
				businessRuleList.each { businessRuleRow ->
					
					def dbBusinessRuleLastModBy = businessRuleRow.LastModBy ?: "admin"
					
					def businessRuleLastModBy = User.findByUsername(dbBusinessRuleLastModBy)
					if(businessRuleRow.BusinessRuleText){
						module.addToBusinessRules(new BusinessRule(rule : businessRuleRow.BusinessRuleText, lastModBy : businessRuleLastModBy, module : module))
						businessRuleCount++
					}
					
				}
				
				if(businessRuleCount != businessRuleListSize){
					println "Unable to migrate all module business rules attached to module ${moduleRow.pkModuleID}"
				}
				
							
				if(module.validate()){
//					if(moduleRow.LastModDt){
//						module.lastUpdated = moduleRow.LastModDt
//					}
					success = module.save(flush:true)
//					if(success && moduleRow.CreatedDt){
//						module.dateCreated = moduleRow.CreatedDt
//						success = module.save()
//					}
					
				}else {
					println module.content.length()
					println module.errors
					println("Error in validation of module: ${moduleRow.pkModuleID}")
				}
			}else{
				println "Unable to create a module object from module: ${moduleRow.pkModuleID}"
			}
			
		} catch (SQLException e) {
			println("SQL exception while creating new Module", e)
		}
		return success
		
	}
		
	
	private static boolean createLetterTemplate(GroovyRowResult letterRow, Sql sql, User defaultUser){
				
		boolean success = false
		def categoryQuery = "select Category from tblCategories where CategoryID = ${letterRow.CategoryId}"
		def modelQuery = "select tblTemplateID from tblLetterToTemplate where tblLetterID = ${letterRow.pkRefID}"
	
			
		def categoryRow = sql.firstRow(categoryQuery)
		def modelRow = sql.firstRow(modelQuery)
		
		def category
		if(categoryRow){
			category = Category.findByName(categoryRow.Category)
		}
		
		def modelName = ""
		if(modelRow){
			if(modelRow.tblTemplateID == 1){
				modelName = "Left Bar"
			}
			if(modelRow.tblTemplateID == 2){
				modelName = "Centered"
			}
			if(modelRow.tblTemplateID == 3){
				modelName = "Coupon/Tearoff"
			}
		}
		
		
		def model
		if(modelName){
			model = Model.findByTemplateStyle(modelName)
		}
		
		def status = "Draft"
		
		if(letterRow.StatusId == 1){
			status = "Draft"
		}
		if(letterRow.StatusId == 2){
			status == "Review"
		}
		if(letterRow.StatusId == 3){
			status == "Final"
		}
		
		def dbCreatedBy = letterRow.CreatedBy ?: "admin"
		def dbLastModBy = letterRow.LastModBy ?: "admin"
				
		def createdBy = User.findByUsername(dbCreatedBy)
		def lastModBy = User.findByUsername(dbLastModBy)
		
		def description = ""
		if(letterRow['Overall Comments'] && letterRow['Overall Comments'] != '.'){
			description = letterRow['Overall Comments']
		}
			
		try{
			def letter = new LetterTemplate(name : letterRow.Title, description : description, lastModBy : lastModBy, category : category, status : status, model : model, createdBy : createdBy)
			if(letter){
				
				letter.id = letterRow.pkRefID
				def letterCommentQuery = "select * from tblLetterComments where LetterID = ${letterRow.pkRefID}"
				List<GroovyRowResult> letterCommentList = sql.rows(letterCommentQuery)
				
				def letterCommentListSize = letterCommentList.size()
				def letterCommentCount = 0
				letterCommentList.each { commentRow ->
					
					def dbCommentLastModBy = commentRow.LastModBy ?: "admin"
					def commentLastModBy = User.findByUsername(dbCommentLastModBy)
					if(commentRow.CommentText && commentRow.CommentText != '.'){
						letter.addToComments(new LetterTemplateComment(comment : commentRow.CommentText, lastModBy : commentLastModBy, letterTemplate : letter))
						letterCommentCount++
					}
					
				}
				
				if(letterCommentCount != letterCommentListSize){
					println "Unable to migrate all letter comments attached to letter ${letterRow.pkRefID}"
				}
				
				def recipeQuery = "select * from tblLetterRecipeModule where LetterID = ${letterRow.pkRefID}"
				List<GroovyRowResult> recipeList = sql.rows(recipeQuery)
				// tblLetterRecipeModule references two letters 809 and 810 that do not exist in tblLetters...this accounts for the difference of 23 recipes that do
				// not get migrated
				def recipeListSize = recipeList.size()
				def ingredientCount = 0
				recipeList.each { recipeRow ->
					
					def module = Module.get(recipeRow.ModuleID)
					if(module){
						def ingredient = new Ingredient(module : module, letter : letter, sequence : recipeRow.Sortorder, lastModBy : defaultUser)
						if(ingredient){
							module.addToIngredients(ingredient)
							ingredientCount++
						}else{
							println "Unable to add indgredient for LetterID ${letter.id} and ModuleID ${module.id}"
						}
					}
									
				}
						
				
				if(letter.validate()){
					success = letter.save(flush:true)
				}else {
					println letter.errors
					println("Error in validation of LetterTemplate: ${letterRow.pkRefID}")
				}
			}
			
			
		} catch (SQLException e) {
			println("SQL exception while creating new LetterTemplate", e)
		}
		return success
				
			
		}
		
		private static String changeImageSrcAttributes(String origContent, List imgTagList){
			
			def newContent = origContent
			
			imgTagList.each {
				
				def srcAttribute = it.find(/src=["|\']([^"|\']+)/)
				println "Found source attribute = ${srcAttribute}"
				def parsedSrcAttributeList = srcAttribute.tokenize('/')
				
				parsedSrcAttributeList.each {
					
					if(it =~ /.bmp/ || it =~ /.png/ || it =~ /.gif/ || it =~ /.jpg/){
						println "Found image file name = ${it}"
						
						def foundImage = Image.findByOriginalFilename(it)
						if(foundImage){
							def newSrcAttribute = 'src="/writers-tool-web/image/picture/' + foundImage.id + '"'
							def replacedSrcAttribute = srcAttribute + '"'
							println "Replacing ${replacedSrcAttribute} with ${newSrcAttribute}"
							
							newContent = origContent.replace(replacedSrcAttribute, newSrcAttribute)
						}else{
							"No image found for attribute ${srcAttribute}"
						}
						
					}
					
				}
				
			}
			
			if(!newContent){
				newContent = origContent
			}
			return newContent
		}
	
}
