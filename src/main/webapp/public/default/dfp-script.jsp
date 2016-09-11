<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
	var windowWidth = document.body.offsetWidth || screen.width;
	var googletag = googletag || {};
	googletag.cmd = googletag.cmd || [];
	
	(function() {
		var gads = document.createElement("script");
		gads.async = true;
		gads.type = "text/javascript";
		var useSSL = "https:" == document.location.protocol;
		gads.src = (useSSL ? "https:" : "http:") + "//www.googletagservices.com/tag/js/gpt.js";
		var node = document.getElementsByTagName("script")[0];
		node.parentNode.insertBefore(gads, node);
	})();
</script>
<script type="text/javascript">
	if (windowWidth >= 800) {
		googletag.cmd.push(function() {	    
			googletag.defineSlot("/9541677/Test_Arroba_Banner_(290x250)", [290, 250], "arroba_banner").addService(googletag.pubads());
			       
		    googletag.pubads().enableSingleRequest();
			googletag.pubads().collapseEmptyDivs();
			googletag.enableServices();
		});

		googletag.cmd.push(function(){googletag.display("arroba_banner");});	
	}
</script>