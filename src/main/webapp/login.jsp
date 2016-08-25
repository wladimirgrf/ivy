<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/frameworks/yaml/screen/typography.css" />
		<link rel="stylesheet" type="text/css" href="/public/css/login.css" />
	</head>
	<body>
		<form action="/login" method="post" class="box-shadow login">
			<img class="top" src="/public/img/gear.png" width="120px">
								    
			<div class="user">
				<img src="/public/img/user.png">
				<input type="text" name="email"/>  
			</div>
			
			<div class="password">
				<img src="/public/img/pass.png">
				<input type="password" name="password"/>
			</div>
				 	    			
			<input type="submit" value="Entrar"/>    	
						
		</form>		
	</body>
</html>