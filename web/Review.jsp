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
    String fileName=(String)session.getAttribute("fileName");
    session.setAttribute("fileName", fileName);
    String upload_message= (String) session.getAttribute("upload_message");
    session.setAttribute("upload_message",upload_message);
%>
    <div class="container">
        <div  class="login-box">
            <div class="box-header" style="background-color: #665851">
                <h2 style="margin:10px;">Hi <%=admin_username%>! <a style="color:lavender; float: right; font-size: medium; font-weight: bolder;" href="adminlogoutservlet">Logout</a></h2>
            </div>
            <br/>
            <a style="color:mediumseagreen; margin-bottom:10px; padding-bottom:10px;" href='/getdatabaseservlet'>Show databases</a>
            <br/><br/>
            <form method="post" action="/downloadannonymizedservlet">
                <input  style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Download Annonymized Data" />
            </form>
            <br/>
            <form method="post" action="/deleteannonymizedservlet">
                <input style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Delete Annonymized Data" />
            </form>
            <br/>
            <form method="post" action="/uploadannonymizedservlet">
                <input style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Upload & Delete Annonymized Data" />
            </form>
            <%
                String delete_msg= (String) request.getAttribute("delete_msg");
                if(delete_msg!=null){
            %>
                <span style="color:green; font-size: medium; font-weight: bolder;"> <%=delete_msg%> </span>
            <%
                }
            %>
            <br/>
            <%
                String upload_msg= (String) request.getAttribute("upload_msg");
                if(upload_msg!=null){
            %>
            <span style="color:green; font-size: medium; font-weight: bolder;"> <%=upload_msg%> </span>
            <%
                }
            %>
            <br/>
        </div>
    </div>

</body>
</html>