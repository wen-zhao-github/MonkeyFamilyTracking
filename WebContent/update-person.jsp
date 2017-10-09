<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update info</title>

</head>
<body>
<h2>hahah to update</h2>
<form action = "PersonServlet" method = "GET">
    <input type= "hidden" name="command" value = "UPDATE"/>
    <input type = "hidden" name="personid" value= "${theCurrentPerson.id}"/>
	<table>
		<tr>
			<td>First Name: </td>
			<td><input type = "text" name = "firstname" value = "${theCurrentPerson.firstName}"></td>
		</tr>
		<tr>
			<td>Last Name: </td>
			<td><input type = "text" name = "lastname" value = "${theCurrentPerson.lastName}"></td>
		</tr>
		<tr>
			<td>Email: </td>
			<td><input type = "text" name = "email" value = "${theCurrentPerson.email}"></td>
		</tr>
	
	</table>
	<br/><br/>
	<input type = "submit" value="Submit"/>
</form>

</body>
</html>