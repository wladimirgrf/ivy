<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<form action="/exploit" method="get">			    
	<div>
		<input type="text" name="domain"/> 
		<input type="submit" value="Scan"/>    	
	</div>			
</form>
<label>${error}</label>