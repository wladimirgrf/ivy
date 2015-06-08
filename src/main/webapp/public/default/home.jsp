<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="box-1024 padding-top-15">
	<div class="box-540 info">

			<div class="detail">
				<div class="d-header">
					<img id="close" src="/public/img/close.png" width="25px">
				</div>
				<div class="title">Host</div>
				<div id="d-host" class="sub-title"></div>
				
				<div class="title">País</div>
				<div id="d-country" class="sub-title"></div>
				
				<div class="title">Proprietário</div>
				<div id="d-owner" class="sub-title"></div>
				
				<div class="title">Responsavel</div>
				<div id="d-person" class="sub-title"></div>
				
				<div class="title">E-mail</div>
				<div id="d-email" class="sub-title"></div>
			
				<div class="title">Ultima atualização.</div>
				<div id="d-changed" class="sub-title"></div>
				
				<div class="title">Links Testados</div>
				<div id="d-urls" class="sub-title"><ul id="in-urls"></ul></div>
			</div>
	</div>
	
	<div class="box-439 margin-left-15 margin-top-15">
	
		<div class="analyze">
			<form>			    
				<input type="text" id="domain" name="domain"/> 
				<input id="btnAnalyze" type="button" value="ANALISAR"/>    		
			</form>
		</div>	
		
		<div class="targets margin-top-15">
			<div class="header-targets">
				<h1 class="header-host">Dominios</h1>
				<h1 class="header-result">Seguro</h1>
			</div>
			<div class="last-targets"></div>
		</div>
		<div class="index margin-top-15">
			<img alt="" src="/public/img/unfected.png" width="24px">
			<h3>Seguro</h3>
 				
 			<img alt="" src="/public/img/infected.png" width="24px">
 			<h3>Inseguro</h3>
		</div>

	</div>
</div>
