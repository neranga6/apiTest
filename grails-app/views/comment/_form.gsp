<%@ page import="com.nationwide.edm.authoring.Comment" %>

<table border="1">
	<tbody>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'comment', 'error')} required">
					<label for="comment">
						<g:message code="comment.comment.label" default="Comment" />
						<span class="required-indicator">*</span>
					</label>
					<g:textArea name="comment" cols="40" rows="5" maxlength="500" required="" value="${commentInstance?.comment}"/>
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'module', 'error')} required">
					<label for="module">
						<g:message code="comment.module.label" default="Module" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="module" name="module.id" from="${com.nationwide.edm.authoring.Module.list()}" optionKey="id" required="" value="${commentInstance?.module?.id}" class="many-to-one"/>
				</div>
			</td>
	</tbody>
</table>
