<%@ page import="com.nationwide.edm.authoring.LetterTemplateComment" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'letterTemplateComment.label', default: 'Letter Comment')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="list-letterTemplateComment" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableBorder">
				<thead>
					<tr>
						<th>&nbsp</th>
						<g:sortableColumn property="id" title="${message(code: 'letterTemplateComment.content.label', default: 'Letter ID')}" />
						<th><g:message code="letterTemplate.comment.label" default="Comments" /></th>
						<g:sortableColumn property="lastModBy" title="${message(code: 'letterTemplateComment.lastModBy.label', default: 'Last Mod By')}" />
						<g:sortableColumn property="lastUpdated" title="${message(code: 'letterTemplateComment.dateCreated.label', default: 'Date Updated')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${letterTemplateCommentInstanceList}" status="i" var="letterTemplateCommentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${letterTemplateCommentInstance?.id}" />
									<g:link class="edit" action="edit" id="${letterTemplateCommentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						<td>${fieldValue(bean: letterTemplateCommentInstance, field: "id")}</td>
						<td>${fieldValue(bean: letterTemplateCommentInstance, field: "comment")}</td>
						<td>${fieldValue(bean: letterTemplateCommentInstance, field: "lastModBy.username")}</td>
						<td><g:formatDate date="${letterTemplateCommentInstance.lastUpdated}" /></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${letterTemplateCommentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
