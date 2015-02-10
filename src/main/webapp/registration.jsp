<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='tags' tagdir='/WEB-INF/tags'%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
  div {
	align: center; margin-left: 0px;
	width: 550px;
	padding: 10px;
}  
</style>
</head>
<body>

	<p style="color: red;">
		<c:out value="${error}" />
	</p>
    <br/>
	<p style="font-size: 150%; text-align: left; margin-left: 30px;">
		<c:out value="Registration" />
	</p>
	<section>
		<div>
			<form:form method="POST" action="registrationDo"
				modelAttribute="userForm">
				<table border="1">

					<tr>
						<th>Login:</th>
						<td><form:input name="login" path="login"
								value="${userToEdit.login}" /> <input type="hidden" name="role"
							value="user" />
						<td><form:errors path="login" cssClass="errors" /></td>
					</tr>

					<tr>
						<th>Password:
						<td><form:input path="password" name="password"
								value="${userToEdit.password}" type="password" />
						<td><form:errors path="password" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Confirm password:
						<td><form:input path="confirmPassword" name="confirmPassword"
								value="${userToEdit.password}" type="password" />
						<td><form:errors path="confirmPassword" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Email:
						<td><form:input name="email" path="email"
								value="${userToEdit.email}" />
						<td><form:errors path="email" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>First name:
						<td><form:input name="firstName" path="firstName"
								value="${userToEdit.firstName}" />
						<td><form:errors path="firstName" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Last name:
						<td><form:input name="lastName" path="lastName"
								value="${userToEdit.lastName}" />
						<td><form:errors path="lastName" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Birthday:
						<td><form:input title="Expected date format: YYYY-MM-DD"
								name="birthDate" path="testBirthday"
								value="${userToEdit.birthday}" />
						<td><form:errors path="testBirthday" cssClass="errors" /></td>
					</tr>

				</table>
				
				<tags:captureTag
					privateKey="6Lf8evkSAAAAAN_1DMvh5w0qw9zbnrq0OBCwpN5T"
					publicKey="6Lf8evkSAAAAAGR5cvydcpBL4T4xRj9_wNOh0ZBy" />
				<p style="text-align: left; margin-left: 0px;">
					 <input type="submit" name="ok" value="ok" /> 
					 <input type="button" value="Cancel" onclick="location.href='index.jsp'" />
				</p>
			</form:form>
			<h5>All fields must be filled.</h5>
		</div>
	</section>
</body>
</html>
