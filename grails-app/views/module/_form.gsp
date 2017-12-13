<%@ page import="com.nationwide.edm.authoring.Module" %>

<div class="group">

<div class="richTextPanel">

<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'section', 'error')} required">
	<label for="section">
		<g:message code="module.section.label" default="Section Selected" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="section" name="section.id" from="${com.nationwide.edm.authoring.Section.list()}" optionKey="id" required="" value="${moduleInstance?.section?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="module.status.label" default="Module Status" />
	</label>
	<g:if test="${moduleInstance.statusEnabled}" >
		<g:select name="status" from="${moduleInstance.statusValues}" value="${moduleInstance?.status}" valueMessagePrefix="module.status" noSelection="['': '']"/>
	</g:if>
	<g:else>
		<g:fieldValue bean="${moduleInstance}" field="status"/>
	</g:else>	
</div>

<!-- TinyMCE rich text section --> 
<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'content', 'error')} required">
	<label>
		<g:message code="module.content.label" default="Module Content" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="content" class="richText" cols="40" rows="5" maxlength="8000" value="${moduleInstance?.content}"/>
</div>

<g:if test="${moduleInstance.id != null}">
This modules is used in <g:fieldValue bean="${moduleInstance}" field="letterCount"/> letters.

<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'ingredients', 'error')} ">
	
<ul class="one-to-many">
<g:each in="${moduleInstance?.uniqueLetters?}" var="letterTemplateInstance">
    <li><g:link controller="letterTemplate" action="editStructure" id="${letterTemplateInstance.id}">${letterTemplateInstance.id}: ${letterTemplateInstance.name}</g:link></li>
</g:each>
</ul>

</div>
</g:if>


</div>

<div class="commentBusinessRulePanel">
	<div>
		<h3>Add a Comment</h3>
		<hr/>
		<textarea name="comment" id="comment" rows="5" cols="55"></textarea><br/>
		<br/><br/>
	</div>
	
	<g:if test="${moduleInstance.id != null}">
	<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'comments', 'error')} ">
		<h3><g:message code="module.comments.label" default="View Comments" /></h3>
		<hr/>
		<iframe width="100%" src='<g:createLink controller="comment" action="listForModule" id="${moduleInstance.id}" absolute="true"/>' frameborder="0"></iframe>
	</div>
	</g:if>

	<div>
		<h3>Add a Business Rule</h3>
		<hr/>
		<textarea name="rule" id="rule" rows="5" cols="55"></textarea><br/>
		<br/><br/>
	</div>
	
	<g:if test="${moduleInstance.id != null}">
	<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'businessRules', 'error')} ">
		<h3><g:message code="module.businessRules.label" default="View Business Rules" /></h3>
		<hr/>
		<iframe width="100%" src='<g:createLink controller="businessRule" action="listForModule" id="${moduleInstance.id}" absolute="true"/>' frameborder="0"></iframe>
	</div>
	</g:if>

</div>

</div>
