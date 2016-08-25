<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<form action="/restrict/scope?action=save" class="ym-form" method="post">
	
	<div class="ym-fbox-text">
		<input type="hidden" id="current_id">

		<label for="id">Id:</label>
		<input type="text" name="id" id="id" value="${object.id}"/>

		<label for="owner">Owner:</label>
		<input type="text" name="owner" value="${object.owner}"/>

		<label for="person">Person:</label>
		<input type="text" name="person" value="${object.person}"/>

		<label for="country">Country:</label>
		<input type="text" name="country" value="${object.country}"/>

		<label for="email">Email:</label>
		<input type="text" name="email" value="${object.email}"/>

		<label for="changed">Changed:</label>
		<input type="text" name="changed" value="${object.changed}"/>
					
		<input type="submit" value="Salvar"/>
	</div>
</form>

<script type="text/javascript">
	$(window).load(function(){ 	
		if($("#id").val().length > 0){
			$("#id").attr('disabled','disabled');
			
			$("#current_id").attr({
				"name" : "id",
				"value" : $("#id").val(),
			});
		}
	});
</script>