<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="letterTemplate.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${letterTemplateInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="letterTemplate.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="500" value="${letterTemplateInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="letterTemplate.status.label" default="Status" />
		
	</label>
	<g:select name="status" from="${letterTemplateInstance.constraints.status.inList}" value="${letterTemplateInstance?.status}" valueMessagePrefix="letterTemplate.status" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'category', 'error')} required">
	<label for="category">
		<g:message code="letterTemplate.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="category" name="category.id" from="${com.nationwide.edm.authoring.Category.list()}" optionKey="id" required="" value="${letterTemplateInstance?.category?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'model', 'error')} required">
	<label for="model">
		<g:message code="letterTemplate.model.label" default="Model" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="model" name="model.id" from="${com.nationwide.edm.authoring.Model.list()}" optionKey="id" required="" value="${letterTemplateInstance?.model?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'createdBy', 'error')} required">
	<label for="createdBy">
		<g:message code="letterTemplate.createdBy.label" default="Created By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="createdBy" name="createdBy.id" from="${com.nationwide.edm.authoring.User.list()}" optionKey="id" required="" value="${letterTemplateInstance?.createdBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'lastModBy', 'error')} required">
	<label for="lastModBy">
		<g:message code="letterTemplate.lastModBy.label" default="Last Mod By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="lastModBy" name="lastModBy.id" from="${com.nationwide.edm.authoring.User.list()}" optionKey="id" required="" value="${letterTemplateInstance?.lastModBy?.id}" class="many-to-one"/>
</div>