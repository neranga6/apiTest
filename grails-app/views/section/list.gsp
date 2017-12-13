<%@ page import="com.nationwide.edm.authoring.Section" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'section.label', default: 'Section')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<g:link controller="section" action="create" class="button">Add Section</g:link>
		<div id="list-section" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th>&nbsp;</th>
						
						<g:sortableColumn property="sectionName" title="${message(code: 'section.sectionName.label', default: 'Section')}" />
						<th><g:message code="category.lastModBy.label" default="Last Mod By" /></th>
						<g:sortableColumn property="lastUpdated" title="${message(code: 'category.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${sectionInstanceList}" status="i" var="sectionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${sectionInstance?.id}" />
									<g:link class="edit" action="edit" id="${sectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: sectionInstance, field: "sectionName")}</td>
						<td>${fieldValue(bean: sectionInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${sectionInstance.lastUpdated}" /></td>				
															
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${sectionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
