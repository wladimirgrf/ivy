<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"                       %>

<c:choose>
    <c:when test="${isAdmin || userId == object.id}">
        <form action="/restrict/user?action=save" class="ym-form" method="post">
			<div class="ym-fbox-text">
				<input type="hidden" name="id" value="${object.id}"/>
					
				<label for="name">Name:</label>
				<input type="text" name="name" value="${object.name}"/>
		
				<label for="email">E-mail:</label>
				<input type="text" name="email" value="${object.email}"/>
				
				<label for="password">Password:</label>
				<input type="password" name="password" />
				
				<c:if test="${isAdmin}">
					<label for="securityRule">Security Rule:</label>
					<input type="text" name="securityRule" value="${object.securityRule}"/>
					
					<div class="ym-fbox-check" style="padding-top: 10px;">
						<label for="active">Active</label>		
						<input type="checkbox" name="active" ${object.active ? 'checked' : ''} />
			   		</div>
				</c:if>
				
				<input type="submit" value="Salvar"/>
			</div>
		</form>
    </c:when>    
    <c:otherwise>
		<form class="ym-form">
			<div class="ym-fbox-text">
		      	<label>Name:   ${object.name}</label>
				<label>E-mail: ${object.email}</label>
			</div>
		</form>
    </c:otherwise>
</c:choose>
