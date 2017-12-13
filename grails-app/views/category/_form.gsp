<%@ page import="com.nationwide.edm.authoring.Category" %>
<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="category.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField class="adminTextField" name="name" maxlength="50" required="" value="${categoryInstance?.name}"/>
</div>
	

