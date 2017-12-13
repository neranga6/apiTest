
<%@ page import="com.nationwide.edm.authoring.Section" %>
<!doctype html>
<html>
	<head>
		
		<r:require module="userManagement"/>
		
		<g:set var="entityName" value="${message(code: 'section.label', default: 'Section')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="show-section" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list section">
			
				<g:if test="${sectionInstance?.sectionName}">
				<li class="fieldcontain">
					<span id="sectionName-label" class="property-label"><g:message code="section.sectionName.label" default="Section Name" /></span>
					
						<span class="property-value" aria-labelledby="sectionName-label"><g:fieldValue bean="${sectionInstance}" field="sectionName"/></span>
					
				</li>
				</g:if>
						
				<g:if test="${sectionInstance?.modules}">
				<li class="fieldcontain">
					<span id="modules-label" class="property-label"><g:message code="section.modules.label" default="Modules" /></span>
					
						<g:each in="${sectionInstance.modules}" var="m">
						<span class="property-value" aria-labelledby="modules-label"><g:link controller="module" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${sectionInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastModTimestamp-label" class="property-label"><g:message code="section.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${sectionInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionInstance?.group}">
				<li class="fieldcontain">
					<span id="group-label" class="property-label"><g:message code="section.group.label" default="Group" /></span>
					
						<span class="property-value" aria-labelledby="group-label"><g:link controller="sectionGroup" action="show" id="${sectionInstance?.group?.id}">${sectionInstance?.group?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
							
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${sectionInstance?.id}" />
					<g:link class="edit" action="edit" id="${sectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
