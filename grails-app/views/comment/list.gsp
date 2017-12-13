<%@ page import="com.nationwide.edm.authoring.Comment" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'comment.label', default: 'Comment')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="list-comment" class="content scaffold-list" role="main">
			<h1>Edit Module Comments</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableBorder">
				<thead>
					<tr>
						<th>&nbsp</th>
						<g:sortableColumn property="module.id" title="${message(code: 'comment.module.id.label', default: 'Module ID')}" />
						<th><g:message code="comment.comment.label" default="Comments" /></th>
						<g:sortableColumn property="lastModBy" title="${message(code: 'comment.lastModBy.label', default: 'Created By')}" />
						<g:sortableColumn property="dateCreated" title="${message(code: 'comment.dateCreated.label', default: 'Created Date')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${commentInstanceList}" status="i" var="commentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${commentInstance?.id}" />
									<g:link class="edit" action="edit" id="${commentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: commentInstance, field: "module.id")}</td>
						
						<td>${fieldValue(bean: commentInstance, field: "comment")}</td>
						
						<td>${fieldValue(bean: commentInstance, field: "lastModBy.username")}</td>
					
						<td><g:formatDate date="${commentInstance.dateCreated}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${commentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
