<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="area">
	<div class="body-off"></div>
	
	<div class="popup">
		<div class="popup-header">
			<h3 class="title">Analyze Domain</h3>
			<div class="close">
				<img src="/public/img/icon-close-white.png">
			</div>
		</div>
		<input type="text" class="popup-input" placeholder="domain.com.br" >
		
		<div class="popup-filter">
			<span>number of pages to be analyzed:</span>
			<div class="p-quantity">
			    <div class="p-minus">-</div>
			    <div class="p-input">
			        <input type="text" value="1" disabled="disabled" />
			    </div>
			    <div class="p-plus">+</div>
			</div>
			<div class="close">
				<img src="/public/img/icon-close-grey.png">
			</div>
		</div>
		
		<div class="popup-footer">
			<button class="option-container">
				<img class="options" src="/public/img/icon-options.png" >
			</button>
			<img class="loading" src="/public/img/gif-gears.gif" >
			<button id="hack" class="button">Analyze</button>
		</div>
	</div>	
</div>