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
        <a style="color:mediumseagreen; margin-bottom:10px; padding-bottom:10px;" href='/getdatabaseservlet'>Show databases</a>
        <span class="column_list">
            <%
                //retrieve fileName
                String fileName=(String)session.getAttribute("fileName");
                session.setAttribute("fileName", fileName);
                // retrieve your list from the request, with casting
                ArrayList<String> column_list = (ArrayList<String>) request.getAttribute("column_arrayList");
                session.setAttribute("column_arrayList",column_list);
                for(String str : column_list) {
            %>
         <form action="uploadservlet" method="post" enctype="multipart/form-data">
            <div><span style="font-size: medium; cursor: pointer;" class="label label-default"><%=str%></span></div>
            <select name=<%=str%> id=<%=str%> >
                <option value="No_Encryption">No_Encryption</option>
                <option value="Generalisation">Generalisation</option>
                <option value="Random_Anonymization">Random_Anonymization</option>
            </select>
            <br/>
            <%
                }
            %>
            <br/>
        </span>

                <input type="file" name="file_path" id="file_path" />
                <br />
                <input type="submit" value="upload" />
        </form>
        <%
            String upload_message=(String)session.getAttribute("upload_message");
            upload_message= (String) request.getAttribute("upload_message");
            if(upload_message!=null)
                session.setAttribute("upload_message",upload_message);
            if(upload_message==null)
                upload_message=(String)session.getAttribute("upload_message");
            if(upload_message!=null){
        %>
        <span style="color:green; font-size: medium; font-weight: bolder;"> <%=upload_message%> </span>
        <br/><br/>
        <form action="Review.jsp" method="post">
            <input style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Proceed" />
        </form>
        <%
            }
        %>
    </div>
</div>
</body>
</html>