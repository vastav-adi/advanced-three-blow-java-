<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Welcome</title>

</head>

<body>

<p>Welcome ,<%=session.getAttribute("registerno")%>,<%=session.getAttribute("usertype") %></p>
<br><br>
<%
	/* String etype = (String)session.getAttribute("encryption_type"); */
%>



<p><a href="home.jsp">Logout</a>

</body>

</html>