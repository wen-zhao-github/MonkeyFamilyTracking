<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Family member list</title>
</head>
<body>
<h2>hahahahahaha</h2>
<table border = "1px">
	<tr>	
		<td>First Name</td>
		<td>Last Name</td>
		<td>Email</td>
		<td>Operation</td>
	</tr>
	
		<c:forEach var = "temp" items = "${familyMem}">
			<c:url var = "tempUpdate" value = "PersonServlet">
				<c:param name = "command" value="LOAD"/>
				<c:param name= "personid" value="${temp.id}"/>		
			</c:url>
			<c:url var = "tempDelete" value = "PersonServlet">
				<c:param name = "command" value = "DELETE"/>
				<c:param name = "personid" value = "${temp.id}"/>
			</c:url>
			<tr>
				<td>${temp.firstName}</td>
				<td>${temp.lastName}</td>
				<td>${temp.email}</td>
				<td><a href = "${tempUpdate}">UPDATE</a> | <a href = "${tempDelete}">DELETE</a></td>
			</tr>
		</c:forEach>
 	

</table>
<br/><br/>

<a href = "add-person.html">Add new member</a>
</body>
</html>