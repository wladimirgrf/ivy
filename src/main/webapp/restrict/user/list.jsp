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
			  window.location.href = "/restrict/user?action=delete&id=" + id;
			}
		);
	}
</script>
<nav class="ym-hlist">
	<ul>
		<li class="active">
			<strong>Template</strong>
		</li>
		<c:if test="${isAdmin}">
			<li>
				<a href="/restrict/user?action=edit">Novo</a>
			</li>	
		</c:if>	
	</ul>
</nav>
<table class="list">
	<thead>
		<tr>
			<th>Id</th>
			<th>E-mail</th>
			<th></th>
			<c:if test="${isAdmin}">
				<th></th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="object">
			<c:if test="${isAdmin || userId == object.id}">
				<tr>
					<td>${object.id}</td>
					
					<td>${object.email}</td>
							
					<td class="td">
						<a href="/restrict/user?action=edit&id=${object.id}">Editar</a>
					</td>
					<c:if test="${isAdmin}">
						<td class="td">
							<a href="javascript:remove('${object.id}');">Excluir</a>
						</td>
					</c:if>						
				</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>