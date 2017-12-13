<%@ page import="com.nationwide.edm.authoring.SearchType" %>
<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="OYS Home"/></title>
		<r:script disposition="head">var CONTEXT_ROOT = "${request.contextPath}";</r:script>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'oys.css')}" type="text/css">
		<g:layoutHead/>
		<r:require modules="jquery, jquery-ui, application, jqueryUITheme, listgrid" />
		<r:layoutResources/>
    </head>
	<body>
		
	<div id="custom-doc" class="myaccount singleRail">
		    <div id="hd" class=" ">
			   <div class="pageTools">
                    <div class="search" >
                        <br/>
                        
                        <g:form controller="search">
	                        <input name="criteria" type="text" id="txtSearchText" value="${params.criteria}"/>
	                        
	                        <g:select name="searchType" id="ddlSearchType" from="${SearchType.values()*.description}" keys="${SearchType.values()}" value="${params.searchType}" />
	                        
	                        <g:actionSubmit value="${message(code: 'default.button.search.label')}" action="index"  />
	                        
							<g:if test="${flash.error}">
							<div class="message">${flash.error}</div>
							</g:if>
                        </g:form>
                                         
                        
                    </div>
                      <hr/>
                    
           			<a href="${resource(uri:'/')}"><img src="${resource(dir: 'images', file: 'rsz_client-letter-icon.gif')}" alt="OYS Document Management logo"></a>
           			<a href="${resource(uri:'/')}"><img src="${resource(dir: 'images', file: 'application_title.png')}" alt="OYS Document Management banner"></a>
           		
           			 <span class="logout">
                        <g:form controller="logout" >                        
                         	<g:actionSubmit value="${message(code: 'default.button.logout.label', default: 'Logout')}" action="index"  />
                        </g:form>

                        </span>
                       
                
                </div>
             
				<div id="tabs" class="tabs ui-tabs ui-corner-all">
					<ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header ui-corner-all">
						<li><a href="${resource(uri:'/')}">Main</a></li>
						<li><g:link controller="module">Modules</g:link></li>
						<li><g:link controller="letterTemplate" action="list">Templates</g:link></li>
						<sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_REVIEWER">
							<li><g:link controller="admin">Administration</g:link></li>
						</sec:ifAnyGranted>
					</ul>
				
				    <div id="mainContent">
		    			<div id="mainWrapper">
          			 		<div id="main">
                        		<g:layoutBody/>
							</div>
						</div>
					</div>
				</div>
       
		    </div>
	
       </div>
       <br/>
       <div class="footer">
       		&copy;2012 Nationwide Mutual Insurance Company. All rights reserved.
       </div>
       <r:layoutResources />
     
	</body>
</html>