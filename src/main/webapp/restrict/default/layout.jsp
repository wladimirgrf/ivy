<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/restrict/default/head.jsp"/>
	</head>
	<body>
		<div class="ym-wrapper" id="wrapper">
			<div class="ym-wbox">
				<div id="header">
					<img src="/public/img/logo-horizontal.png" width="110px">
				</div>
				<div class="ym-column">
					<div class="ym-col1" id="content">
						<div class="ym-cbox">
							<jsp:include page="${content}"/>
						</div>
					</div>
					<div class="ym-col3" id="menu">
						<div class="ym-cbox">
							<jsp:include page="/restrict/default/menu.jsp"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>