<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="box-1024 padding-top-15">

	<div class="box-540 info">
		<div class="loader">
			<img src="/public/img/loading.gif">
		</div>
	
		<div class="about">
			<p class="title">Sobre</p>
			<p>IVI é sistema que tem por objetivo analise de vulnerabilidades em outros sistemas web.</p>
			<p>Esse projeto oferece uma "auditoria" livre de custos, afim de minimizar erros em outro sistemas.</p>
			<p>Para uma alanise basta inserir seu domínio no campo de entrada ao lado e aguardar o resultado.</p>
			<p>Ao lado temos também a lista de domínios já analisados pelo nosso sistema.</p>
			</br>
			<p class="red bold">Atenção: O teste cobrem apenas erros básicos que coloquem sua aplicação em risco de ataques maliciosos.</p>
		</div>
	
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
				<h1 class="header-result">SQL Injection</h1>
			</div>
			<div class="last-targets"></div>
		</div>
		<div class="index margin-top-15">
			<img alt="" src="/public/img/infected.png" width="24px">
			<h3>Vulnerável</h3>
 				
 			<img alt="" src="/public/img/unfected.png" width="24px">
 			<h3>Não Vulnerável</h3>
		</div>
	</div>
</div>
