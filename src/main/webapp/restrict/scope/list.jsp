<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						 %>
<script type="text/javascript">
	function remove(id) {
		if (confirm("Tem certeza de que deseja excluir ?")) {			
			window.location.href = "/restrict/scope?action=delete&id=" + id;
		}	
	}
</script>
<table>
	<thead>
		<tr>
			<th>Id</th>
			<th>Proprietario</th>
			<th>Pais</th>
			<th>Ultima atualizaçao</th>
			<th>Responsavel</th>
			<th>E-mail</th>
			<th><a href="/restrict/scope?action=edit">Novo</a></th>			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="object">
			<tr>
				<td>${object.id}</td>
				<td>${object.owner}</td>
				<td>${object.country}</td>
				<td>${object.changed}</td>
				<td>${object.person}</td>	
				<td>${object.email}</td>
						
				<td>
					<a href="/restrict/scope?action=edit&id=${object.id}">Editar</a>
				</td>	
				<td>
					<a href="javascript:remove('${object.id}');">Excluir</a>
				</td>							
			</tr>
		</c:forEach>
	</tbody>
</table>
<a href="/restrict/preferences.jsp">Voltar</a>