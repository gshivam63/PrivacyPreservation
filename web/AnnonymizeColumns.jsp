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
                String fileName=(String)request.getAttribute("fileName");
                // retrieve your list from the request, with casting
                ArrayList<String> column_list = (ArrayList<String>) request.getAttribute("column_arrayList");
                session.setAttribute("column_arrayList",column_list);
                for(String str : column_list) {
            %>
        <form action="uploadservlet" method="post" enctype="multipart/form-data" onsubmit='return review_annoymization("<%=fileName%>")'>
            <div><span style="font-size: medium; cursor: pointer;" class="label label-default"><%=str%></span></div>
            <select name=<%=str%> id=<%=str%> >
                <option>Encryption</option>
                <option>Masking</option>
                <option>Generalisation</option>
                <option>Deletion</option>
                <option>No Operation</option>
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
        <input onclick='review_annoymization("<%=fileName%>")' style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Review Annonymize" />
        <br/>
        <input onclick='upload_annoymization("<%=fileName%>")' style="cursor: pointer; color: mediumseagreen; padding-top:10px;" type="submit" value="Upload Annonymized Data" />
        <br/>
    </div>
</div>
</body>
<script>
    function review_annoymization(fileName){
        alert("review Annonymization "+fileName);
        <%
            int i=0;
            for(String str : column_list) {
                i++;
        %>
        alert(<%=i%>);
        var col_name="<%=str%>";
        var col_option=document.getElementById("<%=str%>");
        alert(col_name + " " + col_option.value);
        <%
                System.out.print(i+" ");
            }
        %>
    }
    function upload_annoymization(fileName){
        alert("upload Annonymization "+fileName);
    }
</script>
</html>