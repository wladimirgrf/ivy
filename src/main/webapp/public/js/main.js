$(function() {	
	$('button.new-scan').click(function(){
		$('.popup').show();
		$('.body-off').show();
	});
	
	$('.body-off').click(function(){
		$('.popup').hide();
		$('.body-off').hide();
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
        if(query != "") ivy.target.search(query);
	});
	
	$('.search-input').keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	    	var query = $('input.search-input').val();
	        if(query != "") ivy.target.search(query);
	    }
	});
	
	$('#hack').click(function(){
		$('#hack').prop("disabled", true);
		
		var host   = $('.popup .popup-input').val();
		var rounds = Number($(".p-input input").val());
		
		if(host != "" && ivy.pattern.test(host)){
			ivy.exploit.host = host;
			
			if(rounds > 0){
				ivy.exploit.rounds = rounds;
			}
			
			ivy.exploit.execute();
		}
	});
});

var ivy = {
		
	target:{
		list: function() {
			$.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data != null){
						ivy.addPage(data);
					}
				}
			});
		},
		
		search: function(query) {
			var params = {
				"action": "search", 
				"query" : query
			};
			
			$.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					if (data != null){
						ivy.addPage(data);
					}
				}
			});
		},
		
		save: function(host, url, safe, tags){
			if($.isEmptyObject(host) 
			|| !ivy.pattern.test(host)
			|| $.isEmptyObject(url)) {
				return;
			}
			
			var params = {
				"action" : "save", 
				"host"   : host, 
				"url"    : url, 
				"safe"   : (safe ? safe : false),
				"tags"   : tags
			};
			
			$.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					if (data != null){
						ivy.addPage([data]);
					}
				}
			})
		}
	},
	

	exploit: {
		rounds:     0,
		host:       "",
		urls:       [],
		url:        "", 
		target:     null,
		vulnerable: false,
		
		msg_no_urls: "No link has been found, please contact support.",
		

		map: function(){
			if(this.host == "" 
			|| !ivy.pattern.test(this.host) 
			|| this.rounds < 1) {
				return;
			}
			
			var params = {
				"character"   : "=", 
				"host"        : this.host, 
				"linksNumber" : this.rounds
			};
			
			return $.ajax({
				url : "/api/url-mapping",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					if (data != null){
						for(var index in data){
							var url = data[index].replace(/&/g,"%26");
							ivy.exploit.urls.push(url);
						}
					}
				}
			})
		},
		
		code_injection: function(){
			if(this.urls.length <= 0){
				return;
			}
			
			return $.ajax({
				url : "/api/injection?links="+this.urls,
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data != null){
						var url = "";
						var vulnerable = false;
						for (var key in data) {
							url = key;
							vulnerable = data[key];
							if(vulnerable){
								ivy.exploit.url = url;
								ivy.exploit.vulnerable = vulnerable;
							}
						}
						if($.isEmptyObject(ivy.exploit.url)){
							ivy.exploit.url = url;
							ivy.exploit.vulnerable = vulnerable;
						}
					}
				}
			})
		},
		
		evaluate: function(){	
			if(this.host == "" 
			|| !ivy.pattern.test(this.host)) {
				return;
			}
			
			var params = {
				"action" : "evaluate", 
				"host"   : this.host
			};
			
			return $.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					if (data != null){
						ivy.exploit.target = data;
					}
				}
			})
		},
		
		execute: function(){
			$.when(ivy.exploit.evaluate()).done(function() {
				 if(ivy.exploit.target != null){
					 ivy.addPage([ivy.exploit.target]);
					 ivy.close_popup();
				 } else {
					 ivy.loading_event();
					 
					 $.when(ivy.exploit.map()).done(function() { 
						 if(ivy.exploit.urls.length <= 0){
							 alert(ivy.exploit.msg_no_urls);
							 ivy.close_popup();
						 } else {
					
							 $.when(ivy.exploit.code_injection()).done(function() {
								 
								 ivy.target.save(
									 ivy.exploit.host, 
									 ivy.exploit.url, 
									 ivy.exploit.vulnerable,
									 ivy.create_tags(ivy.exploit.host)
								 );
								 
								 ivy.close_popup();
							 });
						 }
					 });
				 }
			});
		}
	},
	
	create_tags: function(host){
		var tags = host.split("//")[1];
		 
		 tags = tags.replace(/\./g, " ");
		 tags = tags.replace(/\//g, "");
		 tags = tags.replace("com", "");
		 tags = tags.replace("br",  "");
		 
		 return $.trim(tags);
	},
	
	loading_event: function(){
		$(".popup-footer .loading").show();
	},
	
	close_popup: function(){
		$('#hack').prop("disabled", false);
		$(".popup-footer .loading").hide();
		$('.popup').hide();
		$('.body-off').hide();
	},
	
	getTimeStamp: function(time) {
		var date = new Date().getTime();
		var timestamp = "";
		
		switch (true) {
			case ((date - time) <= (59 * 1000)):
				timestamp = Math.round((date - time) / 1000) + " sec";
            	break;
			case ((date - time) <= (59 * 60 * 1000)):
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
		if(line == null 
		|| line == "")  {
			return;
		}
		
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
					
					$('<div>').addClass("url " + (!data[i].safe ? "url-green" : "url-red")).append(
						$('<a>').attr("href", data[i].url).html(data[i].url)	
					),
					
					$('<div>').addClass("search-tags").append(ivy.formatTags(data[i].tags))
	             ).addClass("item")
	        )
		}
	},
	
	pattern: /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/
}