<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="area">
	<div class="body-off"></div>
	
	<div class="popup">
		<div class="title">Scan Domain</div>
		<input type="text" class="popup-input" placeholder="domain.com.br" >
		
		<div class="popup-filter">
			<input type="text" class="popup-number" disabled="disabled">
		</div>
		
		<div class="popup-footer">
			<img class="options" src="/public/img/icon-options.png" >
			<button id="hack" class="new-scan">
				<span class="icon">
					<img src="/public/img/icon-eye.png">
				</span> 
				<span class="text">Scan</span> 
			</button>
		</div>
	</div>	
</div>