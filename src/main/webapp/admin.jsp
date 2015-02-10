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


    <div id="view1">
        <a href="#/addUser">Add new user</a>
    <table borber="1">

        <thead>
        <tr>
            <td width="100" >Login</td>
            <td width="100" >First name</td>
            <td width="100" >Last name</td>
            <td width="100" >Email</td>
            <td width="100" >Birthday</td>
            <td width="100" >Role</td>
            <td width="100" >Action</td>
        </tr>
        </thead>
        <tbody id="main">
       <%-- <div id="main"></div>--%>

        </tbody>
    </table>

    <script type="text/template" id="userLine">
        <tr>
        <td> <\%= login %> </td>
        <td> <\%= firstName %> </td>
        <td> <\%= lastName %> </td>
        <td> <\%= email %> </td>
        <td> <\%= birthday %> </td>
        <td> <\%= role.name %> </td>
        <td> <a href="#/preEditUser?userId=<\%= id %>" >[edit]</a>
            <a href="#/deleteUser?userId=<\%= id %>" >[delete]</a>
        </td>
        </tr>
    </script>
    </div>

    <div id="view2"></div>

    <script type="text/template" id="form">

        Add user
        <table>
            <tr>
                <td>login</td>
                <td><input type="text" id="login" value="<\%= login%>"> </td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" id="email" value="<\%= email%>"> </td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" id="password" value="<\%= password %>"> </td>
            </tr>
            <tr>
                <td>Confirm password</td>
                <td><input type="password" id="confirmPassword" value="<\%= password %>"> </td>
            </tr>
            <tr>
                <td>First name</td>
                <td><input type="text" id="firstName" value="<\%= firstName%>"> </td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><input type="text" id="lastName" value="<\%= lastName%>"> </td>
            </tr>
            <tr>
                <td>Birthday</td>
                <td><input type="text" id="birthday" value="<\%= birthday %>"> </td>
            </tr>
        </table>
        <input type="button" id="ok" value="Ok">
        <input type="button" onclick='window.location=""' value="Cancel">

    </script>



    <script src="resources/js/underscore.js" type="text/javascript"></script>
    <script src="resources/js/jquery-2.1.1.js" type="text/javascript"></script>
    <script src="resources/js/backbone.js" type="text/javascript"></script>
    <script src="resources/js/scr.js" type="text/javascript"></script>

</body>
</html>