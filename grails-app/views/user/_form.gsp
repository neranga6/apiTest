<%@ page import="com.nationwide.edm.authoring.User" %>

<table border="1">
	<tbody>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
					<label for="username">
						<g:message code="user.username.label" default="Username" />
						<span class="required-indicator">*</span>
					</label>
					
				</div>
			</td>
			<td>
				<g:textField name="username" required="" value="${userInstance?.username}"/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} ">
					<label for="firstName">
						<g:message code="user.firstName.label" default="First Name" />
						
					</label>
					
				</div>
			</td>
			<td>
				<g:textField name="firstName" value="${userInstance?.firstName}"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} ">
					<label for="lastName">
						<g:message code="user.lastName.label" default="Last Name" />
						
					</label>
					
				</div>
			</td>
			<td>
				<g:textField name="lastName" value="${userInstance?.lastName}"/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
					<label for="accountExpired">
						<g:message code="user.accountExpired.label" default="Account Expired" />
						
					</label>
					
				</div>
			</td>
			<td>
				<g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
					<label for="accountLocked">
						<g:message code="user.accountLocked.label" default="Account Locked" />
						
					</label>
					
				</div>
			</td>
			<td>
				<g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" />
			</td>
		</tr>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
					<label for="enabled">
						<g:message code="user.enabled.label" default="Enabled" />
						
					</label>
					
				</div>
			</td>
			<td>
				<g:checkBox name="enabled" value="${userInstance?.enabled}" />
			</td>
		</tr>
		<tr>
			<td>
				<label for="userRoleSelection">
							<g:message code="user.roleSelection.label" default="Role" />
							
				</label>
			</td>
			<td>
				<g:radioGroup  name="userRoleSelection"
		              labels="['Writer','Reviewer','Admin']"
		              values="['ROLE_WRITER','ROLE_REVIEWER','ROLE_ADMIN']"
		              value="${userInstance?.userRole}">
					<p>${it.label} ${it.radio}</p>
				</g:radioGroup>
			</td>
			
		</tr>
	
	</tbody>
</table>
		









