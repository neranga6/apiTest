<%@ page import="com.nationwide.edm.authoring.Category" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Edit Categories')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<g:link controller="category" action="create" class="button">Add Category</g:link>
		<div id="list-category" class="content scaffold-list" role="main">
			<br/>
			<h3><g:message code="default.list.label" args="[entityName]" class="boxclass"/></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th>&nbsp</th>
						<g:sortableColumn property="name" title="${message(code: 'category.name.label', default: 'Name')}" />
						<th><g:message code="category.lastModBy.label" default="Last Mod By" /></th>
						<g:sortableColumn property="lastUpdated" title="${message(code: 'category.lastUpdated.label', default: 'Last Updated')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${categoryInstance?.id}" />
									<g:link class="edit" action="edit" id="${categoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: categoryInstance, field: "name")}</td>	
						<td>${fieldValue(bean: categoryInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${categoryInstance.lastUpdated}" /></td>				
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${categoryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
