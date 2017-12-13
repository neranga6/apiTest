<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<r:require module="reviewLetter"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title>Review &amp; Edit Letter</title>
	</head>
	<body>
	
	<h1>Review &amp; Edit Template #<g:fieldValue bean="${letterTemplateInstance}" field="id"/></h1>
	
	
	<g:link action="preview" id="${letterTemplateInstance?.id}">Preview Letter</g:link><br/><br/>
	<a href="#" onClick="toggleAddTemplateComment();">Toggle Comments</a><br/><br/>
	
	<div id="reviewAndEditCommentList">
		
			<table id="reviewAndEditCommentListTable">
				<g:each in="${commentInstanceList}" status="i" var="commentInstance">
				<tr>
					<td>Added by:  ${commentInstance.lastModBy} on <g:formatDate date="${commentInstance.dateCreated}" /></td>
				</tr>
				<tr>
					<td>${fieldValue(bean: commentInstance, field: "comment")}</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				</g:each>
				
			</table>
		<fieldset class="buttons">
			<button id="addLetterCommentButton" class="save" type="button">Add Comment</button>
				
		</fieldset>
	</div>
	<br/>
	<%-- start of the Edit form --%>
	<div id="edit-letterTemplate" class="content scaffold-edit" role="main">
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
		<g:form method="post" >
			<g:hiddenField name="id" value="${letterTemplateInstance?.id}" />
			<g:hiddenField name="version" value="${letterTemplateInstance?.version}" />
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			<fieldset class="buttons">
				<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
			</fieldset>
		</g:form>
	</div>
	<%-- end of the Edit form --%>
	
	<g:each var="module" in="${modules}">
	
	<h2><g:fieldValue bean="${module}" field="section.group.groupName"/></h2>
	<table class="tableData" id="reviewLetterTable">
		<tbody>
			<tr>
				<th style="width: 50%;">
					Module #<g:fieldValue bean="${module}" field="id"/> Content
				</th>
				<th>
					Comments
				</th>		
			</tr>
			<tr>
				<td style="vertical-align: top;">
					${module.content}
				</td>
				<td>
					<!-- load up Comments in a scrollable iframe -->
					<iframe width="100%" src='<g:createLink controller="comment" action="listForModule" id="${module.id}" absolute="true"/>' frameborder="0"></iframe>
				</td>
			</tr>
			<tr>
				<td><g:link controller="module" action="edit" id="${module.id}">Edit Module</g:link></td>
				<td><g:link controller="comment" action="create" params='["module.id": "${module.id}"]'>Add a Comment</g:link></td>			
			</tr>
			<tr>
				<th colspan="2">Business Rules</th>
			</tr>
			<tr>
				<td colspan="2">
					<g:if test="${module.businessRules}">
						<ul>
						<g:each var="businessRule" in="${module.businessRules.sort{it.id}}">
							<li><g:fieldValue bean="${businessRule}" field="rule"/></li>
						</g:each>
						</ul>
					</g:if>
				</td>
			</tr>
		</tbody>
	</table>
	</g:each>
	<g:render template="addCommentModal"/>
	</body>
	
</html>