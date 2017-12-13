<%@ page import="com.nationwide.edm.authoring.LetterTemplateComment" %>



<div class="fieldcontain ${hasErrors(bean: letterTemplateCommentInstance, field: 'comment', 'error')} required">
	<label for="comment">
		<g:message code="letterTemplateComment.comment.label" default="Comment" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="comment" cols="40" rows="5" maxlength="500" required="" value="${letterTemplateCommentInstance?.comment}"/>
</div>
<br/>
<div class="fieldcontain ${hasErrors(bean: letterTemplateCommentInstance, field: 'letterTemplate', 'error')} required">
	<label for="letterTemplate">
		<g:message code="letterTemplateComment.letterTemplate.label" default="Letter Template" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="letterTemplate" name="letterTemplate.id" from="${com.nationwide.edm.authoring.LetterTemplate.list()}" optionKey="id" required="" value="${letterTemplateCommentInstance?.letterTemplate?.id}" class="many-to-one"/>
</div>

