<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Anonymize your data</title>
    <!-- Google Fonts -->
    <!-- Custom Stylesheet -->
    <link rel="stylesheet" href="css/index_style.css">
    <link rel="stylesheet" href="css/afterlogin_style.css">
    <script src="js/jquery-1.11.1.min.js"></script>
</head>
<body>
<%@ page import="java.util.*" %>
<%
    session=request.getSession(false);
    String admin_username =(String) session.getAttribute("USERNAME");
    session.setAttribute("USERNAME", admin_username);
%>
<div class="container">
    <div  class="login-box">
        <div class="box-header" style="background-color: #665851">
            <h2 style="margin:10px;">Hi <%=admin_username%>! <a style="color:lavender; float: right; font-size: medium; font-weight: bolder;" href="adminlogoutservlet">Logout</a></h2>
        </div>
        <a  href='/getdatabaseservlet'>Show databases</a>

        <br/>
    </div>
</div>
</body>
</html>