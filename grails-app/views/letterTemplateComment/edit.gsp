<%@ page import="com.nationwide.edm.authoring.LetterTemplateComment" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'letterTemplateComment.label', default: 'Letter Comment')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="edit-letterTemplateComment" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${letterTemplateCommentInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${letterTemplateCommentInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${letterTemplateCommentInstance?.id}" />
				<g:hiddenField name="version" value="${letterTemplateCommentInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
