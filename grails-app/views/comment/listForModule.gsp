<%@ page import="com.nationwide.edm.authoring.Comment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'oys.css')}" type="text/css">
		<title>Comment List for Module</title>
	</head>
	<body>
	<table>
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
			Added: <g:formatDate date="${commentInstance.dateCreated}" /><br/>
			${fieldValue(bean: commentInstance, field: "comment")}<br/>
			<br/>
		</g:each>
	</table>
	</body>
</html>