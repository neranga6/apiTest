
<%@ page import="com.nationwide.edm.authoring.LetterTemplateComment" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'letterTemplateComment.label', default: 'LetterTemplateComment')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="show-letterTemplateComment" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list letterTemplateComment">
			
				<g:if test="${letterTemplateCommentInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="letterTemplateComment.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${letterTemplateCommentInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateCommentInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="letterTemplateComment.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${letterTemplateCommentInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateCommentInstance?.lastModBy}">
				<li class="fieldcontain">
					<span id="lastModBy-label" class="property-label"><g:message code="letterTemplateComment.lastModBy.label" default="Last Mod By" /></span>
					
						<span class="property-value" aria-labelledby="lastModBy-label"><g:link controller="user" action="show" id="${letterTemplateCommentInstance?.lastModBy?.id}">${letterTemplateCommentInstance?.lastModBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateCommentInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="letterTemplateComment.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${letterTemplateCommentInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateCommentInstance?.letterTemplate}">
				<li class="fieldcontain">
					<span id="letterTemplate-label" class="property-label"><g:message code="letterTemplateComment.letterTemplate.label" default="Letter Template" /></span>
					
						<span class="property-value" aria-labelledby="letterTemplate-label"><g:link controller="letterTemplate" action="show" id="${letterTemplateCommentInstance?.letterTemplate?.id}">${letterTemplateCommentInstance?.letterTemplate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${letterTemplateCommentInstance?.id}" />
					<g:link class="edit" action="edit" id="${letterTemplateCommentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
