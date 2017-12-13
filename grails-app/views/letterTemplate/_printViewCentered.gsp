<%@ page import="com.nationwide.edm.authoring.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<r:require module="previewLetter"/>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'printScreen.css')}" media="print" type="text/css">
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:layoutResources/>
	</head>
	<div class="reportBackLink"><g:link controller="letterTemplate" action="list">Back to template list</g:link></div>
	
	<body>
	   	<form id="leftNavPreviewForm">
	         <div class="previewWrapper">
	         	<!--this sets the width to roughly 81/2 by 11-->
	                      
	            <div class="previewBody">
	                <div id="previewTop">
	                    <table id="previewReference">
	                        <tr>
	                            <td class="previewLogoAndAddress">
	                                <g:render template="previewIngredientTemplate" collection="${logoList}" var="ingredientModule"/>
	                                <div class="previewRefaddress">
	                                	<p>&nbsp;&nbsp;</p>
	                                    <g:render template="previewIngredientTemplate" collection="${addressList}" var="ingredientModule"/>
	                                </div>
	                            </td>
	                            <td class="previewReference"><g:render template="previewIngredientTemplate" collection="${refInfoList}" var="ingredientModule"/></td>
	                        </tr>
	                    </table>
	                </div>
	                <div class="previewMainBodyCentered">
	                    <g:render template="previewIngredientTemplate" collection="${bodyList}" var="ingredientModule"/>
	                    <g:render template="previewIngredientTemplate" collection="${closingList}" var="ingredientModule"/>
	                </div>
	            </div>
			 </div>
   	 	</form>
	</body>
	
</html>