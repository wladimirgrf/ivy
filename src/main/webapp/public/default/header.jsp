<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<div class="container">
	<div class="menu-left">
		<ul>
			<li class="selected">
				<a href="/">
					<img class="home-icon" src="/public/img/icon-home-form.png">
					<span>Home</span>
				</a>
			</li>
			<li>
				<a href="/about">
					<img class="info-icon" src="/public/img/icon-info-form.png">
					<span>About</span>
				</a>
			</li>
		</ul>	
	</div>
	<div class="menu-right">
		<form action="/search" class="search-form">
			<input type="text" name="query" class="search-input" placeholder="Search for Domain">
			<span class="search-icon">
				<img src="/public/img/icon-search.png">
			</span>
		</form>
		<button class="new-scan">
			<span class="icon">
				<img src="/public/img/icon-eye.png">
			</span> 
			<span class="text">Scan</span> 
		</button>
	</div>
</div>