package com.nationwide.edm.authoring

class Image {
	
	String originalFilename
	String thumbnailFilename
	String newFilename
	Byte[] imageBytes
	Long fileSize

    static constraints = {
		//imageBytes(maxSize:1073741824) // max 4GB file
		imageBytes nullable:true

    }
	
	static mapping = {
		//TODO:  Determine final size for db
		imageBytes column: "IMAGE_BYTES", length:1024000
	}
}
