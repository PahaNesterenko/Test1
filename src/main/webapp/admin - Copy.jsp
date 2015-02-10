<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin homepage</title>
</head>
<body>

    <div align="center">
        <p style="font-size: 100%; text-align: right">
            Admin <c:out value="${currentUser.firstName}" /> (<a href="<c:url value="j_spring_security_logout"/>">logout</a>)
        </p>


        <div id="AllUsers">
            <p align="center"><a href="#/preAddUser">Add User</a></p>
            <table align="center" border="1">
                <thead>
                <tr bgcolor="gray">
                    <td width="100">Login</td>
                    <td width="100">First name</td>
                    <td width="100">Last name</td>
                    <td width="100">Age</td>
                    <td width="100">Role</td>
                    <td width="100">Action</td>
                </tr>
                <thead>
                <tbody id="usersTable">
                <script id="usersList" type="text/template">
                    <td> <\%= login %></td>
                    <td> <\%= firstName %></td>
                    <td> <\%= lastName %></td>
                    <td> <\%= age %></td>
                    <td> <\%= role.name %></td>
                    <td width=\"100\"><a href="#/preEditUser?userId=<\%=id %>">Edit</a> <a href="#/deleteUser?userId=<\%=id %>"
                                                                                         onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </script>
                </tbody>
            </table>
        </div>

    </div>
    <div id="addOrEdit" align="center">
        <script id="addOrEditScript" type="text/template">
            <h1 id="head"></h1>
            <form id="form">
                <table>
                    <tr>
                        <td>Login</td>
                        <td><input id="login" type="text" value="<\%= login %>" name="login"/></td>
                        <td><span id="loginError"></span></td>
                    </tr>

                    <tr>
                        <td>Password</td>
                        <td><input type="password" value="<\%= password %>" name="password"/></td>
                        <td><span id="passwordError"></span></td>
                    </tr>

                    <tr>
                        <td>Confirm password</td>
                        <td><input type="password" value="<\%= password %>" name="confirmPassword"/></td>
                        <td><span id="confirmPasswordError"></span></td>
                    </tr>

                    <tr>
                        <td>Email</td>
                        <td><input type="email" value="<\%= email %>" name="email"/></td>
                        <td><span id="emailError"></span></td>
                    </tr>

                    <tr>
                        <td>First name</td>
                        <td><input type="text" value="<\%= firstName %>" name="firstName" id="firstName"/></td>
                        <td><span id="firstNameError"></span></td>
                    </tr>

                    <tr>
                        <td>Last name</td>
                        <td><input type="text" value="<\%= lastName %>" name="lastName"/></td>
                        <td><span id="lastNameError"></span></td>
                    </tr>

                    <tr>
                        <td>Birthday(YYYY-MM-DD)</td>
                        <td><input type="text" value="<\%= birthday %>" name="birthday"/></td>
                        <td><span id="birthdayError"></span></td>
                    </tr>
                    <tr>
                        <td>Role</td>
                        <td>
                            <select name="role" >
                                <option <c:if test="${role.name == 'user'}"> selected </c:if>>user</option>
                                <option <c:if test="${role.name == 'admin'}"> selected </c:if>>admin</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <input type="button" value="Ok" id="submit">
                            <input type="button" value="Cancel" onclick="window.location='#'">
                        </td>
                    </tr>
                </table>
            </form>
        </script>
    </div>
    <script src="resources/js/underscore.js" type="text/javascript"></script>
    <script src="resources/js/jquery-2.1.1.js" type="text/javascript"></script>
    <script src="resources/js/backbone.js" type="text/javascript"></script>
    <script src="resources/js/scr.js" type="text/javascript"></script>

</body>
</html>