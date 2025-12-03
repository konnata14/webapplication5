<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><meta charset="utf-8"/><title>Вход</title></head>
<body>
<h2>Вход</h2>
<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Логин: <input type="text" name="username" /></label><br/>
    <label>Пароль: <input type="password" name="password" /></label><br/>
    <button type="submit">Войти</button>
</form>
<p><a href="${pageContext.request.contextPath}/">На главную</a></p>
</body>
</html>
