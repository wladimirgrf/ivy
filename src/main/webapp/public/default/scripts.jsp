<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>

<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>

<script src="/public/js/main.js?1"></script>
<script src="/public/js/plugin-mp.js"></script>

<c:if test="${model.equals('home')}">
	<script src="/public/js/home.js?3"></script>
</c:if>

<c:if test="${model.equals('about')}">
	<script src="/public/js/about.js?3"></script>
</c:if>