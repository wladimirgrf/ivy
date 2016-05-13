<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"                        %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" 						 %>
<jsp:useBean id="date" class="java.util.Date"/>  
<c:set target="${date}" property="time" value="${object.lastScan}"/>  

<form action="/restrict/target?action=save" class="ym-form" method="post">
	
	<div class="ym-fbox-text">
		<input type="hidden" name="id" value="${object.id}"/>
		
		<label>Host: ${object.host}</label>
		<label>Owner: ${object.owner}</label>
		<label>Country: ${object.country}</label>
		<label>Email: ${object.email}</label>
		<label>Changed: ${object.changed}</label>
		
		<label>Last Scan: <fmt:formatDate value="${date}"  pattern="dd/MMM/yyyy  HH:mm" /></label>


		<label for="tags">Tags:</label>
		<input type="text" name="tags" value="${object.tags}"/>
					
		<input type="submit" value="Salvar"/>
	</div>
</form>