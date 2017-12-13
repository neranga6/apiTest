
<%@ page import="com.nationwide.edm.authoring.Ingredient" %>
<!doctype html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'ingredient.label', default: 'Ingredient')}" />
		
		
		<r:require module="userManagement"/>
			
		
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="show-ingredient" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list ingredient">
			
				<g:if test="${ingredientInstance?.sequence}">
				<li class="fieldcontain">
					<span id="sequence-label" class="property-label"><g:message code="ingredient.sequence.label" default="Sequence" /></span>
					
						<span class="property-value" aria-labelledby="sequence-label"><g:fieldValue bean="${ingredientInstance}" field="sequence"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="ingredient.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${ingredientInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.lastModBy}">
				<li class="fieldcontain">
					<span id="lastModBy-label" class="property-label"><g:message code="ingredient.lastModBy.label" default="Last Mod By" /></span>
					
						<span class="property-value" aria-labelledby="lastModBy-label"><g:link controller="user" action="show" id="${ingredientInstance?.lastModBy?.id}">${ingredientInstance?.lastModBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastModTimestamp-label" class="property-label"><g:message code="ingredient.lastModTimestamp.label" default="Last Mod Timestamp" /></span>
					
						<span class="property-value" aria-labelledby="lastModTimestamp-label"><g:formatDate date="${ingredientInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.letter}">
				<li class="fieldcontain">
					<span id="letter-label" class="property-label"><g:message code="ingredient.letter.label" default="Letter" /></span>
					
						<span class="property-value" aria-labelledby="letter-label"><g:link controller="letterTemplate" action="show" id="${ingredientInstance?.letter?.id}">${ingredientInstance?.letter?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingredientInstance?.module}">
				<li class="fieldcontain">
					<span id="module-label" class="property-label"><g:message code="ingredient.module.label" default="Module" /></span>
					
						<span class="property-value" aria-labelledby="module-label"><g:link controller="module" action="show" id="${ingredientInstance?.module?.id}">${ingredientInstance?.module?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ingredientInstance?.id}" />
					<g:link class="edit" action="edit" id="${ingredientInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
