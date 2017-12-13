<%@ page import="com.nationwide.edm.authoring.Category" %>
<!doctype html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
				
		
		<r:require module="userManagement"/>
				
				
		<title>Add Category</title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		
		<br/>
		<div id="create-category" class="content scaffold-create" role="main">
			<h3><g:message code="default.create.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${categoryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${categoryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
