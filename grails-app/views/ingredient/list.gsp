<%@ page import="com.nationwide.edm.authoring.Ingredient" %>
<!doctype html>
<html>
	<head>
		<r:require module="userManagement"/>
		<g:set var="entityName" value="${message(code: 'ingredient.label', default: 'Ingredient')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="list-ingredient" class="content scaffold-list" role="main">
			<h1>Edit Letter Comments</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th>&nbsp;</th>
						<g:sortableColumn property="id" title="${message(code: 'ingredient.id.label', default: 'ID')}" />
						<g:sortableColumn property="letter.id" title="${message(code: 'ingredient.letter.id.label', default: 'LetterID')}" />
						<th><g:message code="ingredient.comment.label" default="Comments" /></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${ingredientInstanceList}" status="i" var="ingredientInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${ingredientInstance?.id}" />
									<g:link class="edit" action="edit" id="${ingredientInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: ingredientInstance, field: "id")}</td>
						
						<td>${fieldValue(bean: ingredientInstance, field: "letter.id")}</td>
						
						<td>${fieldValue(bean: ingredientInstance, field: "comment")}</td>
												
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ingredientInstanceTotal}" />
			</div>
		</div>
		
	</body>
</html>
