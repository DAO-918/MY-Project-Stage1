<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/25
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css"/>--%>

<html>
<head>
    <title>主页</title>
    <style>
        *{
            margin:0px;
            padding:0px;
        }
        body{
            background-image: url("img/背景图片.jpg");
            background-position: center center;
            background-size: cover;
        }
        .div_center{
            width:500px;
            height:300px;
            background-color: white;
            border:6px solid #A6A6A6;
            margin-left: auto;
            margin-right: auto;
            margin-top: 150px;
            padding:15px;
        }
        .div_top{
            float: top;
            width:500px;
            text-align: center;

        }
        .div_top span{
            font-size: 20px;
            text-align: center;
        }
        .div_left_right1{
            float: left;
            width:150px;
            height:200px;
            margin-top: 30px;
            margin-left: 50px;
        }
        .div_left_right2{
            float: right;
            width:150px;
            height:200px;
            margin-top: 30px;
            margin-right: 50px;
        }
        .div_button{
            width:150px;
            height:200px;
            border: 7px solid rgba(255,254,173,0.82);
            border-radius: 25px;
            background-color: rgba(255,208,112,0.82);
            text-align: center;
            text-decoration: none;
            font-size: 30px;
            font-weight: bold;
        }
    </style>
</head>
<%
    Cookie[] cookies = request.getCookies();
    Map<String, String> map = new HashMap<String, String>();
    if (cookies!=null) {
        String userinfo = null;
        for (Cookie cookie : cookies) {
            if("userinfo".equals(cookie.getName())){
                userinfo = cookie.getValue();
            }
        }
        if(userinfo!=null){
            String[] info = userinfo.split("&");
            for (int i = 0; i < info.length; i++) {
                String[]  str = info[i].split("=");
                map.put(str[0], str[1]);
            }
        }
    }
    String username = map.get("username");
%>
<body>

<div class="bg">
<div class="div_center">
    <div class="div_top">
        <span>
        <c:if test="${empty sessionScope.LoginStatus}">您还未登录，请登录</c:if>
        <c:if test="${not empty sessionScope.LoginStatus}">欢迎，<%=username%></c:if>
        </span>
    </div>
    <div class="div_left_right1">
        <c:if test="${empty sessionScope.LoginStatus}">
            <button class="div_button" type="button" onclick="window.location.href='user/login.jsp'"/>登录</button>
        </c:if>
        <c:if test="${not empty sessionScope.LoginStatus}">
            <button class="div_button" type="button" onclick="window.location.href='goods/goods_list.jsp'"/>商品</button>
        </c:if>
    </div>
    <div class="div_left_right2">
        <c:if test="${empty sessionScope.LoginStatus}">
            <button class="div_button" type="button" onclick="window.location.href='user/register.jsp'"/>注册</button>
        </c:if>
        <c:if test="${not empty sessionScope.LoginStatus}">
            <button class="div_button" type="button" onclick="Logout()"/>注销</button>
        </c:if>
<%--        <a href="/login_Servlet?method=Logout"--%>
    </div>
</div>
</div>

</body>

<script>
    function Logout(){
        if(confirm("确认退出登录？")){
            //这样在servlet没有获取到method，
            location.href="logout_Servlet";

        }else {
            alert("取消退出登录");
        }
    //logout_error
    }
</script>
</html>
