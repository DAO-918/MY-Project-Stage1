<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%--<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">--%>

<%--相对路径是相对于地址栏，当转发后http://localhost:8080/MY-Project-Stage1/goods_Info?method=ShowList
    相对路径指的是上面的路径而不是http://localhost:8080/MY-Project-Stage1/--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goods.css"/>
<html>
<head>
    <title>商品展示</title>
    <style>
        .top_div_img{
            height: 50px;
            width: 200px;
            overflow: hidden;
            margin: 5px;
        }
        .top_div_img img{
            max-width: 100%;
            max-height: 100%;
            min-width: 100%;
            min-height: 100%;
        }
        .left_div_button{
            position: absolute;
            top: 100px;
            left: 7px;
        }
        .left_div_button table tr td{
            padding: 3px;
        }
        .left_div_button table tr td button{
            width: 60px;
            height: 60px;
            background-color: rgba(90,200,255,0.82);
            border: 3px solid rgba(160,255,248,0.82);
            border-radius: 5px;
            font-size: 20px;
            font-weight: bold;
        }
        .left_div_img{
            height: 300px;
            width: 150px;
            position: absolute;
            float: bottom;
            /*bottom: 30px;*/
            left: 50px;
            top:300px;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div id="all">

<div id="top_div">
<div class="top_div_img">
    <img src="/MY-Project-Stage1/img/header.png">
</div>
</div>

<div id="left_div">
    <div class="left_div_button">
        <table align="center" border="0px" cellpadding="5px">
            <tr>
                <td>
                    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/goods_Info?method=ShowList'">显示</button>
                </td>
                <td>
                    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/goods_Info?method=toAddPage'">tianjia</button>
                </td>
                <td>
                    <button type="button" onclick="removeGoods()">删除</button>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <button type="button" onclick="window.location.href='index.jsp'">返回</button>
                </td>
            </tr>
        </table>
        <div class="left_div_img">
            <img id="left_pic" src="/MY-Project-Stage1/img/p1.png" onclick="changePic()">
        </div>
<%--        <p><a href="">显示商品信息</a></p>--%>
<%--        <p><a href="${pageContext.request.contextPath}/goods_Info?method=toAddPage">添加商品信息</a></p>--%>
<%--        <p><a href="${pageContext.request.contextPath}/goods_Info?method=toEditPage">修改商品信息</a></p>--%>
<%--        <p><a href="javascript:void (0)" onclick="removeGoods()">批量删除信息</a></p>--%>
    </div>
</div>

<div id="list_div">
<form id="goods_form" action="${pageContext.request.contextPath}/goods_Info?method=Remove" method="post">
    <table align="center" cellpadding="0" cellspacing="5px" border="0px">

<c:if test="${not empty goods_list}">
        <tr>
    <%-- items="stuList" 没有使用EL表达式 导致页面一直没有获取到传过去的list  --%>
    <c:forEach items="${goods_list}" var="goods" varStatus="s">

        <%--如果最后一个td元素不是最后一个添加一个id属性--%>
            <td <c:if test="${s.last==true&&s.count%4!=0}">id="td_last" </c:if> >
                <div class="td">
                    <div class="td_top">
                        <span>NO.${s.count}</span>
                        <span>${goods.g_goods_name}</span>
                    </div>
                    <div class="td_img">
                        <img src="${pageContext.request.contextPath}/uploadFiles/${goods.g_goods_pic}">
                        <%--${goods.g_goods_price} uploadFiles/41b2cce2-022d-4bc5-ac22-514b34019830_E-Pufferfish.png 404--%>
                    </div>
                    <div class="td_text">
                        <p>${goods.g_goods_description}</p>
                    </div>
                    <div class="td_info">
                        <table>
                            <tr>
                                <td>价 格</td>
                                <td>库 存</td>
                            </tr>
                            <tr>
                                <td>${goods.g_goods_price}</td>
                                <td>${goods.g_goods_stock}</td>
                            </tr>
                        </table>
                    </div>
                    <div class="td_bottom">
                        <div class="td_bottom_input">
                            <label class="checkbox-inline i-checks" >
                                <input type="checkbox" class="select_cb" name="select" value="${goods.g_id}"/>
                                <i></i>
                            </label>
                        </div>
                        <button onclick="window.location.href='${pageContext.request.contextPath}/goods_Info?method=toEditPage&edit_id=${goods.g_id}'" type="button">修 改</button>
                    </div>
                </div>
            </td>
        <%--如果是第四个td元素添加tr元素换到下一行--%>
        <c:if test="${s.count%4==0}"></tr></c:if>
        <c:if test="${s.count%4==0}"><tr></c:if>
        <c:if test="${s.last==true&&s.count%4==0}"><td id="td_last"></td></c:if>
    </c:forEach>
        </tr>
</c:if>

    </table>
</form>
</div>

</div>

</body>

<script>
    function removeGoods() {
        var is_select = document.getElementsByName("select");
        var flag = 0;
        for (var i = 0;i<is_select.length;i++){
            if (is_select[i].checked==true){
                flag++;
            }
        } 
        if (flag>0){
            document.getElementById("goods_form").submit();
        }
    }
    var i = 1;
    function changePic() {
        var img = document.getElementById("left_pic");
        img.src="/MY-Project-Stage1/img/p"+i+".png";
        i++;
        if (i==6){
            i=1;
        }
    }
</script>

</html>
