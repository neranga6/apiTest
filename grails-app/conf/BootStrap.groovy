import com.nationwide.edm.authoring.BusinessRule
import com.nationwide.edm.authoring.Category
import com.nationwide.edm.authoring.Comment
import com.nationwide.edm.authoring.Ingredient
import com.nationwide.edm.authoring.LetterTemplate
import com.nationwide.edm.authoring.LetterTemplateComment
import com.nationwide.edm.authoring.Model
import com.nationwide.edm.authoring.Module
import com.nationwide.edm.authoring.Role
import com.nationwide.edm.authoring.Section
import com.nationwide.edm.authoring.SectionGroup
import com.nationwide.edm.authoring.User
import com.nationwide.edm.authoring.UserRole
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition

class BootStrap {
	
	def springSecurityService	

    def init = { servletContext ->
		
		SpringSecurityUtils.clientRegisterFilter('requestHeaderAuthenticationFilter', SecurityFilterPosition.PRE_AUTH_FILTER)
		//SpringSecurityUtils.clientRegisterFilter('clearTrustHeaderUserDetailsFilter', SecurityFilterPosition.PRE_AUTH_FILTER.getOrder() + 10)
	
		//TODO: Get this to work in DEV only
		SpringSecurityUtils.clientRegisterFilter('cleartrustStubFilter', SecurityFilterPosition.PRE_AUTH_FILTER.getOrder() - 10)
		
		def userRole = new Role(authority:"ROLE_USER").save(flush:true, failOnError:true)
		def adminRole = new Role(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
		def writerRole = new Role(authority:"ROLE_WRITER").save(flush:true,failOnError:true)
		def reviewerRole = new Role(authority:"ROLE_REVIEWER").save(flush:true, failOnError:true)
	
		def cyclops = new User(username:"cyclops",firstName:"Scott",lastName:"Summers", password:"password",enabled:true).save(flush:true,failOnError:true)
		UserRole.create(cyclops,userRole)		
		def xavier = new  User(username:"xavier",firstName:"Professor",lastName:"Xavier", password:"password",enabled:true).save(flush:true,failOnError:true)
		UserRole.create(xavier,userRole)
		def wolverine = new User(username:"wolverine",firstName:"John",lastName:"Logan", password:"password",enabled:true).save(flush:true,failOnError:true)
		UserRole.create(wolverine,userRole)
		def gambit = new User(username:"gambit",firstName:"Remy",lastName:"LeBeau", password:"password",enabled:true).save(flush:true,failOnError:true)
		UserRole.create(gambit,userRole)
		def welchb4 = new User(username:"welchb4",firstName:"Barb",lastName:"Welch", password:"password").save(flush:true,failOnError:true)
		UserRole.create(welchb4,userRole)
		
		def admin = new User(username:"admin",firstName:"Admin",lastName:"User", 
			password:"password",enabled:true).save(flush:true,failOnError:true)
		def reviewer = new User(username:"reviewer",firstName:"Reviewer",lastName:"User", 
			password:"password",enabled:true).save(flush:true,failOnError:true)
		def writer = new User(username:"writer",firstName:"Writer",lastName:"User", 
			password:"password",enabled:true).save(flush:true,failOnError:true)
		
		UserRole.create(admin, adminRole)
		//UserRole.create(admin, userRole)
		UserRole.create(reviewer, reviewerRole)
		//UserRole.create(reviewer, userRole)
		UserRole.create(writer,writerRole)
		//UserRole.create(writer, userRole)
		
		def categoryCarRepair = new Category(name:"Car Repair",lastModBy: cyclops).save(failOnError:true)
		new Category(name:"Car Settlement Closing Letter",lastModBy: cyclops).save(failOnError:true)
		new Category(name:"Claim Status",lastModBy: cyclops ).save(failOnError:true)
		
		new Category(name:"Corporate Rental",lastModBy: cyclops).save(failOnError:true)
		new Category(name:"Coverage Exhausted",lastModBy: cyclops).save(failOnError:true)
		
		//Groups
		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : gambit).save(failOnError:true)
		def referenceInfoGroup = new SectionGroup(groupName : "Reference Information", sequence : 2, lastModBy : gambit).save(failOnError:true)
		def addressGroup = new SectionGroup(groupName : "Address", sequence : 3, lastModBy : gambit).save(failOnError:true)
		def groupTitle = new SectionGroup(groupName : "Title", sequence : 4, lastModBy : gambit).save(failOnError:true)
		def groupBody = new SectionGroup(groupName:"Body", sequence: 5, lastModBy:wolverine).save(failOnError:true)
		def groupClosing = new SectionGroup(groupName:"Closing", sequence: 6, lastModBy:wolverine).save(failOnError:true)
		def groupCoupon = new SectionGroup(groupName:"Coupon", sequence: 7, lastModBy:wolverine).save(failOnError:true)
		def groupMessage = new SectionGroup(groupName:"Advocacy Message", sequence: 8, lastModBy:wolverine).save(failOnError:true)
		
		//Sections
		def sectionLogo = new Section(sectionName:"Logo", sequence: 1, lastModBy:wolverine, group: logoGroup).save(failOnError:true)
		def sectionReferenceInfo = new Section(sectionName:"Reference information", sequence: 3, lastModBy:wolverine, group: referenceInfoGroup).save(failOnError:true)
		def sectionAddress = new Section(sectionName:"Address", sequence: 1, lastModBy:wolverine, group: addressGroup).save(failOnError:true)
		def sectionTitle = new Section(sectionName:"Title", sequence: 3, lastModBy:wolverine, group: groupTitle).save(failOnError:true)
		def sectionAboutPolicy = new Section(sectionName:"About your policy", sequence: 3, lastModBy:wolverine, group: groupBody).save(failOnError:true)
		def sectionIntroduction = new Section(sectionName:"Introduction", sequence: 3, lastModBy:wolverine, group: groupBody).save(failOnError:true)
		def sectionSubhead = new Section(sectionName:"About your contract", sequence: 2, lastModBy:wolverine, group: groupBody).save(failOnError:true)
		def sectionClosing = new Section(sectionName:"Closing", sequence: 3, lastModBy:wolverine, group: groupClosing).save(failOnError:true)
		def sectionCoupon = new Section(sectionName:"Coupon", sequence: 4, lastModBy:wolverine, group: groupCoupon).save(failOnError:true)
		def sectionTip = new Section(sectionName:"Tip", sequence: 3, lastModBy:wolverine, group: groupMessage).save(failOnError:true)
				
		def salutation = new Module(content : "Hello, Mr. Anderson", status : "Draft", createdBy : gambit, lastModBy : gambit, section : sectionIntroduction).save(failOnError:true)
		def module2 = new Module(content : "sample module #2", status : "Draft", createdBy : wolverine, lastModBy : wolverine, section : sectionTip).save(failOnError:true)
		def module3 = new Module(content : "another module", status : "Draft", createdBy : wolverine, lastModBy : wolverine, section : sectionSubhead).save(failOnError:true)
		
		def businessRule1 = new BusinessRule(rule : "The X-Men is a fictional Marvel Comics superhero team", lastModBy : gambit, module : salutation).save(failOnError:true)
		def businessRule2 = new BusinessRule(rule : "The X-Men #1 was published September 1963", lastModBy : gambit, module : salutation).save(failOnError:true)
		def businessRule3 = new BusinessRule(rule : "The X-Men are born with latent superhuman abilitites", lastModBy : gambit, module : salutation).save(failOnError:true)
		def businessRule4 = new BusinessRule(rule : "The X-Men are led by a wealthy mutant named Professor Xavier", lastModBy : gambit, module : salutation).save(failOnError:true)
		
		def moduleComment1 = new Comment(comment : "Magneto's right: there is a war coming. Are you sure you're on the right side?", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment2 = new Comment(comment : "When they come out... does it hurt?", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment3 = new Comment(comment : "Welcome to Mutant High.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment4 = new Comment(comment : "Mutants are not the ones mankind should fear.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment5 = new Comment(comment : "Welcome to the future.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment6 = new Comment(comment : "What's a Magneto?", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment7 = new Comment(comment : "Mutants are not the ones mankind should fear. 1.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment8 = new Comment(comment : "Mutants are not the ones mankind should fear. 2.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment9 = new Comment(comment : "Mutants are not the ones mankind should fear. 3.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment10 = new Comment(comment : "Mutants are not the ones mankind should fear. 4.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment11 = new Comment(comment : "Mutants are not the ones mankind should fear. 5.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment12 = new Comment(comment : "Mutants are not the ones mankind should fear. 6.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment13 = new Comment(comment : "Mutants are not the ones mankind should fear. 7.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment14 = new Comment(comment : "Mutants are not the ones mankind should fear. 8.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment15 = new Comment(comment : "Mutants are not the ones mankind should fear. 9.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment16 = new Comment(comment : "Mutants are not the ones mankind should fear. 10.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment17 = new Comment(comment : "Mutants are not the ones mankind should fear. 11.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment18 = new Comment(comment : "Mutants are not the ones mankind should fear. 12.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment19 = new Comment(comment : "Mutants are not the ones mankind should fear. 13.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment20 = new Comment(comment : "Mutants are not the ones mankind should fear. 14.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment21 = new Comment(comment : "Mutants are not the ones mankind should fear. 15.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment22 = new Comment(comment : "Mutants are not the ones mankind should fear. 16.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment23 = new Comment(comment : "Mutants are not the ones mankind should fear. 17.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment24 = new Comment(comment : "Mutants are not the ones mankind should fear. 18.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment25 = new Comment(comment : "Mutants are not the ones mankind should fear. 19.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment26 = new Comment(comment : "Mutants are not the ones mankind should fear. 20.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment27 = new Comment(comment : "Mutants are not the ones mankind should fear. 21.", lastModBy : gambit, module : salutation).save(failOnError:true)
		def moduleComment28 = new Comment(comment : "Mutants are not the ones mankind should fear. 22.", lastModBy : gambit, module : salutation).save(failOnError:true)
		
		def modelLeftBar = new Model(templateStyle : "Left Bar", imageLocation:"C://Images").save(failOnError:true)
		def modelCentered = new Model(templateStyle : "Centered", imageLocation:"C://Images").save(failOnError:true)
		def modelCoupon = new Model(templateStyle : "Coupon/Tearoff", imageLocation:"C://Images").save(failOnError:true)
		
		def letterComment1 = new LetterTemplateComment(comment : "Can't wait for the olympics to start?", lastModBy : gambit)
		def letterComment2 = new LetterTemplateComment(comment : "Goods good, melted or notGoods good, melted or notGoods good, melted or notGoods good, melted or notGoods good, melted or notGoods good, melted or notGoods good, melted or notGoods good, melted or not?", lastModBy : gambit)
		
		def letter1 = new LetterTemplate(name:"Recommendation", description:"This is a valid letter of recommendation", category:categoryCarRepair ,status:"Draft",model:modelLeftBar, createdBy: gambit, lastModBy : gambit)
		letter1.addToComments(letterComment1)
		letter1.addToComments(letterComment2)
		letter1.save(failOnError:true)
				
		def ingredient1 = new Ingredient(sequence:1, comment:"You wouldn't hit a man with glasses would you?", lastModBy:gambit, module:salutation, letter:letter1).save(failOnError:true)
		def ingredient2 = new Ingredient(sequence:1, comment:"I Am as far beyond mutants as they are beyond you...", lastModBy:gambit, module:module2, letter:letter1).save(failOnError:true)
		def ingredient3 = new Ingredient(sequence:4, comment:"I'm not following a cartoon character into a loo that glows in the dark", lastModBy:gambit, module:module3, letter:letter1).save(failOnError:true)
		def ingredient4 = new Ingredient(sequence:2, comment:"Mr. Sinister: my name is Sinister..Mr. Sinister!?", lastModBy:gambit, module:salutation, letter:letter1).save(failOnError:true)
		def ingredient5 = new Ingredient(sequence:3, comment:"I wish I was da snake everyone says I am then I leave my past sins and past lives behind me.?", lastModBy:gambit, module:salutation, letter:letter1).save(failOnError:true)
		
		setupChangePolicyPremiumModules(welchb4,sectionTitle,modelLeftBar,categoryCarRepair,sectionIntroduction)
		setupWhatToExpectModules(welchb4)
		setupCouponModule(welchb4)
		setupClosingModule(welchb4)
//		new File("resources/images").eachFile() { file ->
//			new Image(imageBytes: file.readBytes(), fileSize: file.size(), newFilename: file.name, thumbnailFilename: file.name, originalFilename: file.name).save(failOnError:true)
//		}
	}
	
	def setupChangePolicyPremiumModules = { user,sectionTitle,modelLeftBar,categoryCarRepair,sectionIntroduction ->
				
		def premiumChangeModule = new Module(content : "We're changing your policy's premium", status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : sectionTitle).save(failOnError:true)
			
		def premiumChangeModule1 = new Module(content : "<p><span style=\"color: #ff0000;\">[To help you adjust to your new neighborhood, we've selected [agent name] to be your new agent.&nbsp; If you need to contact your new&nbsp;agent, please call&nbsp;[999-999-9999].<br /><br /></span></p>", status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : sectionIntroduction).save(failOnError:true)
		
		def value = "<table style=\"width: 85%;\" border=\"0\" align=\"center\"><tbody><tr><td style=\"width: 50%;\"><img src=\"http://10.5.138.35/Images/NW%20logo%20for%20WIN.bmp\" alt=\"\" width=\"95\" height=\"70\" /><br /><p>&nbsp;</p><p>&nbsp;</p></td><td style=\"text-align: right;\"><table style=\"width: 293px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"><tbody><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Policy number</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [999999999999]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Your agent</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp;&nbsp;[agent name]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp;&nbsp;[agent address]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [agent phone]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\">&nbsp;</td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Payment due by</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [due date]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Amount due</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [\$9999.99]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Amount enclosed</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; \$______________</p></td></tr><tr><td style=\"text-align: left;\" colspan=\"2\" valign=\"bottom\" width=\"293\"><p>Make your check payable to Nationwide Insurance</p></td></tr></tbody></table></td></tr><tr><td>Ways to Pay<br />By mail:&nbsp; Send your check with this coupon<br />By phone:&nbsp; Call [agent phone]<br />In person:&nbsp; Visit a Nationwide representative</td><td style=\"text-align: right;\"><p style=\"text-align: left;\">&nbsp;</p><p style=\"text-align: left;\">||||||||||||||||||||||||||||||||||||<br />Nationwide&nbsp;General Insurance Company<br />PO Box 13958<br />Philadelphia, PA&nbsp; 19101&nbsp;</p></td></tr></tbody></table>"
		def premiumChangeModule2 = new Module(content : value, status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : sectionIntroduction).save(failOnError:true)
		
		def category1 = new Category(name:"Prem",lastModBy: user).save(failOnError:true)
		def letter1 = new LetterTemplate(name:"PremiumLettter", description:"description for premium", category:category1,status:"Draft",model:modelLeftBar, createdBy: user, lastModBy : user).save(failOnError:true)
		
		def ingredient1 = new Ingredient(sequence:1, comment:"Title section", lastModBy:user, module:premiumChangeModule, letter:letter1).save(failOnError:true)
		def ingredient2 = new Ingredient(sequence:2, comment:"Body Stuff", lastModBy:user, module:premiumChangeModule1, letter:letter1).save(failOnError:true)
		def ingredient3 = new Ingredient(sequence:3, comment:"Another ingredient", lastModBy:user, module:premiumChangeModule2, letter:letter1).save(failOnError:true)
	}
	
	
	def setupWhatToExpectModules = { user->
		
		def titleGroup = new SectionGroup(groupName : "WhatToExpectTitle", sequence : 1, lastModBy : user).save(failOnError:true)
		def titleSection = new Section(sectionName : "What to expect", sequence : 1, lastModBy : user, group : titleGroup).save(failOnError:true)
		def premiumChangeModule = new Module(content : "<p><span style=\"color: #ff0000;\">[To help you adjust to your new neighborhood, we've selected [agent name] to be your new agent.&nbsp; If you need to contact your new&nbsp;agent, please call&nbsp;[999-999-9999].<br /><br /></span></p>", status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : titleSection).save(failOnError:true)
	

	}

	def setupCouponModule = { user->
		
		def value = "<table style=\"width: 85%;\" border=\"0\" align=\"center\"><tbody><tr><td style=\"width: 50%;\"><img src=\"http://10.5.138.35/Images/NW%20logo%20for%20WIN.bmp\" alt=\"\" width=\"95\" height=\"70\" /><br /><p>&nbsp;</p><p>&nbsp;</p></td><td style=\"text-align: right;\"><table style=\"width: 293px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"><tbody><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Policy number</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [999999999999]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Your agent</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp;&nbsp;[agent name]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp;&nbsp;[agent address]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [agent phone]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>&nbsp;</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\">&nbsp;</td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Payment due by</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [due date]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Amount due</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; [\$9999.99]</p></td></tr><tr><td style=\"text-align: left;\" valign=\"top\" width=\"107\"><p>Amount enclosed</p></td><td style=\"text-align: left;\" valign=\"top\" width=\"186\"><p>&nbsp;&nbsp;&nbsp; \$______________</p></td></tr><tr><td style=\"text-align: left;\" colspan=\"2\" valign=\"bottom\" width=\"293\"><p>Make your check payable to Nationwide Insurance</p></td></tr></tbody></table></td></tr><tr><td>Ways to Pay<br />By mail:&nbsp; Send your check with this coupon<br />By phone:&nbsp; Call [agent phone]<br />In person:&nbsp; Visit a Nationwide representative</td><td style=\"text-align: right;\"><p style=\"text-align: left;\">&nbsp;</p><p style=\"text-align: left;\">||||||||||||||||||||||||||||||||||||<br />Nationwide&nbsp;General Insurance Company<br />PO Box 13958<br />Philadelphia, PA&nbsp; 19101&nbsp;</p></td></tr></tbody></table>"
		def titleGroup = new SectionGroup(groupName : "Coupon", sequence : 1, lastModBy : user).save(failOnError:true)
		def titleSection = new Section(sectionName : "Coupon", sequence : 1, lastModBy : user, group : titleGroup).save(failOnError:true)
		def premiumChangeModule = new Module(content : value, status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : titleSection).save(failOnError:true)
		
	}
	
	def setupClosingModule = { user->
		
		def value = "<p><br />WPCN-010NR.V1/<span style=\"color: #ff0000;\">[Reference #]</span></p>"
		def titleGroup = new SectionGroup(groupName : "Closing", sequence : 1, lastModBy : user).save(failOnError:true)
		def titleSection = new Section(sectionName : "Closing", sequence : 1, lastModBy : user, group : titleGroup).save(failOnError:true)
		def premiumChangeModule = new Module(content : value, status : "Draft",
			createdBy : user,
			lastModBy : user,
			section : titleSection).save(failOnError:true)
		
	}


		
    def destroy = {
    }
}
