<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css"/>

<html>
<head>
    <title>请登录</title>
    <style>
        *{
            margin:0px;
            padding:0px;
        }
        h5{
            font-size: 20px;
            color: deepskyblue;
        }
        .img1{
            float: left;
            margin-left: 10px;
        }
        .div_check{
            height: 30px;
            width: 240px;
        }
        .input_sty3{
            width: 160px;
            /*margin: 5px;*/
            height: 30px;
            float: left;
            /*border-radius: ;*/
        }
        #rg_layout{
            overflow: hidden;
        }
        #rg_center2{
            float:left;
            margin-left:30px;
            margin-top: 30px;
            margin-bottom: 45px;
        }
        .div_pic{
            width: 600px;
            height: 250px;
            overflow:hidden;/*img自动裁剪*/
            margin: 0px auto 0px auto;
            background-color: white;
            border:3px solid #A6A6A6 ;
            border-radius: 10px;
            float: bottom;
            /*position: relative;*/
        }
        .div_pic img{
            min-height: 100%;
            min-width: 100%;
            max-height: 100%;
            max-width: 100%;
        }
        .div_error{
            /*width: 100px;*/
            float: right;
            /*margin-right: 100px;*/
            /*margin-bottom: 5px;*/
            /*margin-top: 10px;*/
            /*margin-right: 50px;*/
            text-align: center;
            font-weight: bold;
            color: #ff3e51;
            word-wrap: break-word; /*自动换行*/

        }
        #rg_center{
            margin-bottom: 15px;
        }
        /*提交表单的样式*/
        .td_submit{
            position: relative;
            /*vertical-align: middle;*/
        }
        .select_login{
            position: absolute;
            vertical-align: middle;
            font-size: 12px;
            /*top: 1px;*/
            /*bottom: 50px;*/
            /* float: left;*/
            margin-top: 10px;
            /*margin-bottom: -1px;*/
         }
        .input_login{
            position: absolute;
            left: 150px;
        }

    </style>
</head>
<body>
<div id="rg_layout">
    <div id="rg_left">
        <p>用户登录</p>
        <p>USER LOGIN</p>
    </div>

    <!--中间的部分-->
    <div id="rg_center2">
<form action="/MY-Project-Stage1/login_Servlet?method=Login" id="loginForm" method="post">
    <table id="login_table">
        <tr>
            <td class="td_left"><label for="username">用户名</label></td>
            <td class="td_right"><input type="text" name="username" id="username" onchange="checkUsername()" placeholder="  请输入用户名"/></td>
            <td class="td_error"><span id="username_span" class="error"></span></td>
        </tr>
        <tr>
            <td class="td_left"><label for="password">密码</label></td>
            <td class="td_right"><input type="password" name="password" id="password" onchange="checkPassword()" placeholder="  请输入密码"/></td>
            <td class="td_error"><span id="password_span" class="error"></span></td>
        </tr>
        <tr>
            <td class="td_left"><label for="check_code2" >验证码</label></td>
            <td class="td_right">
                <div class="div_check">
                <input class="input_sty3" type="text" id="check_code2" name="check_code" onchange="checkCode()" placeholder="  请输入验证码"/>
                <img class="img1" src="/MY-Project-Stage1/check_Code" id="img">
                </div>
            </td>
            <td class="td_error"><span id="check_span" class="error"></span></td>
        </tr>
        <tr>
            <td class="td_submit" colspan="3" align="center">
                <div class="select_login">
                    <input type="checkbox" name="select_log" id="select_log" value="true"/><label for="select_log">&nbsp;记住我的登录状态</label>
                </div>
                <div class="input_login">
                <input type="submit" id="btn_submit" name="submit" value="登录"/>
                </div>
                <div class="div_error">

                    <c:if test="${empty login_error}&&${empty check_error}">
                        <c:if test="${not empty filter_error}">
                            ${filter_error}
                        </c:if>
                    </c:if>

                    <c:if test="${not empty login_error}">
                        ${login_error}
                    </c:if>
                    <c:if test="${not empty check_error}">
                        ${check_error}
                    </c:if>
                </div>
            </td>
        </tr>
    </table>
</form>
    </div>
    <!--右边的部分-->
    <div id="rg_right">
        <p>没有账号？<a href="${pageContext.request.contextPath}/user/register.jsp">立即注册</a>
        </p>
    </div>

    <div class="div_pic">

        <img id="img_turn" src="/MY-Project-Stage1/img/bg_1.png">
    </div>

</div>
</body>
<script>
    var i=1;
    function img_turns() {
        i++;
        var img = document.getElementById("img_turn");
        img.src = "/MY-Project-Stage1/img/bg_"+i+".png";
        if (i==5){
            i=0;
        }
    }
    var interval1 = window.setInterval(img_turns,2000);
    window.onload = function(){
        document.getElementById("loginForm").onsubmit=function(){
            var flag1 = checkUsername();
            var flag2 = checkPassword();
            var flag6 = checkCode();
            return flag1&&flag2&&flag6;
        }
        document.getElementById("img").onclick = function () {
            this.src = "/MY-Project-Stage1/check_Code?time="+new Date().getTime();
        }
    }
    //验证用户名
    function checkUsername(){
        var reg = /^[A-Z|a-z\u4e00-\u9fa5]{2,12}$/;
        //获取到了用户名
        var username = document.getElementById("username").value;
        var username_span = document.getElementById("username_span");
        if(reg.test(username)){
            //验证通过，设置图片
            username_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else{
            username_span.innerHTML= "<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

    //验证密码
    function checkPassword(){
        var reg = /\w{6,24}/;//6-24个字符，数字，字母和下划线_
        //获取到了输入的密码
        var password = document.getElementById("password").value;
        var password_span = document.getElementById("password_span");
        if(reg.test(password)){
            //验证通过，设置图片
            password_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else{
            password_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }
    function checkCode() {
        var reg = /^\w{4}$/;
        var check_code2 = document.getElementById("check_code2").value;
        var check_span = document.getElementById("check_span");
        if (reg.test(check_code2)){
            check_span.innerHTML="&nbsp;";
            return true;
        }else {
            check_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

</script>
</html>
