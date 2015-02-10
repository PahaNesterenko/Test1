<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<br />
	<p style="font-size: 150%; text-align: left; margin-left: 20px;">
		<c:if test="${type == 'editUser'}">
			<c:out value="Edit User" />
		</c:if>
		<c:if test="${type == 'addUser'}">
			<c:out value="Add user" />
		</c:if>
	</p>
	<section>
		<div>
			<form:form method="POST" action="${type}" modelAttribute="userForm">
				<input type="hidden" name="type" value="${type}" />
				<table border="1">

					<c:if test="${type == 'editUser'}">
						<tr>
							<th>Login:*</th>
							<td><form:input name="login" path="login" value="${userToEdit.login}" readonly="${'true'}" />
							<td><form:errors path="login" cssClass="errors" /></td>
						</tr>

					</c:if>

					<c:if test="${type == 'addUser'}">

						<tr>
							<th>Login:*</th>
							<td><form:input name="login" path="login" value="${userToEdit.login}" readonly="${'false'}" />
							<td><form:errors path="login" cssClass="errors" /></td>
						</tr>

					</c:if>


					<tr>
						<th>Password:*
						<td><form:input path="password" name="password" value="${userToEdit.password}" type="password" />
						<td><form:errors path="password" cssClass="errors" /></td>

					</tr>
					<tr>
						<th>Confirm password:*
						<td><form:input path="confirmPassword" name="confirmPassword" value="${userToEdit.password}" type="password" />
						<td><form:errors path="confirmPassword" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Email:*
						<td><form:input name="email" path="email" value="${userToEdit.email}" />
						<td><form:errors path="email" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>First name:*
						<td><form:input name="firstName" path="firstName" value="${userToEdit.firstName}" />
						<td><form:errors path="firstName" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Last name:*
						<td><form:input name="lastName" path="lastName" value="${userToEdit.lastName}" />
						<td><form:errors path="lastName" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Birthday:*
						<td><form:input title="Expected date format: YYYY-MM-DD"
								name="birthDate" path="testBirthday"
								value="${userToEdit.birthday}" />
						<td><form:errors path="testBirthday" cssClass="errors" /></td>
					</tr>
					<tr>
						<th>Role:*
						<td><select name="role" size="1">
								<option
									<c:if test="${userToEdit.role.name == 'user'}"> selected </c:if>>user</option>
								<option
									<c:if test="${userToEdit.role.name == 'admin'}"> selected </c:if>>admin</option>
						</select>
					</tr>
				</table>
				<p style="text-align: left; margin-right: 10px;">
					<input type="submit" name="ok" value="ok" /> 
					<input type="button" value="Cancel" onclick="location.href='admin'" />
				</p>
				<p style="color: red;">
					<c:out value="${error}" />
				</p>
			</form:form>
			<h5>All fields must be filled.</h5>
		</div>
	</section>
</body>
</html>
