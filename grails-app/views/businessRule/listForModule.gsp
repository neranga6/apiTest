<%@ page import="com.nationwide.edm.authoring.BusinessRule" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'oys.css')}" type="text/css">
		<title>Business Rule List for Module</title>
	</head>
	<body>
	<table>
		<g:each in="${businessRuleInstanceList}" status="i" var="businessRuleInstance">
			Added: <g:formatDate date="${businessRuleInstance.dateCreated}" /><br/>
			${fieldValue(bean: businessRuleInstance, field: "rule")}<br/>
			<br/>
		</g:each>
	</table>
	</body>
</html>