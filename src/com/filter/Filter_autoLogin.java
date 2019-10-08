package com.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@WebFilter("/user/*")

public class Filter_autoLogin implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        System.out.println("Filter_autoLogin--requestURI："+uri);//check_Code
        //先查看是否登录过
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies){
            System.out.println(c.toString());
        }
        Map<String, String> map = getAutoLogin(cookies);
        String autoLogin = map.get("autoLogin");
        String username = map.get("username");
        Object LoginStatus = request.getSession().getAttribute("LoginStatus");

        if ("true".equals(autoLogin)) {
            //为什么要添加该句
            //如果网站存在cookie免登录则在服务器设置一个session，用于服务器判断登录状态
            if (!"true".equals(LoginStatus)) {
                request.getSession().setAttribute("LoginStatus", "true");
            }
            chain.doFilter(req, resp);
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter_autoLogin过滤器启动");

    }
    public void destroy() {
        System.out.println("Filter_autoLogin过滤器销毁");
    }

    public Map<String,String> getAutoLogin(Cookie[] cookie){
        Map<String, String> map = new HashMap<String, String>();
        if (cookie!=null) {
            String userinfo = null;
            for (Cookie cook : cookie) {
                if("userinfo".equals(cook)){
                    userinfo = cook.getValue();
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
        return map;
    }

}
