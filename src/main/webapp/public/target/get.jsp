<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						%>
	
<div>
	<label>${object.id}</label>
</div>
<div>
	<label>${object.host}</label>
</div>
<div>
	<label>${object.owner}</label>
</div>	
<div>
	<label>${object.person}</label>
</div>	
<div>
	<label>${object.email}</label>
</div>
<div>
	<label>${object.country}</label>
</div>		
<div>
	<label>${object.changed}</label>
</div>	
<div>
	<table>
	<c:forEach items="${list}" var="url">
		<tr>
			<td>${url.path}</td>
			<td>${url.sqlVulnerability}</td>
			<td>${url.xssVulnerability}</td>					
		</tr>
	</c:forEach>
	</table>				
</div>