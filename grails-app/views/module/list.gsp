
<%@ page import="com.nationwide.edm.authoring.Module" %>
<!doctype html>
<html>
	<head>
		<r:require module="moduleScreen"/>
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-module" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="id" title="${message(code: 'module.content.label', default: 'ID')}" />
						
						<g:sortableColumn property="section.sectionName" title="${message(code: 'module.content.label', default: 'Section')}" />
						
						<g:sortableColumn property="content" title="${message(code: 'module.status.label', default: 'Content')}" />
					
						<th><g:message code="module.createdBy.label" default="Created By" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'module.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="module.lastModBy.label" default="Last Mod By" /></th>
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'module.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${moduleInstanceList}" status="i" var="moduleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${moduleInstance.id}">${fieldValue(bean: moduleInstance, field: "id")}</g:link></td>
					
						<td>${fieldValue(bean: moduleInstance, field: "section.sectionName")}</td>
						
						<td>${moduleInstance.content}</td>
					
						<td>${fieldValue(bean: moduleInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${moduleInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: moduleInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${moduleInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${moduleInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
