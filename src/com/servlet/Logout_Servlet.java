package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout_Servlet")
public class Logout_Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("LoginStatus");

        Cookie[] cookies = request.getCookies();
        Boolean flag = false;
        for (Cookie c:cookies){
            if (c.getName().equals("userinfo")){
                //Cookie newCookie=new Cookie("userinfo","");//假如要删除名称为username的Cookie
                c.setMaxAge(0); //立即删除型
                //c.setPath("/"); //在tomcat里如果部署了多个项目，且该cookie对这些项目都有效
                //但在这里c.setPath("/");反而会使删除cookie的操作失效
                // --原因？？？更改了cookie的Path导致浏览器没有正确指向名称为userinfo的cookie？？？
                response.addCookie(c); //重新写入，将覆盖之前的
                flag = true;
            }
        }

        if (flag){
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            request.setAttribute("logout_error","退出登录失败");
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
