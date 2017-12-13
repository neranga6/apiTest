/**
 * Setup/customization for the Bootstrap File Uploader (jQuery-File-Upload), which is used on the Module Edit screen.
 */
function bootstrapFileUploadSetup() {
	// don't display the Select All checkbox and label.
	$("#fileupload-toggle").hide().next().hide();
}

if (typeof jQuery !== 'undefined') {
	
	$(function() {
		bootstrapFileUploadSetup();
	});

}