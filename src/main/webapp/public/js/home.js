$(function() {
	ivy.target.list();
	
	$(".menu-left .item1").addClass("selected");
	$(".menu-left .item2").removeClass("selected");
	
	
	var isUpdating = false;
	var page = $(".content .page");
	
	page.scroll(function() {	
		
	    var scrollTop = page.scrollTop;
	    var scrollHeight = page.scrollHeight;
	    var offsetHeight = page.offsetHeight;
	    var contentHeight = scrollHeight - offsetHeight;	    
 	    if (contentHeight <= scrollTop && !isUpdating) {
 	    	alert("final");
 	    }
	});
});


