
<%@ page import="com.nationwide.edm.authoring.BusinessRule" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'businessRule.label', default: 'BusinessRule')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		
		<r:require module="userManagement"/>
			
		
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="show-businessRule" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list businessRule">
			
				<g:if test="${businessRuleInstance?.rule}">
				<li class="fieldcontain">
					<span id="rule-label" class="property-label"><g:message code="businessRule.rule.label" default="Rule" /></span>
					
						<span class="property-value" aria-labelledby="rule-label"><g:fieldValue bean="${businessRuleInstance}" field="rule"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${businessRuleInstance?.lastModBy}">
				<li class="fieldcontain">
					<span id="lastModBy-label" class="property-label"><g:message code="businessRule.lastModBy.label" default="Last Mod By" /></span>
					
						<span class="property-value" aria-labelledby="lastModBy-label"><g:link controller="user" action="show" id="${businessRuleInstance?.lastModBy?.id}">${businessRuleInstance?.lastModBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${businessRuleInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="businessRule.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${businessRuleInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${businessRuleInstance?.module}">
				<li class="fieldcontain">
					<span id="module-label" class="property-label"><g:message code="businessRule.module.label" default="Module" /></span>
					
						<span class="property-value" aria-labelledby="module-label"><g:link controller="module" action="show" id="${businessRuleInstance?.module?.id}">${businessRuleInstance?.module?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${businessRuleInstance?.id}" />
					<g:link class="edit" action="edit" id="${businessRuleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
