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

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript">
        function val()
        {
            var a=document.forms["myForm"]["username"].value;
            var b=document.forms["myForm"]["password"].value;
            if((!a.match(/\S/)) || (!b.match(/\S/)) || (!c.match(/\S/))){
                alert("All Fields are Required");
                return false;
            }
            if(a!=b)
            {
                alert("new password and retype new password not Matched");
                return false;
            }
        }
	</script>
</head>

<body>
	<form action="/adminloginservlet" method="post" name="myForm" onsubmit="return val()">
		<%
			
		%>
		<div class="container">
			<div  class="login-box">
				<div class="box-header" style="background-color: #665851">
					<h2>Log In</h2>
				</div>
				<label for="username">Admin Name</label>
				<br/>
				<input type="text" id="username" name="username">
				<br/>
				<label for="password">Password</label>
				<br/>
				<input type="password" id="password" name="password">
				<br/>
				<button type="submit">Sign In</button>
				<br/>
				<span style="color:red; font-size: medium; font-weight: bolder;"> ${param.err} </span>
				<span style="color:green; font-size: medium; font-weight: bolder;"> ${param.msg} </span>
				<br/>
			</div>
		</div>
	</form>
</body>

<script>
	$(document).ready(function () {
    	$("input:text:visible:first").focus();
	});
	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	});
</script>

</html>