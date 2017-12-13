<%@ page import="com.nationwide.edm.authoring.Section" %>


<table border="1">
	<tbody>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: sectionInstance, field: 'sectionName', 'error')} required">
					<label for="sectionName">
						<g:message code="section.sectionName.label" default="Section Name" />
						<span class="required-indicator">*</span>
					</label>
				</div>
			</td>
			<td>
				<g:textField class="adminTextField" name="sectionName" required="" value="${sectionInstance?.sectionName}"/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: sectionInstance, field: 'group', 'error')} required">
					<label for="group">
						<g:message code="section.group.label" default="Section Group" />
						<span class="required-indicator">*</span>
					</label>
				</div>
			</td>
			<td>
				<g:select id="group" name="group.id" from="${com.nationwide.edm.authoring.SectionGroup.list()}" optionKey="id" required="" value="${sectionInstance?.group?.id}" class="many-to-one"/>
			</td>
		</tr>
		
	</tbody>
</table>
