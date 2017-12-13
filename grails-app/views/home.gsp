<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>	
		<r:require module="home"/>	
	</head>
	<body class="mainPage">
		<table class="greyTable">
			<tr>
			<td>
					<div class="mainPageColumnLeft">
			            <div class="colHeader1" >
	                        <h2><span class="alternateTextColor">Writers</span></h2>
	                    </div>
			            <div class="col1">  
			            	<g:link controller="letterTemplate" action="createSelection">Create a new Template (letter)</g:link>		             
	                        <br />
	                        <g:link controller="letterTemplate" action="list">View/Edit all Templates (letter)</g:link>
	                        <br /><br /><br />
	                        <table>
	                        	<tr><td><b>Last 5 Templates Added:</b></td></tr>
	                        	<tr><td><ul id="lastFiveLettersForWriters"></ul></td></tr>
	                        </table>
	                        
	                        <br />
	                        <br /><br />
	                        -------------------------------
	                        <br />
	                        <g:link controller="module" action="create">Create a module</g:link><br />
	                        <g:link controller="module" action="list">View/edit all modules</g:link><br />
	                        <span style="font-size: 8pt;">(search for specific letter or module)</span>
	               		</div>
		                   
		             </div>
				
				</td>
				
				<td>
					<div class="mainPageColumnMiddle">
				            <div class="colHeader2" >
		                        <h2><span class="alternateTextColor">Reviewers</span></h2>
		                    </div>
				                    
			               <div class="col1">   
			              
		                        <table>
		                        	<tr><td><b>Last 5 Templates Added:</b></td></tr>
		                        	<tr><td><ul id="lastFiveLettersForReviewers"></ul></td></tr>
		                        </table>
		                      
		                        <br />
		                        <br /><br />
		                        <g:link controller="letterTemplate" action="list">View all letters</g:link>
		                        <br /><br />
		                     
		                   </div>
	                </div>
				</td>
			</tr>
		</table>
	</body>
</html>
