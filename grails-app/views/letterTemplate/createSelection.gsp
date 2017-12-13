<!doctype html>
<html>
	<head>
		<r:require modules="home, createSelection" />
	</head>
	<body>
		<div class="mainPageColumnLeft">
            <div class="colHeader1" >
                <h2><span class="alternateTextColor">Templates</span></h2>
             </div>
        </div>
	    <h3>Add New Template</h3>
        <b>Build New Template From:</b>
        <br />
        <br />
     	<div id="createTemplateSelect">
     		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${letterTemplateInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${letterTemplateInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
	     	<g:form id="createTemplateSelectionForm" action="create" >
		        <g:radioGroup name="letterTemplateGroup" labels="['Model', 'Existing Letter']" values="['new','existing']" value="new" >
					<p class="radioButton"><g:message code="${it.label}" />: ${it.radio}</p>
				</g:radioGroup>
				<br />
				<br />
				 
				<label for="newModelTemplate">
					<g:message code="model.selection.label" default="Select model to use" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
				<g:select id="newModelTemplate" name="model.id" from="${com.nationwide.edm.authoring.Model.list()}" optionKey="id" value="${model?.id}"
		          name="newModelTemplate" noSelection="['':'-select-']"/>
		        <g:select style='display:none;' id="modelExistingTemplate" name="letterTemplate.id" from="${com.nationwide.edm.authoring.LetterTemplate.list()}" optionKey="id" value="${letterTemplate?.id}"
		          name="modelExistingTemplate" noSelection="['':'-select-']"/>
		        <br />
				<br /> 
				<div id="modelImage"></div>
		        <label for="templateName">
					<g:message code="model.templateName.label" default="Enter Template Name:" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
		         <g:textField name="templateName" value="" />
		         
		        <br />
				<br /> 
		        <label for="templateDescription">
					<g:message code="model.templateDescription.label" default="Description (Audience & Intent):" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
		         <g:textArea name="templateDescription" value="" />
		         
		        <br />
				<br />
				<label for="category">
					<g:message code="letterTemplate.category.label" default="Category" />
					<span class="required-indicator">*</span>
				</label>
				<br />
				<g:select id="category" name="categoryId" from="${com.nationwide.edm.authoring.Category.list()}" optionKey="id" value="${category?.id}" noSelection="['':'-select-']"/>
				<br/>
				<br/>
						
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.add.letter.label', default: 'Add Letter')}" />
				</fieldset>
			</g:form>
		</div>
					 
	</body>
</html>