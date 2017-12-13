package com.nationwide.edm.authoring

import org.springframework.dao.DataIntegrityViolationException

class CommentService {
	
	def createComment(params) {
		log.trace "called createComment()"
		new Comment(params)
	}
	
	def saveComment(Comment commentInstance) {
		log.trace "called saveComment()"
		return commentInstance.save(flush: true)
	}
	
	/**
	 * Delete a Comment instance and return whether the operation was successful or not.
	 */
	boolean deleteComment(Comment commentInstance) {
		boolean deleteSuccess = false
		try {
			commentInstance.delete(flush: true)
			deleteSuccess = true
		} catch (DataIntegrityViolationException e) {
			log.error("There was an error when deleting Comment with ID ${commentInstance.id}", e)
			deleteSuccess = false
		}
		return deleteSuccess
	}
}
