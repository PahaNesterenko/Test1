<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true"%>
<c:set var="contPath" value="${pageContext.request.contextPath}" />
<c:set var="loginService"
	value="${contPath}${applicationScope['appResources']['LOGIN_SERVICE']}" />
<html>
<head>
<title>Error</title>
</head>
<body>
	<h1>Opps, error occured...</h1>
	<p>
	go to <a href="${loginService}">main page</a>
	
	<table border="1">
		<tr valign="top">
			<td width="40%"><b>Error:</b></td>
			<td>${pageContext.exception}</td>
		</tr>
		<tr valign="top">
			<td><b>URI:</b></td>
			<td>${pageContext.errorData.requestURI}</td>
		</tr>
		<tr valign="top">
			<td><b>Status code:</b></td>
			<td>${pageContext.errorData.statusCode}</td>
		</tr>
		<tr valign="top">
			<td><b>Stack trace:</b></td>
			<td><c:forEach var="trace"
					items="${pageContext.exception.stackTrace}">
					<p>${trace}</p>
				</c:forEach></td>
		</tr>
	</table>
</body>
</html>