package com.nationwide.edm.authoring

import org.springframework.dao.DataIntegrityViolationException

class LetterTemplateCommentController {
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [letterTemplateCommentInstanceList: LetterTemplateComment.list(params), letterTemplateCommentInstanceTotal: LetterTemplateComment.count()]
    }

    def create() {
        [letterTemplateCommentInstance: new LetterTemplateComment(params)]
    }

    def save() {
		
		User currentUser = springSecurityService.getCurrentUser()
		params["lastModBy"]=currentUser
		
        def letterTemplateCommentInstance = new LetterTemplateComment(params)
        if (!letterTemplateCommentInstance.save(flush: true)) {
            render(view: "create", model: [letterTemplateCommentInstance: letterTemplateCommentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), letterTemplateCommentInstance.id])
        redirect(action: "show", id: letterTemplateCommentInstance.id)
    }

    def show() {
        def letterTemplateCommentInstance = LetterTemplateComment.get(params.id)
        if (!letterTemplateCommentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "list")
            return
        }

        [letterTemplateCommentInstance: letterTemplateCommentInstance]
    }

    def edit() {
        def letterTemplateCommentInstance = LetterTemplateComment.get(params.id)
        if (!letterTemplateCommentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "list")
            return
        }

        [letterTemplateCommentInstance: letterTemplateCommentInstance]
    }

    def update() {
        def letterTemplateCommentInstance = LetterTemplateComment.get(params.id)
        if (!letterTemplateCommentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (letterTemplateCommentInstance.version > version) {
                letterTemplateCommentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment')] as Object[],
                          "Another user has updated this LetterTemplateComment while you were editing")
                render(view: "edit", model: [letterTemplateCommentInstance: letterTemplateCommentInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
        letterTemplateCommentInstance.properties = params
		letterTemplateCommentInstance.lastModBy = currentUser

        if (!letterTemplateCommentInstance.save(flush: true)) {
            render(view: "edit", model: [letterTemplateCommentInstance: letterTemplateCommentInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), letterTemplateCommentInstance.id])
        redirect(action: "list")
    }

    def delete() {
        def letterTemplateCommentInstance = LetterTemplateComment.get(params.id)
        if (!letterTemplateCommentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            letterTemplateCommentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
