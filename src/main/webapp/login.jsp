<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/7/24
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/test/login" method="post">
    <input type="text" placeholder="请输入账户" name="userName">
    <input type="password" placeholder="请输入密码" name="password">
    <input type="submit" value="提交">
</form>
<h1>${requestScope.error}</h1>
</body>
</html>
