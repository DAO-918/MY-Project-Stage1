package com.servlet;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/login_Servlet")
public class Login_Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getParameter("method");
        System.out.println("login_Servlet--method:"+method);

        if (method.equals("Login")){
            this.Login(request,response);
        }else if(method.equals("toLoginPage")){
            this.toLoginPage(request,response);
        }else  if (method.equals("toRegisterPage")){
            this.toRegisterPage(request,response);
        }else if (method.equals("Register")){
            this.Register(request,response);
        }else if (method.equals("toEditPage")){
            this.toEditPage(request,response);
        }else if (method.equals("Edit")){
            this.Edit(request,response);
        }else if (method.equals("Remove")){
            this.Remove(request,response);
        }

        /*if (method.equals("Logout")){
            this.Logout(request,response);
        }else*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    private void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/login.jsp").forward(request,response);
    }

    private void Login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //7天免登录：登录成功后在客户端添加一个cookie
        //前端通过解析cookie获取存储的用户名和密码，输入正确的验证码
        //再次进入数据库验证用户名和密码
        //都正确时，服务器添加一个session：LoginStatus
        //服务器通过session：LoginStatus 判断是否登录，而不是使用cookie进行判断

        //想法--扩展--
        //cookie安全：在设置cookie时，对用户名和密码进行加密
        //当服务器不存在名称为LoginStatus的session时，对cookie进行解析
        //用户名和密码正确时，LoginStatus为true，且返回用户名给浏览器，则浏览器可获取到用户名，不用在浏览器上解析cookie
        request.setCharacterEncoding("utf-8");

        //一、为什么设置了request.setCharacterEncoding("utf-8");还会乱码
        //解决方案：
        //1.String username = new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        //先以iso-8895-1进行解码，username = URLEncoder.encode(username, "ISO-8859-1");
        //然后再以utf-8进行编码，username = URLDecoder.decode(username, "UTF-8");//缩减后为第一句的格式
        //2.修改Tomcat/conf 目录下 server.xml
        //useBodyEncodingForURI="true" URIEncoding="UTF-8"
        //•useBodyEncodingForURI参数表示是否用request.setCharacterEncoding参数对URL提交的数据和表单中GET方式提交的数据进行重新编码，在默认情况下，该参数为false。
        //•URIEncoding参数指定对所有GET方式请求进行统一的重新编码（解码）的编码。
        //URIEncoding是对所有GET方式的请求的数据进行统一的重新编码，
        //而useBodyEncodingForURI则是根据响应该请求的页面的request.setCharacterEncoding参数对数据进行的重新编码，
        //不同的页面可以有不同的重新编码的编码。
        //https://blog.csdn.net/qq_43342406/article/details/83477616
        //request.getParameter(“参数名”) 中文乱码解决方法【新手设置问题】【JSP】-表单传值问题：为什么设置UTF-8之后还是乱码？

        //https://blog.csdn.net/lxf512666/article/details/52939573
        //Servlet中文乱码原因 解决 Get 和 Post 和客户端

        String username = new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String password = new String(request.getParameter("password").getBytes("iso-8859-1"),"utf-8");
        String check_code = new String(request.getParameter("check_code").getBytes("iso-8859-1"),"utf-8");
        //<input type="checkbox" name="select_log" id="select_log" value="true"/>
        // 当勾选时传回的值为true否则为null
        String select = request.getParameter("select_log");
        if (select==null){
            select="false";
        }
        //而String str = new String() 括号中传入的值不能为空，否则会报空指针异常
        String autoLogin = new String(select.getBytes("iso-8859-1"),"utf-8");
        //没必要  autoLogin = "false";
        /*if (!autoLogin.equals("true")){
            autoLogin = "false";
        }*/
        //验证码
        String checkCode_value = (String) request.getSession().getAttribute("checkCode_value");
        Boolean bool = false;
        if (check_code.equals(checkCode_value)) {

            UserDao userDao = new UserDaoImpl();
            String password_real = userDao.getUserByName(username);
            System.out.println("password_real:"+password_real);
            System.out.println("password-----:"+password);
            if (password_real != null && password.equals(password_real)) {
                bool = true;
                request.getSession().setAttribute("LoginStatus", "true");
            }

        }else {
            request.setAttribute("check_error","验证码错误");
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        }
        //1.如果之前张三不使用记住我的状态登录，cookie名为userinfo的值为username=张三&password=123123&autoLogin=false
        //然后不注销，服务器重新部署(redeploy)，session自动清空，此时刷新页面不会自动登录
        //2.这时牛六使用记住我的状态登录，cookie名为userinfo的值为username=牛六&password=123123&autoLogin=true
        //传回浏览器后，浏览器中的cookie会被覆盖
        //不要添加&&"true".equals(autoLogin)，即点击记住我的登录状态才添加cookie
        if (bool){
            Cookie cookie = new Cookie("userinfo","username="+username+"&"+"password="+password+"&"+"autoLogin="+autoLogin);
            cookie.setMaxAge(60*60*24*7);//7天免登录
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("login_error", "密码或用户名错误");
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        }
    }

    private void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    private void toRegisterPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/register.jsp").forward(request,response);
    }

    private void Register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String check_code2 = request.getParameter("check_code2");
        String checkCode2_value = (String) request.getSession().getAttribute("checkCode2_value");
        if (check_code2.equals(checkCode2_value)) {
            User user = this.getInfo_noid(request,response);
            UserDao userDao = new UserDaoImpl();
            System.out.println("插入的信息："+user);
            int result = userDao.addUserInfo(user);
            System.out.println("影响的行数："+result);

            if (result>0){
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }else {
                request.setAttribute("add_user_error","注册失败");
                response.sendRedirect(request.getContextPath()+"/user/register.jsp");
                //request.getRequestDispatcher("/user/register.jsp").forward(request,response);
            }
        }else {
            request.setAttribute("check_error","验证码错误");
            response.sendRedirect(request.getContextPath()+"/user/register.jsp");
        }

    }

    private void toEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/user_change.jsp").forward(request,response);
    }

    private void Edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = this.getInfo_noid(request,response);
        int u_id = Integer.parseInt(request.getParameter("u_id"));
        user.setU_id(u_id);
        UserDao userDao = new UserDaoImpl();
        int result = userDao.editUserInfo(user);
        if (result>0){
            request.getSession().setAttribute("edit_info","修改信息成功");
            response.sendRedirect(request.getContextPath()+"/user/welcome.jsp");
        }else {
            request.setAttribute("edit_user_error","修改失败");
            request.getRequestDispatcher("/user/user_change.jsp").forward(request,response);
        }
    }

    private void Remove(HttpServletRequest request, HttpServletResponse response){

    }

    private User getInfo_noid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String u_username = new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String u_password = new String(request.getParameter("password").getBytes("iso-8859-1"),"utf-8");
        String u_sex = new String(request.getParameter("sex").getBytes("iso-8859-1"),"utf-8");
        String[] hobbies = request.getParameterValues("hobbies");
        StringBuffer str_buff = new StringBuffer();
        for (String str:hobbies){
            str_buff.append(str);
            str_buff.append(" ");
        }
        String u_hobbies = new String(str_buff.substring(0,str_buff.length()-1).getBytes("iso-8859-1"),"utf-8");
        String u_phone = new String(request.getParameter("phone").getBytes("iso-8859-1"),"utf-8");
        String u_email = new String(request.getParameter("email").getBytes("iso-8859-1"),"utf-8");
        String u_address = new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
        String is_delete = "0";
        User user = new User(0,u_username,u_password,u_sex,u_hobbies,u_phone,u_email,u_address,is_delete);
        return user;
    }
}
