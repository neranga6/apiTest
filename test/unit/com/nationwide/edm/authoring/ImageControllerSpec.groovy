package com.nationwide.edm.authoring

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.domain.*

import grails.test.mixin.support.*

import org.codehaus.groovy.grails.plugins.testing.GrailsMockMultipartFile
import org.junit.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(ImageController)
@Mock(Image)
class ImageControllerSpec extends Specification{

	private static final FILE_NAME = "myFile.jpg"
	
	def "Test JSON response when uploading a new image"(){
		
		setup: "A single file in the request"
			mockDomain(Image)
			GrailsMockMultipartFile mockedFile = new GrailsMockMultipartFile(FILE_NAME,"foo".bytes)
			mockedFile.metaClass.originalFilename = FILE_NAME
					
			request.addFile(mockedFile)
			
		when: "The file is uploaded"
			controller.upload()
		
		then: "JSON response should contain appropriate links and file details"
			response.json.name[0] == FILE_NAME
			response.json.url[0] == '/image/picture/1'
			response.json.thumbnail_url[0] == '/image/thumbnail/1'
			
			//mockedFile.targetFileLocation.path == ".\\uploads\\"	
			
	}
	
	def "Test retrieving full picture"() {
		
		setup: "An image exists in the db"
		mockDomain(Image)		
		generateTestImage().save()
		
		when: "The full file is requested"
		params["id"] = 1
		controller.picture()
		
		then: "The file should be available to the view"
		response.text == "CyclopsKnowsYourName"
		
	}
	
	def "Test retrieving picture thumbnail"() {
		
		setup: "An image exists in the db"
		mockDomain(Image)		
		generateTestImage().save()
		
		when: "The thumbnail is requested"
		params["id"] = 1
		controller.thumbnail()
		
		then: "The thumbnail should be available to the view"
		response.text =="CyclopsKnowsYourName"
	}
	
	private Image generateTestImage(){
		Image newImage = new Image(
			originalFilename: 'originalFile.jpg',
			thumbnailFilename: 'originalFile.jpg',
			newFilename: 'originalFile.jpg',
			imageBytes: "CyclopsKnowsYourName".bytes,
			fileSize: 3
		)
	}
}
