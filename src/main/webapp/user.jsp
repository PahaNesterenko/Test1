<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User homepage</title>
</head>
<body>

	<p style="color: red;">
		<c:out value="${error}" />
	</p>

	<p style="font-size: 300%; text-align: center">
		Hello,
		<c:out value="${currentUser.firstName}" />
		!
	</p>

	<p style="text-align: center">
		Click <a href="<c:url value="j_spring_security_logout"/>">here</a> to
		logout
	</p>
</body>
</html>