/**
 * This script depends on an external variable being set, named CONTEXT_ROOT.
 */
if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);

	var DISPLAY_LENGTH_CONTENT = 30;
	
	var abbreviateModuleContent = function(rawHtmlContent) {
		// strip out the raw html
		var content = $("<span>" + rawHtmlContent + "</span>").text();
		return content.substring(0, DISPLAY_LENGTH_CONTENT);
	};
	
$(function() {
	
	$(window).load(function(){
		
		$.ajax({
			url: CONTEXT_ROOT + "/letterTemplate/lastFiveLettersAdded",
			type: "GET",
			cache: false,
			success: function(result) {
					
				$.each(result.letters, function(val, option) {
					var id = option.id;
					var name = option.name;
					$("#lastFiveLettersForWriters").append('<li><A href="#" onClick="displayPreview(' + id + ');return false;">' + name + '</A></li>');
				});
				
				$.each(result.letters, function(val, option) {
					var id = option.id;
					var name = option.name;
					$("#lastFiveLettersForReviewers").append('<li><A href="#" onClick="displayReviewAndEditContentView(' + id + ');return false;">' + name + '</A></li>');
				});
			},
			statusCode: {
				401: function() {					
					window.location.href=CONTEXT_ROOT + "/"
				}
			}
				
		});	
		
	});
	
	$( "#dialog-modal" ).dialog({
		autoOpen: false,
        width: 1000,
        height: 600,
        modal: true,
        resizable: true,
        close: function(event, ui) {
        	// after the recipes list has been updated, we refresh the page when the user closes the dialog box
        	window.location.reload();
        }
      
	});
	
	$( "#addLetterComment-modal" ).dialog({
		autoOpen: false,
        width: 600,
        height: 300,
        modal: true,
        resizable: true
	});

	$(".radioButton").change(function(){
		var radioValue = $('input[name=letterTemplateGroup]:checked').val();
		
		if(radioValue == "new"){
			$('#newModelTemplate').show();
			$('#modelExistingTemplate').hide();
			
		}else if(radioValue == 'existing'){
			$('#newModelTemplate').hide();
			$('#modelExistingTemplate').show();
		};
			
	});
	
	$("#saveLetterCommentButton").click(function(){		
		
		var letterId = $('#id').val();
    	var comment = $('#letterComment').val();
    	var version = $('#version').val();
    
    	addLetterTemplateComment(letterId, comment, version);
		
	});
	
	$("#selectAddress").click(function(){		
		
		$('#dialog-modal').dialog({ height: 200 });
		
	});
	
	$("#sectionList").change(function(){
		alert('Change selected');
	});
	
	$("#addLetterCommentButton").click(function(){		
		
		$('#letterComment').val('');
		$('#addLetterComment-modal').dialog('open');
		
	});
});

/**
 * This is used to build the sortable list of Recipes on the "Edit Structure" screen of the LetterTemplate section.
 */
function addToRecipeList(moduleId, ingredientId, htmlModuleContent) {
	var shortContent = abbreviateModuleContent(htmlModuleContent);
	var deleteButton = $('<input/>', {
		type: "button",
		value: "Remove Module #" + moduleId,
		'class': "deleteIngredient",
		click: function() {
			
			var ingredientIds = $.map($(".ingredientId"), function(element) {
				if(element.innerHTML != ingredientId) {
					return element.innerHTML;
				}
			});
			
			$.post('../../letterTemplate/deleteIngredient', "recipe=" + ingredientIds + "&deleteIngredientId=" + ingredientId, function(data) {
				if(data.success) {
					$("#ingredient" + ingredientId).fadeOut();
					$("#ingredient" + ingredientId).remove();
				} else {
					alert("The ingredient could not be removed. Please try again.");
				}
			});
		}
	});
	
	var listItem = $('<li/>', {
		id: "ingredient" + ingredientId,
		'class': "ui-state-default",
		html: shortContent + '<span class="ingredientId">' + ingredientId + '</span>',
	});
	
	listItem.prepend(deleteButton);
	$("#sortable").append(listItem);
}

/**
 * This is called on the Letter Builder screen, for the Module Selection dialog.
 */
function populateModuleSelectionList(response){
	
	$("#moduleList li").remove();
	$("#selectedAndOrderedModuleList tr").remove(); 
	$("#sortable li").remove();
	$('#letterBuilderSectionDropDown').children().remove().end().append('<option selected value="">Choose</option>') ;

	$.each(response.modulesAndSections.sections, function(val, option) {
		$("<option/>").val(option.id).text(option.name).appendTo("#letterBuilderSectionDropDown");
	});
	
	$.each(response.modulesAndSections.attachedModules, function(val, option) {
		addToRecipeList(option.moduleId, option.ingredientId, option.moduleContent);
	});
	
	$('#dialog-modal').dialog('open');
	$("#dialog-modal").scrollTop("0")
}

function populateAvailableModuleList(response){
		$("#moduleList tr").remove();
		
		if(response.moduleList) {
			
			var letterTemplateId = $("#letterTemplateId").text()
			
			$.each(response.moduleList, function(val, option) {
		
				var moduleId = option.id;
				var htmlModuleContent = option.content;
				
				var addButton = $('<input/>', {
					type: "button",
					value: "Add Module #" + moduleId,
					'class': "addIngredient",
					click: function() {
						
						$.post('../../letterTemplate/addIngredient', "letterTemplateId=" + letterTemplateId + "&moduleId=" + moduleId, function(data) {
							if(data.success) {
								var ingredientId = data.ingredientId;
								addToRecipeList(moduleId, ingredientId, htmlModuleContent);
							} else {
								alert("The ingredient could not be added. Please try again.");
							}
						});
					}
				});
				
				var row = $('<tr/>', {
					html: '<td>' + htmlModuleContent + "</td>",
				});
				
				row.prepend($('<td/>').append(addButton));
			
				$("#moduleList").append(row);
											
			});
		} else {
			alert("Module not found");
		}
}

function displayPreview(id){
		
	var url = CONTEXT_ROOT + '/letterTemplate/preview' + '?id=' + id; 
	window.location.href = url;
}

function displayReviewAndEditContentView(id){
	
	var url = CONTEXT_ROOT + '/letterTemplate/review' + '?id=' + id; 
	window.location.href = url;
}

function toggleAddTemplateComment(){
	
	$("#reviewAndEditCommentList").toggle();

}

function addLetterTemplateComment(letterId, comment, version){
	
	var url = CONTEXT_ROOT + '/letterTemplate/addComment' + "?id="+letterId + "&comment=" + comment + "&version=" + version;
	
	$.ajax({
		url: url,
		type: "POST",
		success: function(data) {
			window.location.reload();
		},
		error: function(data){
			alert("Error occurred saving letter comment");
		}
	});	
	
}

}
