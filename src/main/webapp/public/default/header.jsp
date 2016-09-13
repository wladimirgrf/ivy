<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="container">
	<div class="menu-left">
		<ul>
			<li class="item1">
				<a href="/">
					<img class="home-icon" src="/public/img/icon-home-form.png">
					<span>Home</span>
				</a>
			</li>
			<li class="item2">
				<a href="/about">
					<img class="info-icon" src="/public/img/icon-info-form.png">
					<span>About</span>
				</a>
			</li>
		</ul>	
	</div>
	<div class="menu-middle">
		<img class="logo" alt="logo" src="/public/img/logo.png">
	</div>
	<div class="menu-right">
		<div class="search-form">
			<input type="text" class="search-input" placeholder="Search for Domain">
			<span class="search-icon">
				<img src="/public/img/icon-search.png">
			</span>
		</div>
		<button id="open-popup" class="button">Analyze</button>
	</div>
</div>

<div class="container-mobile">
	<div class="search-form">
		<input type="text" class="search-input" placeholder="Search for Domain">
		<img class="form-close" src="/public/img/icon-close-grey.png">
	</div>
	<div class="menu-top">
		<img class="logo" alt="logo" src="/public/img/logo.png">
			
		<span class="search-icon">
			<img src="/public/img/icon-search.png">
		</span>
	</div>
	
	<div class="menu-bottom">
		<ul>
			<li class="item1">
				<a href="/">
					<img class="home-icon" src="/public/img/icon-home-form.png">
				</a>
			</li>
			<li class="item2">
				<a href="/about">
					<img class="info-icon" src="/public/img/icon-info-form.png">
				</a>
			</li>
		</ul>	
	</div>
</div>