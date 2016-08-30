$(function() {
	ivy.target.list(true);
	
	$(".menu-left .item1").addClass("selected");
	$(".menu-left .item2").removeClass("selected");
});

$(document).ready(function(){	
	var isUpdating = false;
	
	$(window).scroll(function () {
		var scrollHeight = $(window).scrollTop();
		var documentSize = $(document).height();
		var sizeWindow   = $(window).height();
		
	 	if (scrollHeight + sizeWindow >= documentSize && !isUpdating) {
	 		isUpdating = true;
	 		ivy.target.list(false);
			isUpdating = false;
		}
	});
});
