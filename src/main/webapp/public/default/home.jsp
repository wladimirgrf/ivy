<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="box-1024 padding-top-15">
	<div class="box-540 info">
			<div id="error">
				<div class="error-header">
					<h3>Error:</h3>
					<img id="close" src="/public/img/close.png" width="25px">
				</div>
				
				<div class="error-msg"></div>
			</div>

			<div class="detail">
				<div class="d-header">
					<img id="close" src="/public/img/close.png" width="25px">
				</div>
				<div class="title">Host</div>
				<div id="d-host" class="sub-title"></div>
				
				<div class="title">Country</div>
				<div id="d-country" class="sub-title"></div>
				
				<div class="title">Owner</div>
				<div id="d-owner" class="sub-title"></div>
				
				<div class="title">Person</div>
				<div id="d-person" class="sub-title"></div>
				
				<div class="title">E-mail</div>
				<div id="d-email" class="sub-title"></div>
			
				<div class="title">Update</div>
				<div id="d-changed" class="sub-title"></div>
				
				<div class="title">Tested Links</div>
				<div id="d-urls" class="sub-title"><ul id="in-urls"></ul></div>
			</div>
	</div>
	
	<div class="box-439 margin-left-15 margin-top-15">
	
		<div class="analyze">
			<form>			    
				<input type="text" id="domain" name="domain"/> 
				<input id="btnAnalyze" type="button" value="ANALYZE"/>    		
			</form>
		</div>	
		
		<div class="targets margin-top-15">
			<div class="header-targets">
				<h1 class="header-host">Domains</h1>
				<h1 class="header-result">Safe</h1>
			</div>
			<div class="last-targets"></div>
		</div>

	</div>
</div>
