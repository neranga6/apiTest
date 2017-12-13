<%@ page import="com.nationwide.edm.authoring.BusinessRule" %>
<table border="1">
	<tbody>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: businessRuleInstance, field: 'rule', 'error')} required">
					<label for="rule">
						<g:message code="businessRule.rule.label" default="Rule" />
						<span class="required-indicator">*</span>
					</label>
					<g:textArea name="rule" cols="40" rows="5" maxlength="500" required="" value="${businessRuleInstance?.rule}"/>
				</div>
			</td>
			
		</tr>
		
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: businessRuleInstance, field: 'module', 'error')} required">
					<label for="module">
						<g:message code="businessRule.module.label" default="Module" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="module" name="module.id" from="${com.nationwide.edm.authoring.Module.list()}" optionKey="id" required="" value="${businessRuleInstance?.module?.id}" class="many-to-one"/>
				</div>
			</td>
	</tbody>
</table>
