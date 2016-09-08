$(function() {
	$(".menu-left .item2").addClass("selected");
	$(".menu-left .item1").removeClass("selected");
	
	$("span.search-icon").click(function(){
		var query = $("input.search-input").val();
        if(query != "") window.location.href = "/?query="+query;
	});
	
	$(".search-input").keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == "13"){
	    	var query = $("input.search-input").val();
	        if(query != "") window.location.href = "/?query="+query;
	    }
	});
});


