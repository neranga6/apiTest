
<%@ page import="com.nationwide.edm.authoring.Category" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
				
		
		<r:require module="userManagement"/>
			
		
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<div id="show-category" class="content scaffold-show" role="main">
			<br/>
			<h3><g:message code="default.show.label" args="[entityName]" /></h3>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table border="1">
		
			
				
				<g:if test="${categoryInstance?.name}">
				<tr><td>
				
					<span id="name-label" class="property-label"><g:message code="category.name.label" default="Name" /></span>
					</td>
					<td>
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${categoryInstance}" field="name"/></span>
					</td>
				
				
				
				</g:if>
				</tr>
				
					
		
			</table>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${categoryInstance?.id}" />
					<g:link class="edit" action="edit" id="${categoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		
	</body>
</html>
