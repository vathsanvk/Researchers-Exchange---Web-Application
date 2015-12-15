<%-- 
    Document   : header
    Created on : Sep 19, 2015, 4:50:37 PM
    Author     : Srivathsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Researchers Exchange Participations</title>
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body>

        <div id="header">
            <nav id="header_menu">
                <ul  class="left" >
                    <li>Researchers Exchange Participations</li>
                </ul>

                <ul class="right">
                    <c:if test="${theUser == null}">
                        <li><a href="userController?action=about">About Us</a></li>
                        <li><a href="userController?action=how">How it Works</a></li>
                        <li><a href="userController?action=login_page">Login</a></li>
                        </c:if>
                        <c:if test="${theUser != null}">
                        <li><a href="userController?action=about">About Us</a></li>
                        <li><a href="userController?action=main">How it Works</a></li>
                        <li>Hello, <c:out value="${theUser.name}" /></li>
                        </c:if>
                </ul>

            </nav>



        </div>

