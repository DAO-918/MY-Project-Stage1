<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goods.css"/>

<html>
<head>
    <title>添加商品</title>
    <style>


    </style>
</head>
<body>
<table>
<tr>
    <form action="${pageContext.request.contextPath}/goods_Info?method=Add" id="goods_add" method="post" enctype="multipart/form-data">
        <td>
            <div class="td">
                <div class= td_top">
                    <input class="in_name" type="text" name="in_name" placeholder="请输入名称"/>
                </div>
                <div class="td_img">
                    <input class="in_img" type="file" name="in_img"/>
                </div>
                <div class="td_text">
                    <textarea class="in_text" name="in_text" placeholder="请输入简述"></textarea>
                </div>
                <div class="td_info">
                    <table>
                        <tr>
                            <td>价格</td>
                            <td>库存</td>
                        </tr>
                        <tr>
                            <td><input class="in_num" type="number" name="in_price" min="0" step="0.1" placeholder="   number1"/></td>
                            <td><input class="in_num" type="number" name="in_stock" min="0" placeholder="   number2"></td>
                        </tr>
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
