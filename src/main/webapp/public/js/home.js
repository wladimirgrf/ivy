$(function() {
	globalHosts();
	
	$('span.search-icon').click(function(){
		var query = $('input.search-input').val();
        if(query != "") searchHosts(query);
	});
	
	$('span.search-icon').click(function(){
		var query = $('input.search-input').val();
        if(query != "") searchHosts(query);
	});
	
	$('button.new-scan').click(function(){
	    $('.body-off').show();
	    $('.popup').show();
	});
	
	$('.body-off').click(function(){
		$('.body-off').hide();
	    $('.popup').hide();
	});
	
});

function globalHosts(){
	$.ajax({
		url : "/api/target",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data != null) {
				addPage(data);
			}
		}
	});
}

function searchHosts(query){
	$.ajax({
		url : "/api/target?action=search&query=" + query,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data != null) {
				addPage(data);
			}
		}
	});
}

function addPage(data){
	var itens = $("div.itens").empty();
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
					$('<span>').addClass("time").html(getTimeStamp(Number(data[i].lastScan)))
				).addClass("bloc-info"),
				$('<div>').addClass("url " + (!data[i].safe ? "url-red" : "url-green")).append(
					$('<a>').attr("href", data[i].url).html(data[i].url)	
				),
				$('<div>').addClass("search-tags").append(formatTags(data[i].tags))
             ).addClass("item")
        )
	}
}

function getTimeStamp(time){
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
}

function formatTags(line){
	if(line == null || line == "") return;
	
	var tags = line.split(" ");
	var result = $("<div>");
	
	for(var i = 0; i < tags.length; i++){
		result.append($("<a>").attr({
			"href" : "#",
			"onclick" : "searchHosts('" + tags[i] + "'); return false;"
		}).html("#"+tags[i]));
	}
	return result;
}

function analyze(path){
	$.ajax({
		url : path,
		cache : false,
		dataType: "json",
		beforeSend: function(){
			$("div.detail").hide();
			$("div.about").hide();
			$('.loader').show();
			
			$('#btnAnalyze').attr("disabled", true);
		},
		complete: function(){ 
			$('.loader').hide();
			$('#btnAnalyze').removeAttr("disabled");;
		},
		success : function(data) {
			if (data != null) {
				if(data.error == null){					
					
                    $('div#d-host').text(data.host);
                    $('div#d-country').text(data.country);
                    $('div#d-owner').text(data.owner);
                    $('div#d-person').text(data.person);
                    $('div#d-email').text(data.email);
                    $('div#d-changed').text(data.changed);

                    var urls = $('ul#in-urls').empty();
			        for(var i = 0; i < data.urls.length; i++){
			        	urls.append($('<li>').text(data.urls[i].path))
                    }
                    
                    $('div.detail').show();
                    listTargets();
				        
				}else{
					
					swal({
					  title: "Erro!",
					  text: data.error,
					  type: "error",
					  confirmButtonText: "OK"
					});
					
					$("div.about").show();
				}
			}
		}
	});
}