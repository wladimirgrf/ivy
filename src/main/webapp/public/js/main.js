$(function() {	
	$('button.new-scan').click(function(){
	    $('.body-off').show();
	    $('.popup').show();
	});
	
	$('.body-off').click(function(){
		$('.body-off').hide();
	    $('.popup').hide();
	});
	
	$('.popup-filter .close').click(function(){
		$('.popup-filter').hide();
		$(".p-input input").val("1");
	});
	
	$('.popup-footer .options').click(function(){
		$('.popup-filter').show();
	});
	
	$('span.search-icon').click(function(){
		var query = $('input.search-input').val();
        if(query != "") ivy.searchHosts(query);
	});
	
	$('.search-input').keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	    	var query = $('input.search-input').val();
	        if(query != "") ivy.searchHosts(query);
	    }
	});
	
	$('#hack').click(function(){
		var host   = $('.popup .popup-input').val();
		var rounds = Number($(".p-input input").val());
		
		if(host != ""){
			ivy.exploit.host = host;
			
			if(rounds > 0) ivy.exploit.rounds = rounds;
			
			ivy.exploit.execute();
		}
	});
});

var ivy = {
		
	globalHosts: function() {
		$.ajax({
			url : "/api/target",
			cache : false,
			dataType : "json",
			success : function(data) {
				if (data != null) ivy.addPage(data);
			}
		});
	},
	
	searchHosts: function(query) {
		$.ajax({
			url : "/api/target?action=search&query=" + query,
			cache : false,
			dataType : "json",
			success : function(data) {
				if (data != null) ivy.addPage(data);
			}
		});
	},

	addPage: function(data) {
		var itens = $("div.page").empty();
		for(var i = 0; i < data.length; i++){
			itens.append(
				$('<div>').append(
					$('<div>').append(
						$('<a>').attr({"href": "http://" + data[i].host, "target": "_blank"}).append(
							$('<strong>').addClass("host").html(data[i].host)
						),
						$('<span>').addClass("location").append(
								$('<img>').attr("src","/public/img/icon-geo-form.png").addClass("geo-icon"),
								$('<strong>').html(data[i].country)
						),
						$('<span>').addClass("time").html(ivy.getTimeStamp(Number(data[i].lastScan)))
					).addClass("bloc-info"),
					$('<div>').addClass("url " + (!data[i].safe ? "url-red" : "url-green")).append(
						$('<a>').attr("href", data[i].url).html(data[i].url)	
					),
					$('<div>').addClass("search-tags").append(ivy.formatTags(data[i].tags))
	             ).addClass("item")
	        )
		}
	},
	
	getTimeStamp: function(time) {
		var date = new Date().getTime();
		var timestamp = "";
		
		switch (true) {
			case ((date - time) <= (10 * 60 * 1000)):
				timestamp = Math.round((date - time) / (60 * 1000)) + " min";
	            break;
	        case ((date - time) <= (48 * 60 * 60 * 1000)):
	        	timestamp = Math.round((date - time) / (60 * 60 * 1000)) + " h";
	            break;
	        default:
	        	timestamp = Math.round((date - time) / (24 * 60 * 60 * 1000)) + " days";
		}
		return timestamp;
	},
	
	formatTags: function(line) {
		if(line == null || line == "") return;
		
		var tags = line.split(" ");
		var result = $("<div>");
		
		for(var i = 0; i < tags.length; i++){
			result.append($("<a>").attr({
				"href" : "#",
				"onclick" : "ivy.searchHosts('" + tags[i] + "'); return false;"
			}).html("#"+tags[i]));
		}
		return result;
	},
	
	exploit: {
		rounds: 	0,
		host:   	"",
		urls:   	[],
		vulnerable: {},
		target: 	null,
		
		msg_no_urls: "No link has been found, please contact support.",
		pattern: /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/,
		
		execute: function(){
			
			$.when(ivy.exploit.evaluate()).done(function() {
				
				 if(ivy.exploit.target != null){
					 ivy.addPage([ivy.exploit.target]);
					 
				 } else {
					 $(".popup-footer .loading").show();
					 
					 $.when(ivy.exploit.map()).done(function() { 
						 if(ivy.exploit.urls.length > 0){
							 $.when(ivy.exploit.code_injection()).done(function() { 
								 alert(ivy.exploit.vulnerable[0]);
							 });
						 } else {
							 alert(ivy.exploit.msg_no_urls);
						 }
					 });
				 }
				 

			});
		},
		
		map: function(){
			if(this.host == "" || !this.pattern.test(this.host) || this.rounds < 1) return;
			
			return $.ajax({
				url : "/api/url-mapping?character==&host=" + this.host + "&linksNumber=" + this.rounds,
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data != null) ivy.exploit.urls = data;
				}
			})
		},
		
		code_injection: function(){
			if(this.urls.length <= 0) return;
			
			return $.ajax({
				url : "/api/injection?links=" + this.urls,
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data != null){
						for (var key in data) {
							console.log(key + " - " + data[key]);
						 }
					}
				}
			})
		},
		
		evaluate: function(){	
			if(this.host == "" || !this.pattern.test(this.host)) return;
			
			return $.ajax({
				url : "/api/target?action=evaluate&host=" + this.host,
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data != null) ivy.exploit.target = data;
				}
			})
		}
		
	},
	
	
}