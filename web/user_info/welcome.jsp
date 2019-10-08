<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>欢迎</title>
    <style>
        *{
            margin:0px;
            padding:0px;
        }
    </style>
</head>
<body>
<c:if test="${empty sessionScope.login_user}">请登录</c:if>
<c:if test="${not empty sessionScope.login_user}">欢迎，${sessionScope.login_user}</c:if>
<a href="../goods/good_list.jsp">商品</a>
</body>
</html>
