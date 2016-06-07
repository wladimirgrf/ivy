<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>


<div class="menu-left">
	<ul>
		<li><a href="/">Home</a></li>
		<li><a href="/about">About</a></li>
	</ul>	
</div>

<div class="menu-right">
	<form action="/search">
		<input type="text" name="query" placeholder="Search for Domain">
		<span class="search-icon"></span>
	</form>
	<button class="new-scan">
		<span class="icon"></span> 
		<span class="text">Scan</span> 
	</button>
</div>