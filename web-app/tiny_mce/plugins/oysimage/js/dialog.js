tinyMCEPopup.requireLangPack();

var ExampleDialog = {
	init : function() {
		$.getJSON('../../../image/list', function(data) {

			$.each(data, function(key, value) {
				
				$("#noImage").remove();
				
				// this will be inserted back into the rich text editor field
				var html = "<img src=\"" + value.url + "\" data-mce-src=\"" + value.url + "\"/>";
				
				var image = $('<img/>', {
					src: value.thumbnail_url,
					alt: value.name,
					click: function() {
						ExampleDialog.insert(html);
					}
				});
				
				// display the thumbnail in the pop-up window, and let the user click on it to inject it back into the text editor
				$('<li />', {
				    html: image,
				}).appendTo("#imageList");
				

			});
			
			
		});
	},

	insert : function(html) {
		// Insert HTML content into the TinyMCE rich text editor, which is a link to the image
		tinyMCEPopup.editor.execCommand('mceInsertContent', false, html);
		tinyMCEPopup.close();
	}
};

tinyMCEPopup.onInit.add(ExampleDialog.init, ExampleDialog);
