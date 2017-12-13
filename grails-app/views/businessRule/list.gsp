<%@ page import="com.nationwide.edm.authoring.BusinessRule" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'businessRule.label', default: 'Edit BusinessRules')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="list-businessRule" class="content scaffold-list" role="main">
			<h1>${message(code: 'business.rule.list.title', default: 'Edit/View Business Rules')}</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th>&nbsp</th>
						<g:sortableColumn property="module.id" title="${message(code: 'businessRule.module.id.label', default: 'Module ID')}" />
						<th><g:message code="businessRule.rule.label" default="Business Rule" /></th>
						<g:sortableColumn property="lastModBy" title="${message(code: 'businessRule.lastModBy.label', default: 'Created By')}" />
						<g:sortableColumn property="dateCreated" title="${message(code: 'businessRule.dateCreated.label', default: 'Created Date')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${businessRuleInstanceList}" status="i" var="businessRuleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${businessRuleInstance?.id}" />
									<g:link class="edit" action="edit" id="${businessRuleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
							
						<td>${fieldValue(bean: businessRuleInstance, field: "module.id")}</td>
						
						<td>${fieldValue(bean: businessRuleInstance, field: "rule")}</td>
						
						<td>${fieldValue(bean: businessRuleInstance, field: "lastModBy.username")}</td>
					
						<td><g:formatDate date="${businessRuleInstance.dateCreated}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${businessRuleInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
