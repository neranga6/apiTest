
<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-letterTemplate" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list letterTemplate">
			
				<g:if test="${letterTemplateInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="letterTemplate.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${letterTemplateInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="letterTemplate.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${letterTemplateInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="letterTemplate.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${letterTemplateInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.category}">
				<li class="fieldcontain">
					<span id="category-label" class="property-label"><g:message code="letterTemplate.category.label" default="Category" /></span>
					
						<span class="property-value" aria-labelledby="category-label"><g:link controller="category" action="show" id="${letterTemplateInstance?.category?.id}">${letterTemplateInstance?.category?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="letterTemplate.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:link controller="user" action="show" id="${letterTemplateInstance?.createdBy?.id}">${letterTemplateInstance?.createdBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="letterTemplate.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${letterTemplateInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.lastModBy}">
				<li class="fieldcontain">
					<span id="lastModBy-label" class="property-label"><g:message code="letterTemplate.lastModBy.label" default="Last Mod By" /></span>
					
						<span class="property-value" aria-labelledby="lastModBy-label"><g:link controller="user" action="show" id="${letterTemplateInstance?.lastModBy?.id}">${letterTemplateInstance?.lastModBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="letterTemplate.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${letterTemplateInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.model}">
				<li class="fieldcontain">
					<span id="model-label" class="property-label"><g:message code="letterTemplate.model.label" default="Model" /></span>
					
						<span class="property-value" aria-labelledby="model-label"><g:link controller="model" action="show" id="${letterTemplateInstance?.model?.id}">${letterTemplateInstance?.model?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${letterTemplateInstance?.recipe}">
				<li class="fieldcontain">
					<span id="recipe-label" class="property-label"><g:message code="letterTemplate.recipe.label" default="Recipe" /></span>
					
						<g:each in="${letterTemplateInstance.recipe}" var="r">
						<span class="property-value" aria-labelledby="recipe-label"><g:link controller="ingredient" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${letterTemplateInstance?.id}" />
					<g:link class="edit" action="edit" id="${letterTemplateInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
