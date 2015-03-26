<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="/restrict/scope?action=save" method="post">
	
	<div>
		<label for="id">Id:</label>
		<input type="text" name="id" value="${object.id}"/>
	</div>
	<div>
		<label for="owner">Owner:</label>
		<input type="text" name="owner" value="${object.owner}"/>
	</div>
	<div>
		<label for="person">Person:</label>
		<input type="text" name="person" value="${object.person}"/>
	</div>	
	<div>
		<label for="country">Country:</label>
		<input type="text" name="country" value="${object.country}"/>
	</div>	
	<div>
		<label for="email">Email:</label>
		<input type="text" name="email" value="${object.email}"/>
	</div>
	<div>
		<label for="changed">Changed:</label>
		<input type="text" name="changed" value="${object.changed}"/>
	</div>						

	<input type="submit" value="Salvar"/>
</form>