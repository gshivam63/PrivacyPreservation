<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Anonymize your data</title>

    <!-- Google Fonts -->
    <!-- Custom Stylesheet -->
    <link rel="stylesheet" href="css/index_style.css">
    <link rel="stylesheet" href="css/afterlogin_style.css">
    <script src="js/jquery.min.js"></script>
</head>

<body>
<form action="/getdatabaseservlet" method="post" name="myForm">
    <%@ page import="java.util.*" %>
    <%
        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache");
        session=request.getSession(false);
        String admin_username =(String) session.getAttribute("USERNAME");
        session.setAttribute("USERNAME", admin_username);
    %>
    <div class="container">
        <div  class="login-box">
            <div class="box-header" style="background-color: #665851">
                <h2 style="margin:10px;">Hi <%=admin_username%>! <a style="color:lavender; float: right; font-size: medium; font-weight: bolder;" href="adminlogoutservlet">Logout</a></h2>
            </div>
            <br/>
            <span class="database_list">
            <%
                // retrieve your list from the request, with casting
                ArrayList<String> list = (ArrayList<String>) request.getAttribute("database_arrayList");
                for(String str : list) {

            %>
           <!-- <span style="font-size: medium;" class="label label-default"><a href="getcolumnservlet?database_name=<%=str%>" name="database_name"><%=str%></a></span>-->
            <div onclick='templateDownload("<%=str%>")'><span style="font-size: medium; cursor: pointer;" class="label label-default"><%=str%></span></div>
            <br/><br/>
            <%
                }
            %>
            <br/>
            </span>
            <span id="template1"></span>
            <span style="color:red; font-size: medium; font-weight: bolder;"> ${param.err} </span>
            <form method="post">
                <div id="templatedownload_reload" hidden></div>
            </form>
            <form method="post">
                <div id="templateupload_reload" hidden></div>
            </form>
        </div>
    </div>
</form>

</body>
<script>
    $(document).ready(function(){
    })
    function templateDownload(fileName){
        //document.getElementsByClassName('database_list')[0].clear();
        $('.database_list').empty();
        //var string1 = "<a href='file:///C:/Users/shivam/IdeaProjects/PrivacyPreservation/web/templates/" + fileName + ".xlsx'> Download template:" + fileName + "</a>";
        var string1 = "<a href='downloadservlet?param1=" + fileName + "'> Download template:" + fileName + "</a>";
        $('#template1').append(string1);
        $('#templatedownload_reload').show();
        var download_string = "<a  href='/getdatabaseservlet'>Show databases</a>";
        $('#templatedownload_reload').append(download_string);
        $('#templateupload_reload').show();
        var upload_string = "<form action=\"annonymizeservlet\" method=\"post\" enctype=\"multipart/form-data\">\n" +
            "    <input type=\"file\" name=\"file_path\" id=\"file_path\" />\n" +
            "    <br />\n" +
            "    <input type=\"submit\" value=\"upload\" />\n" +
            "</form>";
        $('#templateupload_reload').append(upload_string);
    }
</script>
</html>