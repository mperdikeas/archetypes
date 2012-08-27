<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Some Security Home Page</title>
</head>
<body>
<h3>Welcome to The Some Security Home Page</h3>
<p>Our web site does have some security so NOT everyone can visit all our pages.</p>
<p>If you haven't <a href='login.jsp'>logged in</a> you cannot visit the <a href='secure/index.jsp'>pages in the secure area</a>.</p>
<p>You most definitely cannot see our <a href='GetAllUsers'> secret users list.</a></p>
<p>If you are logged in you can view the above pages.  You should <a href='LogoutUser'>log out</a> when 
you're finished.</p>
</body>
</html>