<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Date, java.util.concurrent.atomic.AtomicLong" %>
<%
    AtomicLong counter = (AtomicLong) application.getAttribute("visitCounter");
    if (counter == null) {
        counter = new AtomicLong(0);
        application.setAttribute("visitCounter", counter);
    }
    long current = counter.incrementAndGet();
%>
<html>
<head><meta charset="utf-8"/><title>Главная</title></head>
<body>
<h1>Добро пожаловать на портал «НЕФТЬПЕНЗ»</h1>
<p>Счётчик посещений (сервер): <strong><%= current %></strong></p>
<p>Текущее время на сервере: <strong><%= new Date() %></strong></p>

<% if (session.getAttribute("user") == null) { %>
<p><a href="<%= request.getContextPath() %>/login">Войти</a></p>
<% } else { %>
<p><a href="<%= request.getContextPath() %>/profile">Кабинет</a> |
    <a href="<%= request.getContextPath() %>/logout">Выйти</a></p>
<% } %>

</body>
</html>
