
<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'Template')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-letterTemplate" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th colspan="4"></th>
						
						<th>ID</th>
						
						<g:sortableColumn property="name" title="${message(code: 'letterTemplate.name.label')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'letterTemplate.description.label')}" />
					
						<th><g:message code="default.category.label" /></th>
						
						<th><g:message code="default.lastModBy.label" /></th>
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'default.lastUpdated.label')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${letterTemplateInstanceList}" status="i" var="letterTemplateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<%-- TODO Links are currently non working since those screens don't exist yet --%>
						<td><g:link action="preview" id="${letterTemplateInstance?.id}">Preview</g:link></td>
						
						<td><g:link action="review" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.review.link" /></g:link></td>
						
						<td><g:link action="editStructure" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.structure.link" /></g:link></td>
						
						<td><g:link action="report" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.report.link" /></g:link></td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "id")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "category.name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${letterTemplateInstance.lastUpdated}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${letterTemplateInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
