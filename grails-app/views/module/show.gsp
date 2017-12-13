
<%@ page import="com.nationwide.edm.authoring.Module" %>
<!doctype html>
<html>
	<head>
		
		<r:require module="moduleScreen"/>
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-module" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list module">
			
				<g:if test="${moduleInstance?.content}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="module.content.label" default="Content" /></span>
					
						<span class="property-value" aria-labelledby="content-label"><g:fieldValue bean="${moduleInstance}" field="content"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="module.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${moduleInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.comments}">
				<li class="fieldcontain">
					<span id="comments-label" class="property-label"><g:message code="module.comments.label" default="Comments" /></span>
					
						<g:each in="${moduleInstance.comments}" var="c">
						<span class="property-value" aria-labelledby="comments-label"><g:link controller="comment" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.businessRules}">
				<li class="fieldcontain">
					<span id="businessRules-label" class="property-label"><g:message code="module.businessRules.label" default="Business Rules" /></span>
					
						<g:each in="${moduleInstance.businessRules}" var="b">
						<span class="property-value" aria-labelledby="businessRules-label"><g:link controller="businessRule" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.ingredients}">
				<li class="fieldcontain">
					<span id="ingredients-label" class="property-label"><g:message code="module.ingredients.label" default="Ingredients" /></span>
					
						<g:each in="${moduleInstance.ingredients}" var="i">
						<span class="property-value" aria-labelledby="ingredients-label"><g:link controller="ingredient" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="module.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:link controller="user" action="show" id="${moduleInstance?.createdBy?.id}">${moduleInstance?.createdBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="module.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${moduleInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.lastModBy}">
				<li class="fieldcontain">
					<span id="lastModBy-label" class="property-label"><g:message code="module.lastModBy.label" default="Last Mod By" /></span>
					
						<span class="property-value" aria-labelledby="lastModBy-label"><g:link controller="user" action="show" id="${moduleInstance?.lastModBy?.id}">${moduleInstance?.lastModBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="module.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${moduleInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${moduleInstance?.section}">
				<li class="fieldcontain">
					<span id="section-label" class="property-label"><g:message code="module.section.label" default="Section" /></span>
					
						<span class="property-value" aria-labelledby="section-label"><g:link controller="section" action="show" id="${moduleInstance?.section?.id}">${moduleInstance?.section?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${moduleInstance?.id}" />
					<g:link class="edit" action="edit" id="${moduleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
