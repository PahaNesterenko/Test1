
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Login Form</title>
    <link rel="stylesheet" type="text/css" href="resources/css/indexStyle.css" media="screen" />
</head>
<body>
	<section>
		<div id="form">
			<form:form method="post" action="j_spring_security_check">
				<fieldset>
					<p id="error" >
						<c:out value="${param.error}" />
					</p>
					<table style="width: 310px;">
						<tr>
							<td>Login</td>
							<td class="field"><input type="text"
								name="j_username" style="width: 100%;"></td>
						</tr>
						<tr>
							<td>Password</td>
							<td class="field"><input type="password"
								name="j_password" style="width: 100%;"></td>
						</tr>
					</table>
					<button type="submit" class="btn btn-default">Login</button>
					<input type="button" value="Register now"
						onclick="location.href='registration'" />
				</fieldset>
			</form:form>
		</div>
	</section>
</body>
</html>

