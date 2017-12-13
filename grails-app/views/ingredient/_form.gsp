<%@ page import="com.nationwide.edm.authoring.Ingredient" %>

<table border="1">
	<tbody>
		<tr>
			<td>

				<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'sequence', 'error')} required">
					<label for="sequence">
						<g:message code="ingredient.sequence.label" default="Sequence" />
						<span class="required-indicator">*</span>
					</label>
					<g:field type="number" name="sequence" min="1" required="" value="${fieldValue(bean: ingredientInstance, field: 'sequence')}"/>
				</div>
			</td>
			
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'comment', 'error')} ">
					<label for="comment">
						<g:message code="ingredient.comment.label" default="Comment" />
						
					</label>
					<g:textArea name="comment" cols="40" rows="5" maxlength="500" value="${ingredientInstance?.comment}"/>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'lastModBy', 'error')} required">
					<label for="lastModBy">
						<g:message code="ingredient.lastModBy.label" default="Last Mod By" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="lastModBy" name="lastModBy.id" from="${com.nationwide.edm.authoring.User.list()}" optionKey="id" required="" value="${ingredientInstance?.lastModBy?.id}" class="many-to-one"/>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'letter', 'error')} required">
					<label for="letter">
						<g:message code="ingredient.letter.label" default="Letter" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="letter" name="letter.id" from="${com.nationwide.edm.authoring.LetterTemplate.list()}" optionKey="id" required="" value="${ingredientInstance?.letter?.id}" class="many-to-one"/>
				</div>
		</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: ingredientInstance, field: 'module', 'error')} required">
					<label for="module">
						<g:message code="ingredient.module.label" default="Module" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="module" name="module.id" from="${com.nationwide.edm.authoring.Module.list()}" optionKey="id" required="" value="${ingredientInstance?.module?.id}" class="many-to-one"/>
				</div>
			</td>
	</tbody>
</table>

