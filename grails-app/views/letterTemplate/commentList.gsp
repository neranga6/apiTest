<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'oys.css')}" type="text/css">
		<title>List for Letter Comments</title>
	</head>
	<body>
	<table>
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
		<tr>
			<td>Added: <g:formatDate date="${commentInstance.dateCreated}" /></td>
		</tr>
		<tr>
			<td>${fieldValue(bean: commentInstance, field: "comment")}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		</g:each>
	</table>
	</body>
</html>