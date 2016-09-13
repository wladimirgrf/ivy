$(function() {
	$(".menu-left .item1").addClass("selected");
	$(".menu-left .item2").removeClass("selected");
	
	$(".menu-bottom .item1").addClass("selected");
	$(".menu-bottom .item2").removeClass("selected");
	
	var query = $.urlParam('query');
	
	$.when((query ? ivy.target.search(query) : ivy.target.list())).done(function(data) {
		
		if(data && !$.isEmptyObject(data)){
			ivy.page.add(data, true);
			$("#page").val(1);
		}
	});
	
	$.when(ivy.whois_scope.list()).done(function(data) {
		
		if(data && !$.isEmptyObject(data)){
			var itens = $("div.topics .itens");
			
			for(var i = 0; i < data.length; i++){
				itens.append($('<a>').attr("href", "/?query=" + data[i].id).html("#"+data[i].id)); 			
			}
		}
	});
	
	$(".container span.search-icon").click(function(){
		var query = $("input.search-input").val();
        if(query == "") return;
        
        $.when(ivy.target.search(query)).done(function(data) {
    		
    		if(data && !$.isEmptyObject(data)){
    			ivy.page.add(data, true);
    			$("#page").val(1);
    		}
    	});
	});
	
	$(".container-mobile span.search-icon").click(function(){
		$(".container-mobile .search-form").show();
		$("div.content").css({"margin": "18% 0"});
	});
	
	$(".container-mobile span.search-icon").click(function(){
		$(".container-mobile .search-form").show();
		$("div.content").css({"margin": "18% 0"});
	});
	
	$(".container-mobile .search-form .form-close").click(function(){
		$(".container-mobile .search-form").hide();
		$("div.content").css({"margin": "8% 0"});
	});
	
	$(".search-input").keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == "13"){
	    	var query = $(".container input.search-input").val() || $(".container-mobile input.search-input").val();
	        if(query == "") return;
	        
	        $.when(ivy.target.search(query)).done(function(data) {
	    		
	    		if(data && !$.isEmptyObject(data)){
	    			ivy.page.add(data, true);
	    			$("#page").val(1);
	    		}
	    	});
	    }
	});
});

ivy.page = {
		 
	add: function(data, empty) {
		var itens = $("div.page");
	
		if(empty){
			itens.empty();
			$("#page").val(0);
		}
	
		for(var i = 0; i < data.length; i++){
			itens.append(
				$('<div>').append($('<div>').append(
					$('<a>').attr({
						"href"  : "http://" + data[i].host, 
						"target": "_blank"
					}).append(
						$('<strong>').addClass("host").html(data[i].host)
					),
					
					$('<span>').addClass("location").append(
						$('<strong>').html(data[i].country),
						$('<img>').attr({
							"src" : "/public/img/icon-geo-form.png"
						}).addClass("geo-icon")
					),
					
					$('<span>').addClass("time").html(
						ivy.page.time_stamp(Number(data[i].lastScan))
					)
				).addClass("bloc-info"),
				
				$('<div>').addClass("url " + (data[i].safe ? "url-green" : "url-red")).append(
					$('<a>').attr({
						"href"  : data[i].url,
						"target": "_blank"
					}).html(data[i].url)	
				),
				
				$('<div>').addClass("search-tags").append(
					ivy.page.format_tags(data[i].tags)
				)
			).addClass("item"))
		}
	},
	
	format_tags: function(line) {
		if(line == null 
		|| line == "")  {
			return;
		}
		
		var tags = line.split(" ");
		var result = $("<div>");
		
		for(var i = 0; i < tags.length; i++){
			result.append($("<a>").attr({
				"href" : "#",
				"onclick" : "ivy.redirect_page('/?query=" + tags[i] + "'); return false;"
			}).html("#"+tags[i]));
		}
		return result;
	},
	
	time_stamp: function(time) {
		var date      = new Date().getTime();
		var timestamp = (24 * 60 * 60 * 1000);
		var season    = " days";
		
		switch (true) {
			case ((date - time) <= (59 * 1000)):
				timestamp = 1000;
	        	season = " sec";
            	break;
			case ((date - time) <= (59 * 60 * 1000)):
				timestamp = (60 * 1000);
	        	season = " min";
	            break;
	        case ((date - time) <= (48 * 60 * 60 * 1000)):
	        	timestamp = (60 * 60 * 1000);
	        	season = " h";
	        	break;
		}
		
		return  (Math.round((date - time) / timestamp) + season);
	},
}

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if(!results) return null;
	return results[1] || 0;
}
 
$(document).ready(function(){	
	var isUpdating = false;
	
	$(window).scroll(function () {
		var scrollHeight = $(window).scrollTop();
		var documentSize = $(document).height();
		var sizeWindow   = $(window).height();
		
	 	if (scrollHeight + sizeWindow >= documentSize && !isUpdating) {
	 		isUpdating = true;
	 		var result = ivy.target.list();
	 		if(result && !$.isEmptyObject(result)){
	 			ivy.page.add(result, false);
	 			$("#page").val(Number($("#page").val())+  1);
	 		}
	 		isUpdating = false;
	 	}
	});
});
