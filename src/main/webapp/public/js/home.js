$(function() {
	
	$.ajax({
		url : "/target",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data != null) {
				var targets = $("div#targets").empty();
				for(var i = 0; i < data.length; i++){
					targets.append($("<table>").append(
						$('<tr>').append(
		                    $('<td>').text(data[i].id),
		                    $('<td>').text(data[i].host),
		                    $('<td>').text(data[i].security)
		                 )
			        ))
				}
			}
		}
	});
	
	
	$("#btnScan").click(function() {
		
		$.ajax({
			url : "/exploit?domain=" + $("#domain").val(),
			cache : false,
			dataType: "json",
			success : function(data) {
				if (data != null) {
					if(data.error == null){
						
						var table = $("div#target").append($("<table>"));
						
						table.append($('<tr>').append(
			                    $('<td>').text(data.id),
			                    $('<td>').text(data.host),
			                    $('<td>').text(data.security)
			             ))
				        
				        for(var i = 0; i < data.urls.length; i++){
				        	table.append($('<tr>').append(
				        		$('<td>').text(data.urls[i].path)
				        	))
	                    }
					        
					}else{
						alert(data.error);
					}
				}
			}
		});
		
	});
	

});