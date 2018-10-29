<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.sql.*" %>

<html>

<head>


<title>Login</title>

</head>

<body>

<%! String userdbName;

String userdbPsw;

String dbUsertype;
String dbenct;

%>

<%

	Connection con= null;
	
	PreparedStatement ps = null;
	PreparedStatement ps2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;



		String driverName = "com.mysql.jdbc.Driver";

		String url = "jdbc:mysql://localhost:3306/project1";

		String user = "root";

		String dbpsw = "Kaushambi123";



	String sql = "select * from user432 where registerno=? and pass=? and usertype=?";
	String sql2 = "select enct from encr where regno=? ";


String registerno = request.getParameter("registerno");
String password = request.getParameter("password");
String usertype = request.getParameter("usertype");
String encrt = request.getParameter("enct");

if((!(registerno.equals(null) || registerno.equals("")) && !(password.equals(null) || 
password.equals(""))) && !usertype.equals("select"))
{
try{
Class.forName(driverName);
con = DriverManager.getConnection(url, user, dbpsw);
ps = con.prepareStatement(sql);
ps2 = con.prepareStatement(sql2);
ps.setString(1, registerno);
ps.setString(2, password);
ps.setString(3, usertype);
ps2.setString(1, registerno);
rs = ps.executeQuery();
rs2 = ps2.executeQuery();
if(rs.next()&&rs2.next())
{ 
userdbName = rs.getString("registerno");
userdbPsw = rs.getString("pass");
dbUsertype = rs.getString("usertype");
dbenct = rs2.getString("enct");

	if(registerno.equals(userdbName) && password.equals(userdbPsw) && usertype.equals(dbUsertype))
		{	
			session.setAttribute("encryption_type",dbenct);
			session.setAttribute("registerno",userdbName);
			session.setAttribute("usertype", dbUsertype); 
			response.sendRedirect("welcome.jsp"); 
		} 
	}
	else
	response.sendRedirect("error.jsp");
	rs.close();
	ps.close(); 
	}
		catch(SQLException sqe)
		{
			out.println(sqe);
		} 
}
else
		{
%>
<center><p style="color:red">Error In Login</p></center>
<% 
		getServletContext().getRequestDispatcher("/home.jsp").include(request, response);
		}
%>
</body>
</html>