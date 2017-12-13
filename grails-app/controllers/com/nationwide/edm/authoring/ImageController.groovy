package com.nationwide.edm.authoring

import grails.converters.JSON

import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

class ImageController {
	
    def upload() {
        switch(request.method){
            case "GET":
				// the Bootstrap Image Uploader calls this action for its image list, which we want to be blank, so this always returns no results.
                def results = []
                render results as JSON
                break;
            case "POST":
                def results = []
                if (request instanceof MultipartHttpServletRequest){
                    for(filename in request.getFileNames()){
                        MultipartFile file = request.getFile(filename)
                        String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))

						Image pic = new Image(
                                originalFilename: file.originalFilename,
                                thumbnailFilename: file.originalFilename,
                                newFilename: file.originalFilename,
                                fileSize: file.size,
								imageBytes: file.bytes
                        ).save()
						
                        results << [
                                name: pic.originalFilename,
                                size: pic.fileSize,
                                url: createLink(controller:'image', action:'picture', id: pic.id),
                                thumbnail_url: createLink(controller:'image', action:'thumbnail', id: pic.id),
                                delete_url: createLink(controller:'image', action:'delete', id: pic.id),
                                delete_type: "DELETE"
                        ]
                    }
                }
				
				/*
				 * This is to fix a known bug when using Internet Explorer...
				 * 
				 * @see https://github.com/blueimp/jQuery-File-Upload/wiki/Frequently-Asked-Questions
				 * "Why does Internet Explorer prompt to download a file after the upload completes?"
				 * 
				 * @see https://github.com/blueimp/jQuery-File-Upload/wiki/Setup
				 * "Iframe based uploads require a Content-type of text/plain or text/html for the JSON response - they will show an undesired download dialog if the iframe response is set to application/json."
				 */
				def resultsJson = results as JSON
				render(contentType: "text/plain", text: resultsJson.toString())
                break;
            default: render status: HttpStatus.METHOD_NOT_ALLOWED.value()
        }
    }

	private void getImage(imageId) {
		def pic = Image.get(imageId)
		response.outputStream << new ByteArrayInputStream(pic.imageBytes)
		response.outputStream.flush()
	}
	
    def picture() {
		getImage(params.id)
		return
    }

    def thumbnail() {
		getImage(params.id)
		return
    }

    def delete(){
        def pic = Image.get(params.id)        
        pic.delete()

        def result = [success: true]
        render result as JSON
    }
	
	def list() {
        def results = []
        Image.findAll().each { Image picture ->
            results << [
                    name: picture.originalFilename,
                    size: picture.fileSize,
                    url: createLink(controller:'image', action:'picture', id: picture.id),
                    thumbnail_url: createLink(controller:'image', action:'thumbnail', id: picture.id),
                    delete_url: createLink(controller:'image', action:'delete', id: picture.id),
                    delete_type: "DELETE"
            ]
        }
        render results as JSON
	}
 
}
