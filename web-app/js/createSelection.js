/**
 * This is used in the Create Letter screen to show a small thumbnail image when a model is selected in the dropdown.
 */
var modelToImageMap = {
	1: "leftbar.png",
	2: "centered.png",
	3: "coupon.png"
};

function switchModelImage() {
	var modelNumber = $(this).val(),
		imagePath = "../images/" + modelToImageMap[modelNumber],
		image = $("<img/>", {
			src: imagePath
		});
	
	$("#modelImage").children().remove();
	$("#modelImage").append(image);
}

if (typeof jQuery !== 'undefined') {
	$(function() {
		$("#newModelTemplate").change(switchModelImage);
	});
}