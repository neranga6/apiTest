<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<r:require module="letterBuilder"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
					
		<div id="create-letterTemplate" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<h2>Template Builder for TN # <span id="letterTemplateId"><g:fieldValue bean="${letterTemplateInstance}" field="id"/></span> |  <g:link action="preview" id="${letterTemplateInstance.id}">Preview this template</g:link></h2>
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
			<g:form action="save" >
				<div id="letterBorder">
					<div id="top">
						<table id="topSection">
							<tbody>
	                            <tr>
	                                <td>
	                                     	<g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'logo']">Select Logo Modules
             								</g:remoteLink>
	                                    <br />
	                                   	<g:render template="ingredientTemplate" collection="${logoList}" var="ingredientModule"/>
	                                    <div class="refaddress">
	                                        <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'address']">Select Address Modules
             								</g:remoteLink>
	                                        <br />
	                                        <g:render template="ingredientTemplate" collection="${addressList}" var="ingredientModule"/>
	                                    </div>
	                                </td>
	                                <td>
	                                     <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'referenceInformation']">Select Reference Information Modules
             								</g:remoteLink>
	                                    <br />
	                                    <g:render template="ingredientTemplate" collection="${refInfoList}" var="ingredientModule"/>
	                                </td>
	                            </tr>
                            </tbody>
                        </table>
                     
                    </div> 
                 	<div class="centerArea">
		                  <table>
			                  <tbody>
				                  <tr>
					                  <td class="noLine">
						                   <div class="leftNav">
						                         <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'title']">Select Title Modules
             									</g:remoteLink>
						                        <br />
						                       <g:render template="ingredientTemplate" collection="${titleList}" var="ingredientModule"/>
						                        <br /><br /><br /><br />
						                         <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'advocacy']">Select Advocacy Modules
             								</g:remoteLink>
						                        <br />
						                       <g:render template="ingredientTemplate" collection="${advMessageList}" var="ingredientModule"/>
						                    </div>
						                   
					              	</td>
				              		<td class="noLine">
					                    <div class="bodyWithLeftNav">
					                       	
					                        <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'body']">Select Body Modules
             								</g:remoteLink>
					                        <br />
					                        <g:render template="ingredientTemplate" collection="${bodyList}" var="ingredientModule"/>
					                         <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'closing']">Select Closing Modules
             								</g:remoteLink>
					                        <br />
					                       <g:render template="ingredientTemplate" collection="${closingList}" var="ingredientModule"/>
					                   </div>
				                   </td>
			                  	</tr>
			                  </tbody>
			                  
		                  </table>
		            	</div>
		                   
                   
                  
<%--                   <fieldset class="buttons">--%>
<%--					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />--%>
<%--					</fieldset>--%>
                </div>
				
			</g:form>
			<g:render template="moduleSelectionModal"/>
			
		</div>
	</body>
</html>
