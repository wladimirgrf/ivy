$(function() {
	$(".menu-left .item2").addClass("selected");
	$(".menu-left .item1").removeClass("selected");
	
	$(".menu-bottom .item2").addClass("selected");
	$(".menu-bottom .item1").removeClass("selected");
	
	$(".container span.search-icon").click(function(){
		var query = $("input.search-input").val();
        if(query != "") window.location.href = "/?query="+query;
	});
	
	$(".container-mobile span.search-icon").click(function(){
		$(".container-mobile .search-form").show();
	});
	
	$(".container-mobile .search-form .form-close").click(function(){
		$(".container-mobile .search-form").hide();
	});
	
	$(".search-input").keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == "13"){
	    	var query = $(".container input.search-input").val() || $(".container-mobile input.search-input").val();
	        if(query != "") window.location.href = "/?query="+query;
	    }
	});
});


