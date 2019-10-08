package com.servlet;

import com.dao.GoodsDao;
import com.dao.GoodsDaoImpl;
import com.domain.Goods;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet("/goods_Info")
public class Goods_Info extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getParameter("method");
        System.out.println("goods_Info--method:"+method);

        if (method.equals("ShowList")) {
            this.ShowList(request, response);
        } else if (method.equals("toAddPage")) {
            this.toAddPage(request, response);
        } else if (method.equals("Add")) {
            this.Add(request, response);
        } else if (method.equals("toEditPage")) {
            this.toEditPage(request, response);
        } else if (method.equals("Edit")) {
            this.Edit(request, response);
        } else if (method.equals("Remove")) {
            this.Remove(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    private void ShowList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GoodsDao goodsDao = new GoodsDaoImpl();
        List<Goods> list = goodsDao.showGoods();
        System.out.println("ShowList成功收到list：list.size："+list.size());
        request.setAttribute("goods_list", list);
        request.getRequestDispatcher("/goods/goods_list.jsp").forward(request, response);
    }

    private Goods getGoods(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Goods goods = new Goods();

        //设置图片保存路径  这个路径相对当前的目录
        ServletContext servletContextPath = request.getServletContext();
        String uploadPath = servletContextPath.getRealPath("/uploadFiles");
        System.out.println("上传的路径uploadPath:" + uploadPath);

        //创建一个磁盘工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过磁盘工厂对象获取上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        //通过上传对象解析请求
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        for (FileItem fitem : list) {
            System.out.println("当前list遍历的fileitem对象内容:"+fitem);
            //5.判断FileItem类对象封装的数据是一个普通文本表单字段，
            //还是一个文件表单字段 isFormField=true是文本  isFormField=false是文件
            //isFormField方法的含义是判断某项是否是普通的表单类型
            Boolean is_cover = false;
            if (!fitem.isFormField()) {
                try {
                    System.out.println("该fileitem对象是一个文件------------");
                    InputStream inputStream = fitem.getInputStream();
                    //file 文件夹
                    File file = new File(uploadPath);
                    //如果目录不存在，要创建目录
                    if (!file.exists()) {
                        file.mkdir();//创建目录
                    }
                    String img_name = fitem.getName();

                    /*//避免文件名过长，把存在的uuid删除
                    int str_length = img_name.length();
                    if (str_length >= 26) {
                        String[] str = img_name.split("_");
                        img_name = str[1];
                    }*/

                    String fileName = null;
                    String filePath = null;
                    //input type="file" 没有选择文件时，传回的值是一个空字符，而不是null
                    //img_name.length()<26 由于页面中保存了原有的图片文件名，如果文件名>=26，
                    //则servlet获取到的是原文件名，
                    if (img_name!=""&&img_name.length()<26){
                        //解决重名问题
                        UUID uuid = UUID.randomUUID();
                        System.out.println("即将使用的uuid" + uuid);
                        fileName = uuid.toString() + "_" + img_name;
                        filePath = uploadPath + File.separator + fileName;
                        is_cover = true;
                        goods.setG_goods_pic(fileName);
                        FileOutputStream outputStream = new FileOutputStream(filePath);
                        IOUtils.copy(inputStream, outputStream);
                        outputStream.close();
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //或者使用
                //HashMap<String,String> map = new HashMap<>();
                //String name = fItem.getFieldName();
                //String value = fitem.getString("utf-8");
                //map.put(name,value);
                //Bean.populate(goods,map);

                //fitem.getFieldName();      input 的 name 属性写入的
                //fitem.getString("utf-8");  相应的''值''
                String fitem_name = fitem.getFieldName();
                String value = fitem.getString("utf-8");
                System.out.println("获取到的对象的值："+ value);
                if ("in_id".equals(fitem_name)) {
                    goods.setG_id(Integer.parseInt(value));
                }else if ("in_name".equals(fitem_name)) {
                    goods.setG_goods_name(value);
                } else if ("in_text".equals(fitem_name)) {
                    goods.setG_goods_description(value);
                } else if ("in_price".equals(fitem_name)) {
                    goods.setG_goods_price(Float.parseFloat(value));
                } else if ("in_stock".equals(fitem_name)) {
                    goods.setG_goods_stock(Integer.parseInt(value));
                }
                //或者填入原有的图片文件名，这样就不用再DAO层里判断pic是否改变了
                /*if ("origin_img".equals(fitem_name)){
                System.out.println(value);
                System.out.println(flag);
                if(is_cover == false){
                    //没有修改则使用隐藏域的值
                    goods.setG_goods_pic(value);
                }*/


            }
        }
        return goods;
    }

    private void toAddPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/goods/to_add.jsp").forward(request,response);
    }

    private void Add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Goods goods = this.getGoods(request,response);
        GoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("即将添加的商品：" + goods);
        int result = goodsDao.addGoodsInfo(goods);
        if (result > 0) {
            response.sendRedirect(request.getContextPath()+"/goods_Info?method=ShowList");
            //request.getRequestDispatcher("/goods_Info?method=ShowList").forward(request,response);
        } else {
            request.setAttribute("add_error","添加失败");
            response.sendRedirect(request.getContextPath()+"/goods/goods_list.jsp");
        }
    }


    private void toEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int edit_id = Integer.parseInt(request.getParameter("edit_id"));
        System.out.println("即将修改的信息的id："+edit_id);
        GoodsDao goodsDao = new GoodsDaoImpl();
        Goods edit_goods = goodsDao.getGoodsById(edit_id);
        request.setAttribute("edit_goods",edit_goods);
        request.getRequestDispatcher("/goods/to_edit.jsp").forward(request,response);
    }

    private void Edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Goods goods = getGoods(request,response);
        GoodsDao goodsDao = new GoodsDaoImpl();
        int result = goodsDao.editGoodsInfo(goods);
        if (result>0){
            response.sendRedirect(request.getContextPath()+"/goods_Info?method=ShowList");
            //request.getRequestDispatcher("/goods_Info?method=ShowList").forward(request,response);
        }else {
            request.setAttribute("edit_error","修改失败");
            response.sendRedirect(request.getContextPath()+"/goods/goods_list.jsp");
        }
    }

    private void Remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] str_arr = request.getParameterValues("select");
        System.out.println("页面上获取到的需要删除的id:--"+str_arr.toString());
        GoodsDao goodsDao = new GoodsDaoImpl();
        int result = goodsDao.delGoodsInfo(str_arr);
        if (result>0){
            response.sendRedirect(request.getContextPath()+"/goods_Info?method=ShowList");
            //request.getRequestDispatcher("/goods_Info?method=ShowList").forward(request,response);
        }else {
            request.setAttribute("del_error","删除失败");
            response.sendRedirect(request.getContextPath()+"/goods/goods_list.jsp");
        }
    }

}
