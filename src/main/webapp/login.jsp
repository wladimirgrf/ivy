<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	<body>
		<form action="/login" method="post">							    
			<div>
				<label for="user">Usuario</label>
				<input type="text" name="user"/>  
				<label for="password">Senha</label>
				<input type="password" name="password"/>   	    			
				<input type="submit" value="Enviar"/>    	
			</div>			
		</form>		
	</body>
</html>