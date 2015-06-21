$(function() {
	
	$("div.detail").hide();
	
	$('.loader').hide(); 
	
	listTargets();
	
	$("#btnAnalyze").click(function() {
		analyze("/exploit?domain=" + $("#domain").val());
	});
	
	$("#close").click(function() { 
		$("div.detail").hide();
		$("div.about").show();
	});
});

function listTargets(){
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
}

function getTarget(id){
	analyze("/target?id=" + id);
}

function listTable(data){
	
	var targets = $("div.last-targets").empty();
	
	for(var i = 0; i < data.length; i++){
		targets.append(
			$('<div>').append(
				$('<div>').addClass("host").append($("<a>").attr("href","javascript:getTarget(" + data[i].id + ");").html(data[i].host)),
				$('<div>').addClass("result").append((data[i].security ? 
						$("<img>").attr("src", "/public/img/infected.png").width("24px") 
						: 
						$("<img>").attr("src", "/public/img/unfected.png").width("24px")))
             ).addClass("target")
        )
	}
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
		},
		complete: function(){ 
			$('.loader').hide(); 
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