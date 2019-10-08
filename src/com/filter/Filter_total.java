package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class Filter_total implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        String method = request.getParameter("method");
       /*Boolean is_Logout = false;
        System.out.println("Filter_total--method:"+method);
        if ("Logout".equals(method)){
            is_Logout = true;
        }*/

        String uri = request.getRequestURI();
        System.out.println("Filter_total--requestURI："+uri);//check_Code

        Object LoginStatus = request.getSession().getAttribute("LoginStatus");

        String autoLogin = null;

            if (!"true".equals(LoginStatus) || LoginStatus == null) {
                //浏览器可能不允许发送cookie
                Cookie[] cookies = request.getCookies();
                Map<String, String> map = getAutoLogin(cookies);
                autoLogin = map.get("autoLogin");
                //String username = map.get("username");
            }
            if ("true".equals(autoLogin)) {
                //为什么要添加该句
                //如果网站存在cookie免登录则在服务器设置一个session，用于服务器判断登录状态
                if (!"true".equals(LoginStatus)) {
                    request.getSession().setAttribute("LoginStatus", "true");
                }
            }

        //LoginStatus = request.getSession().getAttribute("LoginStatus")
        /**第二次加上该句会发生什么？LoginStatus = request.getSession().getAttribute("LoginStatus");
         * 重新获取了名称为LoginStatus的session
         * 结合上面写的两个if判断语句：
         * 第一个判断：浏览器第一次访问服务器，尚未产生关于登录的session，获取cookie中 免登录autoLogin 的值
         * 第二个判断：如果免登录autoLogin 的值为true，即用户选择了记住我的状态，服务器创建session用于判断浏览器连接数据库这段时间的登录权限
         * 另外：单次登录（不记住我的状态）session在loginServlet中创建
         *
         * 可以看出，创建session后没有对变量LoginStatus进行重新赋值，
         * 使下面的判断认为，用户尚未登陆，会跳转到登录页面
         * 跳转到登录页面，还会经过过滤器，此时 由于上面LoginStatus=true的session创建成功
         * 使下面的判断认为，用户已经登陆，会跳转到index 主页面
         * 跳转到index 主页面，还会经过过滤器，由于第一句是对index.jsp放行
         * 直接转到请求的页面
         *
         * 一共经历了3次过滤，增加了服务器的负担
         *
         * 再次加上该句，会直接使下面的判断认为，用户已经登陆，可以直接转到目标页面
         * 只经历了一次过滤，但不会返回到index 主页面
         * */

        //一个过滤器中一定只能存在一个连续的if else 判断，
        // 否则会多次放行，造成页面的重复
        if (uri.contains("index.jsp")||uri.contains("/logout_Servlet")||uri.contains("register.jsp")||uri.contains("/login_Servlet")){
            chain.doFilter(req, resp);
        }else if (uri.contains(".css")||uri.contains(".png")||uri.contains("/img")){
            chain.doFilter(req, resp);
        }else if ("true".equals(LoginStatus)){
            //登录过：
            //1.访问登录注册的页面
            //2.访问登录注册以外的页面
            if (uri.contains("/user/")||uri.contains("/login_Servlet")||uri.contains("/check_Code")){
                //||uri.contains("/login_Servlet")拦截了关于登陆注册，也拦截了login_Servlet注销
                //解决方法有三，在过滤器中注销，不规范？ 或者是重新写一个servlet 或者实在前面放行当method=Logout时的请求
                //不能通过||uri.contains("method=Logout")来判断是否放行，
                //因为发送退出登录请求时，Filter_total--requestURI：/MY-Project-Stage1/login_Servlet
                //即method=Logout不放在uri中，而是request.getParameter("method")请求头中
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }else {
                chain.doFilter(req, resp);
            }

        }else{
            req.setCharacterEncoding("utf-8");
            //未登录过：
            //1.访问登录注册的页面
            //2.访问登录注册以外的页面
            if (uri.contains("/user/")||uri.contains("/login_Servlet")||uri.contains("/check_Code")||uri.contains("index.jsp")){
                chain.doFilter(req, resp);
            }else{
                request.getSession().setAttribute("filter_error","请登录");
                response.sendRedirect(request.getContextPath()+"/user/login.jsp");
            }
        }
    }
    private Boolean containWith(String uri){
        Boolean flag = uri.contains("/user/")||uri.contains("/login_Servlet")||uri.contains("/check_Code");
        return flag;
    }

    public Map<String,String> getAutoLogin(Cookie[] cookie){
        Map<String, String> map = new HashMap<String, String>();
        if (cookie!=null) {
            String userinfo = null;
            for (Cookie cook : cookie) {
                if("userinfo".equals(cook.getName())){
                    userinfo = cook.getValue();
                    System.out.println("Cooide--userinfo--:"+userinfo);
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

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter_total过滤器启动");
    }
    public void destroy() {
        System.out.println("Filter_total过滤器销毁");
    }

}
