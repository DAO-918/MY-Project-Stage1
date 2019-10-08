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

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>

</head>
<body>
<div id="rg_layout">
    <!--左边的部分-->
    <div id="rg_left">
        <p>新用户注册</p>
        <p>USER REGISTER</p>
    </div>

    <!--中间的部分-->
    <div id="rg_center">
        <form action="/MY-Project-Stage1/login_Servlet?method=Register" id="registerForm" method="post">
        <table>
            <tr>
                <td class="td_left"><label for="username">用户名</label></td>
                <td class="td_right"><input type="text" name="username" id="username" onchange="checkUsername()" placeholder="  请输入用户名"></td>
                <td class="td_error"><span id="username_span" class="error"></span></td>
            </tr>

            <tr>
                <td class="td_left"><label for="password">密码</label></td>
                <td class="td_right"><input type="password" name="password" id="password" onchange="checkPassword()" placeholder="  请输入密码"></td>
                <td class="td_error"><span id="password_span" class="error"></span></td>
            </tr>

            <tr>
                <td class="td_left"><label>性别</label></td>
                <td class="td_right">
                    <input type="radio" name="sex" onchange="checkSex()" value="男"> 男
                    <input type="radio" name="sex" onchange="checkSex()" value="女"> 女
                </td>
                <td class="td_error"><span id="sex_span" class="error"></span></td>
            </tr>
            <tr>
                <td class="td_left"><label>爱好</label></td>
                <td class="td_right">
                    <input type="checkbox" name="hobbies" onchange="checkHobbies()" value="篮球"> 篮球
                    <input type="checkbox" name="hobbies" onchange="checkHobbies()" value="足球"> 足球
                    <input type="checkbox" name="hobbies" onchange="checkHobbies()" value="唱歌"> 唱歌
                    <input type="checkbox" name="hobbies" onchange="checkHobbies()" value="跳舞"> 跳舞
                </td>
                <td class="td_error"><span id="hobbies_span" class="error"></span></td>
            </tr>
            <tr>
                <td class="td_left"><label for="phone">Phone</label></td>
                <td class="td_right"><input type="phone" name="phone" id="phone" onchange="checkPhone()" placeholder="  请输入号码"></td>
                <td class="td_error"><span id="phone_span" class="error"></span></td>

            </tr>
            <tr>
                <td class="td_left"><label for="email">Email</label></td>
                <td class="td_right"><input type="text" name="email" id="email" onchange="checkEmail()" placeholder="  请输入邮箱"></td>
                <td class="td_error"><span id="email_span" class="error"></span></td>
            </tr>

            <tr>
                <td class="td_left"><label for="address">地址</label></td>
                <td class="td_right"><input type="text" name="address" id="address" onchange="checkAddress()" placeholder="  请输入地址"></td>
                <td class="td_error"><span id="address_span" class="error"></span></td>
            </tr>

            <tr>
                <td class="td_left"><label for="check_code2" >验证码</label></td>
                <td class="td_right"><input type="text" name="check_code2" id="check_code2" onchange="checkCode()" placeholder="  请输入验证码">
                    <img src="/MY-Project-Stage1/check_Code2" id="img_check"></td>
                <td class="td_error"><span id="check_span" class="error"></span></td>
            </tr>
            <tr>
                <td class="td_submit" colspan="3" align="center"><input type="submit" value="注册" id="btn_submit"></td>
            </tr>
            <%--<div align="center">
                <input type="submit" value="注册" id="btn_submit">
            </div>--%>
        </table>
        </form>
    </div>
    <!--右边的部分-->
    <div id="rg_right">
        <p>已有账号？<a href="${pageContext.request.contextPath}/user/login.jsp">立即登录</a>
        </p>
    </div>
</div>
</body>
<script>
    window.onload = function(){
        document.getElementById("registerForm").onsubmit=function(){
            var flag1 = checkUsername();
            var flag2 = checkPassword();
            var flag3 = checkPhone();
            var flag4 = checkEmail();
            var flag5 = checkAddress();
            var flag6 = checkCode();
            var flag7 = checkSex();
            var flag8 = checkHobbies();
            return flag1&&flag2&&flag3&&flag4&&flag5&&flag6&&flag7&&flag8;
        }
        document.getElementById("img_check").onclick = function () {
            this.src = "/MY-Project-Stage1/check_Code2?time="+new Date().getTime();
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

    function checkPhone() {
        var reg = /^1\d{10}$/;//11位数字，以1开头
        var phone = document.getElementById("phone").value;
        var phone_span = document.getElementById("phone_span");
        if (reg.test(phone)){
            phone_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else {
            phone_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

    function checkEmail() {
        var reg = /^([\w\.\-]+)\@(\w+)(\.([\w^\_]+)){1,2}$/;///^[\w\.\-]+@\w*[\.]?\w*/
        var email = document.getElementById("email").value;
        var email_span = document.getElementById("email_span");
        if (reg.test(email)){
            email_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else {
            email_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

    function checkAddress() {
        var reg = /^[A-Z|a-z\u4e00-\u9fa5]{2,12}$/;
        var address = document.getElementById("address").value;
        var address_span = document.getElementById("address_span");
        if (reg.test(address)){
            address_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else {
            address_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
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

    function checkSex() {
        var sex = document.getElementsByName("sex");
        var sex_span = document.getElementById("sex_span");
        var flag = 0;
        for (var i=0;i<sex.length;i++){
            if (sex[i].checked==true){
                flag++;
            }
        }
        if (flag>0){
            sex_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else {
            sex_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

    function checkHobbies() {
        var hobbies = document.getElementsByName("hobbies");
        var hobbies_span = document.getElementById("hobbies_span");
        var flag = 0;
        for (var i=0;i<hobbies.length;i++){
            if (hobbies[i].checked==true){
                flag++;
            }
        }
        if (flag>0){
            hobbies_span.innerHTML="<img src='/MY-Project-Stage1/img/true.jpg' width='30' height='30'/>";
            return true;
        }else {
            hobbies_span.innerHTML="<img src='/MY-Project-Stage1/img/false.jpg' width='30' height='30'/>";
            return false;
        }
    }

</script>
</html>
