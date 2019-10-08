package com.check;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/check_Code2")
public class Check_Code_2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 70;
        int height = 30;
        //宽度,高度,颜色类型RGB
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bImage.getGraphics();
        //画笔颜色设置
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,0,width,height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("微软雅黑",Font.BOLD,20));
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int code = 0;
        for (int i = 0;i < 4;i++){
            code = ran.nextInt(10);
            graphics.drawString(code+"",5+15*i,23);
            sb.append(code);
        }
        request.getSession().setAttribute("checkCode2_value",sb.toString());

        graphics.setColor(Color.WHITE);
        for (int i = 0;i < 10;i++){
            graphics.drawLine(ran.nextInt(width),ran.nextInt(height),ran.nextInt(width),ran.nextInt(height));
        }
        ImageIO.write(bImage,"jpg",response.getOutputStream());
    }
}

