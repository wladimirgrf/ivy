<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/default/head.jsp"/>
	</head>
	<body>
		<div class="header">
			<jsp:include page="/public/default/header.jsp"/>
		</div>
		<div class="content">
			<jsp:include page="${content}"/>
		</div>
	</body>
</html>