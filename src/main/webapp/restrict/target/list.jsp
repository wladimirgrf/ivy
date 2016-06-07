<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 						 %>
<script type="text/javascript">
	function remove(id) {
		
		swal({
			  title: "Tem certeza de que deseja excluir?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Sim, deletar!",
			  closeOnConfirm: false
			},
			function(){
			  window.location.href = "/restrict/target?action=delete&id=" + id;
			}
		);
	}
</script>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Target</strong>
		</li>	
	</ul>
</nav>
<table class="list">
	<thead>
		<tr>
			<th>Id</th>
			<th>Host</th>
			<th></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="object">
			<tr>
				<td>${object.id}</td>
				
				<td>${object.host}</td>
						
				<td class="td">
					<a href="/restrict/target?action=edit&id=${object.id}">Editar</a>
				</td>	
				<td class="td">
					<a href="javascript:remove('${object.id}');">Excluir</a>
				</td>							
			</tr>
		</c:forEach>
	</tbody>
</table>