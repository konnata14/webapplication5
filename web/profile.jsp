<%@ page contentType="text/html; charset=UTF-8" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head><meta charset="utf-8"/><title>Кабинет</title></head>
<body>
<h2>Кабинет пользователя: <%= user.getUsername() %></h2>
<p>Роль: <%= user.getRole() %></p>

<h3>Загрузка изображения</h3>
<form method="post" action="<%= request.getContextPath() %>/upload" enctype="multipart/form-data">
    <input type="file" name="image" accept="image/*" />
    <button type="submit">Загрузить</button>
</form>
<% if (session.getAttribute("uploadedImage") != null) { %>
<p>Ваша картинка:</p>
<img src="<%= request.getContextPath() + "/" + session.getAttribute("uploadedImage") %>" style="max-width:300px"/>
<% } %>

<p><a href="<%= request.getContextPath() %>/">На главную</a> |
    <a href="<%= request.getContextPath() %>/logout">Выйти</a></p>

<%-- доступ для администратора/модератора --%>
<% if ("ADMIN".equalsIgnoreCase(user.getRole())) { %>
<p><a href="<%= request.getContextPath() %>/admin">Админская страница</a></p>
<% } %>

</body>
</html>
