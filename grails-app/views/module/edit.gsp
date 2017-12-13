<%@ page import="com.nationwide.edm.authoring.Module" %>
<!doctype html>
<html>
	<head>
		<r:require module="moduleScreen"/>
		<r:require modules="bootstrap-file-upload"/>
		<r:require module="fileuploader"/>
		<g:javascript src="../tiny_mce/tiny_mce.js"/>
		<g:javascript src="richtext.js"/>
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="edit-module" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /> #<g:fieldValue bean="${moduleInstance}" field="id"/></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${moduleInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${moduleInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${moduleInstance?.id}" />
				<g:hiddenField name="version" value="${moduleInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Save Module')}" />&nbsp;					
					<g:actionSubmit class="copy" action="copy" value="${message(code: 'default.button.copy.label', default: 'Copy Module')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete Module')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
			<div id="imageUploadPanel">
				<h3>Image Upload</h3>
				<bsfu:fileUpload action="upload" controller="image" maxNumberOfFiles="1" forceIframeTransport="true" autoUpload="true"/>
			</div>
		</div>
	</body>
</html>
