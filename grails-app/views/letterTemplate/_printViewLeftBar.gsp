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
	                <table class="previewBody">
		                <tr>
			                <td>
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
				                <div class="previewLeftbar">
				                    <span class="previewTitlebar"><g:render template="previewTitleIngredientTemplate" collection="${titleList}" var="ingredientModule"/></span>
				                    <br /><br />
				                    <g:render template="previewIngredientTemplate" collection="${advMessageList}" var="ingredientModule"/>
				                </div>
				                <div class="previewMainBody">
				                    <g:render template="previewIngredientTemplate" collection="${bodyList}" var="ingredientModule"/>
				                    <g:render template="previewIngredientTemplate" collection="${closingList}" var="ingredientModule"/>
				                </div>
			               
			           
			            	</td>
		            	</tr>
				 </table>
			 </div>
   	 	</form>
	</body>
	
</html>