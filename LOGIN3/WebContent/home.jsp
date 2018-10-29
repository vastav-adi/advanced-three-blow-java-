<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page import="java.sql.*" %>
<%@ page import= "java.io.UnsupportedEncodingException"%>
<%@ page import= "java.security.MessageDigest"%>
<%@ page import= "java.security.NoSuchAlgorithmException"%>
<%@ page import= "java.util.Arrays"%>
<%@ page import= "java.util.Base64"%>
 
 <%@ page import= "javax.crypto.Cipher"%>
 <%@ page import= "javax.crypto.spec.SecretKeySpec"%>


<html>

	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Home</title>
	
	</head>

	<body>

		<%
		Connection con= null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/project1";
		String user = "root";
		String password = "Kaushambi123";

		String sql1 = "select distinct usertype from user432";
		String sql2 = "select distinct enct from encr";
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql1);
			
			rs = ps.executeQuery(); 
			
			Statement statement = con.createStatement() ;
			rs2 =statement.executeQuery("select distinct enct from encr ") ;
		%>

		<form name="myform" method="post" action="login.jsp">
		<center><h2 style="color:green">LOGIN</h2></center>
		<table border="1" align="center">
			<tr>
				<td>Enter Your registration no. :</td>
				<td><input type="text" name="registerno"/></td>
			</tr>
			<tr>
				<td>Enter Your Password :</td>
				<td><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td>Select UserType</td>
				<td><select name="usertype">
					<option value="select">select</option>
				<%
				while(rs.next())
				{
					String usertype = rs.getString("usertype");
				%>
					<option value=<%=usertype%>>  <%=usertype%> </option>
				<% 
				}
			
				%>
					</select>
				</td>
			</tr>
			<tr>
				<td>Select encryption type</td>
				<td><select name="enct">
					<option value="encryptype">select</option>
				<%
				
				
				
				
				while(rs2.next())
				{
					String enct = rs2.getString("enct");
				%>
					<option value=<%=enct%>><%=enct%></option>
				<% 
				}
			}
		catch(SQLException sqe)
				{
					out.println("home"+sqe);
				}
				%>
					</select>
				</td>
			</tr>
			<tr><td></td>
				<td><input type="submit" value="submit"/></td>	
			</tr>	
		</table>
		</form>
	</body>
</html>