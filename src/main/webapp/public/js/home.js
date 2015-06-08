$(function() {
	
	$("div.detail").hide();
	
	$.ajax({
		url : "/target",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data != null) {
				listTable(data);
			}
		}
	});
	
	
	$("#btnAnalyze").click(function() {
		analyze("/exploit?domain=" + $("#domain").val());
	});
	
	$("#close").click(function() { 
		$("div.detail").hide();
	});
});

function getTarget(id){
	analyze("/target?id=" + id);
}

function listTable(data){
	var infected = $("<img>").attr("src", "/public/img/infected.png").width("24px");
	var unfected = $("<img>").attr("src", "/public/img/unfected.png").width("24px");
	
	var targets = $("div.last-targets").empty();
	
	for(var i = 0; i < data.length; i++){
		targets.append(
			$('<div>').append(
				$('<div>').addClass("host").append($("<a>").attr("href","javascript:getTarget(" + data[i].id + ");").html(data[i].host)),
				$('<div>').addClass("result").append((data[i].security ? infected : unfected ))
             ).addClass("target")
        )
	}
}

function analyze(path){
	$.ajax({
		url : path,
		cache : false,
		dataType: "json",
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
				        
				}else{
					
					swal({
					  title: "Error!",
					  text: data.error,
					  type: "error",
					  confirmButtonText: "OK"
					});
				}
			}
		}
	});
}