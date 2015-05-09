<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						 %>
<table>
	<thead>
		<tr>
			<th>Dominio</th>
			<th>Proprietario</th>
			<th>Responsavel</th>
			<th>E-mail</th>
			<th>País</th>
			<th>Ultima atualização</th>	
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="object">
			<tr>
				<td>${object.host}</td>
				<td>${object.owner}</td>
				<td>${object.person}</td>
				<td>${object.email}</td>
				<td>${object.country}</td>	
				<td>${object.changed}</td>						
			</tr>
		</c:forEach>
	</tbody>
</table>