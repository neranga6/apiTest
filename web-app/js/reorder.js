/**
 * This will use JQuery UI to make a list sortable, so that elements can be dragged and dropped to reorder it.
 * It is used specifically on the Letter Builder screen to order ingredients (the recipe).
 */
if (typeof jQuery !== 'undefined') {
	
	$(function() {
		
		$("#sortable").sortable({
			placeholder: "ui-state-highlight",
			cursor: "move",
			// callback function that is run after the list is reordered
			update: function(ev, ui) {
				
				var ingredientIds = $.map($(".ingredientId"), function(element){
				    return element.innerHTML;
				});
				
				$.post('../../letterTemplate/updateRecipe', "recipe=" + ingredientIds, function(data) {
					if(!data.success) {
						alert("The ingredients could not be reordered. Please try again.");
					}
				});
			}
		});
		
		// don't allow the text to be selected/highlighted, since a click should drag+drop the elements
		$( "#sortable" ).disableSelection();
	});
	
}