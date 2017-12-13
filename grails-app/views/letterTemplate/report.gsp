<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	
	<div>
	<g:if test="${letterTemplateInstance?.name}">
		<span id="name-label" class="property-label"><g:message code="letterTemplate.name.label" />:</span>
		<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${letterTemplateInstance}" field="name"/></span>
	</g:if>
	</div>
	
	<div>
	<g:if test="${letterTemplateInstance?.description}">
		<span id="description-label" class="property-label"><g:message code="letterTemplate.description.label" />:</span>
		<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${letterTemplateInstance}" field="description"/></span>
	</g:if>
	</div>
	
	<div>
	<g:if test="${letterTemplateInstance?.category}">
		<span id="category-label" class="property-label"><g:message code="letterTemplate.category.label" />:</span>
		<span class="property-value" aria-labelledby="category-label"><g:fieldValue bean="${letterTemplateInstance}" field="category"/></span>
	</g:if>
	</div>
	
	<table class="tableData">
		<thead>
			<tr>
				<th>Group</th>
				<th>Module</th>
				<th>Content</th>
				<th>Business Rules</th>
			</tr>
		</thead>
		<tbody>
			<g:each var="module" in="${modules}">
				<tr>
					<td><g:fieldValue bean="${module}" field="section.group.groupName"/></td>
					<td><g:fieldValue bean="${module}" field="id"/></td>
					<td>${module.content}</td>
					<td>
						<g:if test="${module.businessRules}">
							<ul>
							<g:each var="businessRule" in="${module.businessRules}">
								<li><g:fieldValue bean="${businessRule}" field="rule"/></li>
							</g:each>
							</ul>
						</g:if>
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
	</body>
</html>