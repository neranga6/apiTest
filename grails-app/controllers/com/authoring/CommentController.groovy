package com.authoring

class CommentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def commentService
	def springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [commentInstanceList: Comment.list(params), commentInstanceTotal: Comment.count()]
    }

	/**
	 * Get all the comments for one module.
	 */
	def listForModule() {
        def moduleInstance = Module.get(params.id)
        if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }
		
		def commentInstanceList = moduleInstance.comments?.sort{
			it.lastUpdated
		}
		
		[commentInstanceList: commentInstanceList, commentInstanceTotal: commentInstanceList?.size() ?: 0]
	}
	
    def create() {
        [commentInstance: commentService.createComment(params)]
    }

    def save() {
		
		User currentUser = springSecurityService.getCurrentUser()
		params["lastModBy"]=currentUser
		
        def commentInstance = commentService.createComment(params)
        if (!commentService.saveComment(commentInstance)) {
            render(view: "create", model: [commentInstance: commentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.id])
        redirect(action: "show", id: commentInstance.id)
    }

    def show() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        [commentInstance: commentInstance]
    }

    def edit() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        [commentInstance: commentInstance]
    }

    def update() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (commentInstance.version > version) {
                commentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'comment.label', default: 'Comment')] as Object[],
                          "Another user has updated this Comment while you were editing")
                render(view: "edit", model: [commentInstance: commentInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
        commentInstance.properties = params
		commentInstance.lastModBy = currentUser

        if (!commentService.saveComment(commentInstance)) {
            render(view: "edit", model: [commentInstance: commentInstance])
            return
        }

		flash.message = message(code: 'moduleCommentUpdate.label', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.module?.id])
        redirect(action: "list")
    }

    def delete() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

		if(commentService.deleteComment(commentInstance)) {
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
			redirect(action: "list")
		} else {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
			redirect(action: "show", id: params.id)
		}
    }
}
