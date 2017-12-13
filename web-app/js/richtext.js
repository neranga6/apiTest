/**
 * Initialize the TinyMCE rich text editor.  This is used on the Module Edit screen.
 */
if (typeof tinyMCE !== 'undefined') {
	tinyMCE.init({
	    mode: "specific_textareas",
	    editor_selector: "richText",
	    theme: "advanced",
	    width: "75%",
	    height: "480",
	    
	    plugins: "autolink,lists,spellchecker,pagebreak,style,layer,table,save,advhr,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,oysimage",
	
	    // Theme options 
	    theme_advanced_buttons1: "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,insertdate,inserttime,|,forecolor,backcolor|,formatselect,fontselect,fontsizeselect",
	    theme_advanced_buttons2: "pastetext,pasteword,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,addimage",
	    theme_advanced_buttons3: "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,ltr,rtl,|,fullscreen,code",
	    theme_advanced_buttons4: ",styleprops,spellchecker,|,del,ins,attribs,|,visualchars,nonbreaking,blockquote,pagebreak,|,insertfile,",
	
	    theme_advanced_toolbar_location: "top",
	    theme_advanced_toolbar_align: "left",
	    theme_advanced_statusbar_location: "bottom",
	    theme_advanced_resizing: true,
	
		// If you set this option to true, the edit button will be disabled/dimmed until modifications are made.
		save_enablewhendirty : true,
		
		save_onsavecallback: function() {
			$(".save").click()
		},
		
	    theme_advanced_fonts : ""+
		    "Arial=arial,helvetica,sans-serif;"+
		    "Gotham=Gotham,Arial,sans-serif;" +
		    "Times New Roman=times new roman,times;"
	});
}
