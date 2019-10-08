<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goods.css"/>

<html>
<head>
    <title>修改商品</title>

</head>
<body>
<table>
    <tr>
        <form action="${pageContext.request.contextPath}/goods_Info?method=Edit" id="goods_edit" method="post" enctype="multipart/form-data">
            <td>
                <div class="td">
                    <div class= td_top">
                        <input class="in_name" type="text" name="in_name" value="${edit_goods.g_goods_name}" placeholder="请输入名称"/>
                    </div>
                    <div class="td_img">
                        <div class="in_img_down">
                            <img src="${pageContext.request.contextPath}/uploadFiles/${edit_goods.g_goods_pic}">
                        </div>
                        <div class="td_img_up">
                            <input type="file" name="in_img"/>
                            <input type="hidden" name="origin_img" value="${edit_goods.g_goods_pic}"/>
<%--                            value是否是src--%>
                        </div>
                    </div>
                    <div class="td_text">
                        <textarea class="in_text" name="in_text" placeholder="请输入简述">${edit_goods.g_goods_description}</textarea>
                    </div>
                    <div class="td_info">
                        <table>
                            <tr>
                                <td>价格</td>
                                <td>库存</td>
                            </tr>
                            <tr>
                                <td><input class="in_num" type="number" value="${edit_goods.g_goods_price}" name="in_price" min="0" step="0.1" placeholder="   number1"/></td>
                                <td><input class="in_num" type="number" value="${edit_goods.g_goods_stock}" name="in_stock" min="0" placeholder="   number2"></td>
                            </tr>
                            <input type="hidden" name="in_id" value="${edit_goods.g_id}"/>
                        </table>
                    </div>
                    <div class="td_bottom">
                        <input class="in_submit" type="submit" name="sbumit" value="提 交"/>
                    </div>
                </div>
            </td>
        </form>
    </tr>
</table>
</body>
</html>
