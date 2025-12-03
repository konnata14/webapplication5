<%@ page contentType="text/html; charset=UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
        response.sendError(403, "Access denied");
        return;
    }
%>
<html><body>
<h2>Админская страница</h2>
<p>Только для администратора.</p>
<p><a href="<%= request.getContextPath() %>/profile">Назад</a></p>
</body></html>
