$(function() {	
	
	var menu_1 = $(".header .item1");
	
	menu_1.mouseover(function(){
		$(".header .item1 img").addClass("hover");
		$(".header .item1 span").addClass("hover");
	});
	
	menu_1.mouseout(function(){
		$(".header .item1 img").removeClass("hover");
		$(".header .item1 span").removeClass("hover");
	});
	
	var menu_2 = $(".header .item2");
	
	menu_2.mouseover(function(){
		$(".header .item2 img").addClass("hover");
		$(".header .item2 span").addClass("hover");
	});
	
	menu_2.mouseout(function(){
		$(".header .item2 img").removeClass("hover");
		$(".header .item2 span").removeClass("hover");
	});
	
	$("#open-popup").click(function(){
		$('.popup').show();
		$('.body-off').show();
	});
	
	$(".body-off").click(function(){
		$(".popup").hide();
		$(".body-off").hide();
	});
	
	$(".popup-filter .close").click(function(){
		$(".popup-filter").hide();
		$(".p-input input").val("1");
	});
	
	$(".popup-header .close").click(function(){
		$('.popup').hide();
		$('.body-off').hide();
	});
	
	$(".popup-footer .option-container").click(function(){
		$('.popup-filter').show();
	});
	
	$("#hack").click(function(){
		var host   = $(".popup .popup-input").val();
		var rounds = Number($(".p-input input").val());
		
		if(!$.isEmptyObject(host) && ivy.pattern.test(host)){
			ivy.loading_event();
			
			ivy.exploit.host = host;
			
			if(rounds > 0){
				ivy.exploit.rounds = rounds;
			}
			ivy.exploit.execute();
		}else {
			ivy.popup_msg(ivy.msg_link_format);
		}
	});
});

var ivy = {
		
	create_tags: function(host){
		var tags = host.split("//")[1];
		 
		 tags = tags.replace(/\./g, " ");
		 tags = tags.replace(/\//g, "");
		 tags = tags.replace("com", "");
		 tags = tags.replace("br",  "");
		 tags = tags.replace("www", "");
		 
		 return $.trim(tags);
	},
	
	loading_event: function(){
		$("#hack").prop("disabled",true);
		$("#hack").addClass("disabled");
		
		$(".popup-footer .option-container").prop("disabled",true);
		$(".popup-footer .option-container").addClass("disabled");
		
		$(".popup-footer .loading").show();
	},
	
	close_popup: function(){
		$('.popup').hide();
		$('.body-off').hide();
	},
	
	end_loading: function(){
		$("#hack").prop("disabled",false);
		$("#hack").removeClass("disabled");
		
		$(".popup-footer .option-container").prop("disabled",false);
		$(".popup-footer .option-container").removeClass("disabled");
		
		$(".popup-footer .loading").hide();
	},
	
	redirect_page: function(page){
		window.location.href = page;
	},
		
	target:{
		list: function() {
			var page = Number($("#page").val());
			
			var params = {
				"page"     : page, 
				"pagesize" : 6
			};
			
			return $.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params
			});
		},
		
		search: function(query) {
			var params = {
				"action": "search", 
				"query" : query
			};
			
			return $.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					return data;
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
			
			return $.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					ivy.redirect_page("/");
				}
			})
		}
	},
	
	popup_msg: function(msg){
		$("body .popup > input").addClass("error").attr("placeholder",msg).val("");
		ivy.end_loading();
	},
	

	exploit: {
		rounds:     0,
		host:       "",
		urls:       [],
		url:        "", 
		target:     null,
		vulnerable: 		 false,
		supported_extension: "",
		
		msg_no_urls:            "No link has been found, please contact support.",
		msg_no_supported:       "this domain extension is not supported.",
		msg_connection_refused: "Connection refused.",
		
		
		reset_environment_variables: function(){
			this.rounds = 0;
			this.host = "";
			this.urls = [];
			this.url = "";
			this.target = null;
			this.vulnerable = false;
			this.supported_extension = "";
		},
		
		map: function(){
			if($.isEmptyObject(this.host) 
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
		
		supported: function(){	
			if(this.host == "" 
			|| !ivy.pattern.test(this.host)) {
				return;
			}
			
			var params = {
				"action" : "supported", 
				"host"   : this.host
			};
			
			return $.ajax({
				url : "/api/target",
				cache : false,
				dataType : "json",
				data: params,
				success : function(data) {
					if (data != null){
						ivy.exploit.supported_extension = data;
					}
				}
			})
		},
		
		execute: function(){
			$.when(ivy.exploit.evaluate()).done(function() {
				if(ivy.exploit.target != null 
				&& (ivy.exploit.target == "connection_refused" || ivy.exploit.target == "false")){
					ivy.popup_msg(ivy.exploit.msg_connection_refused);
					ivy.exploit.reset_environment_variables();
					
				}else if(ivy.exploit.target != null){
					 var path = "/?query=" + ivy.exploit.target.host;
					 ivy.end_loading();
					 ivy.close_popup();
					 ivy.exploit.reset_environment_variables();
					 ivy.redirect_page(path);
					 
				 } else {
					 $.when(ivy.exploit.supported()).done(function() { 
						 if(ivy.exploit.supported_extension){
							 
							 $.when(ivy.exploit.map()).done(function() { 
								 if(ivy.exploit.urls.length <= 0){
									 ivy.popup_msg(ivy.exploit.msg_no_urls);
									 ivy.end_loading();
									 ivy.close_popup();
									 ivy.exploit.reset_environment_variables();
									 
								 } else {
									 $.when(ivy.exploit.code_injection()).done(function() {
										 
										 $.when(ivy.target.save(
											 ivy.exploit.host, 
											 ivy.exploit.url, 
											 ivy.exploit.vulnerable,
											 ivy.create_tags(ivy.exploit.host)
											 
										 )).done(function() {
											 ivy.end_loading();
											 ivy.close_popup();
											 ivy.exploit.reset_environment_variables();
										 });
									 });
								 }
							 }); 
						 }else {
							 ivy.popup_msg(ivy.exploit.msg_no_supported);
							 ivy.exploit.reset_environment_variables();
						 }
					 });
				 }
			});
		}
	},	
	
	msg_link_format : "the link must be in the proper format ex. http://domain.com",
	
	pattern: /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/
}