<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>User Area Main Page</title>
</head>
<body>
<h3>User Area Main Page</h3>
<h3>Welcome <shiro:principal /></h3> <!-- <h3>Welcome <jsec:principal/> </h3> I believe that, previously, this tag didn't work -->
<p>Since our web site DOES have security, including access by role, only people logged in with a role of user can visit this web page because it's in our user area.</p>
<p><a href="<c:url value='/index.jsp' />">Home</a>
 <shiro:hasRole name="admin">
 | <a href="<c:url value='/admin/index.jsp' />"> Admin Area </a>
 </shiro:hasRole>
 <shiro:hasPermission name="secure">
 | <a href="<c:url value='/staff/index.jsp' />" > Staff Area</a>
 </shiro:hasPermission>
 | <a href="<c:url value='/LogoutUser' />">Log Out</a></p>
</body>
</html>