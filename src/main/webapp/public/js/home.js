$(function() {
	$(".menu-left .item1").addClass("selected");
	$(".menu-left .item2").removeClass("selected");
	
	var query = $.urlParam('query');
	
	if(query){
		ivy.target.search(query);
	}else{
		ivy.target.list(true);
	}
	
	$("span.search-icon").click(function(){
		var query = $("input.search-input").val();
        if(query != "") ivy.target.search(query);
	});
	
	$(".search-input").keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == "13"){
	    	var query = $("input.search-input").val();
	        if(query != "") ivy.target.search(query);
	    }
	});
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

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if(!results) return null;
	return results[1] || 0;
}
