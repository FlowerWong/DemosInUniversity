package csu.edu.wxh.demo.servlets;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "Validate", value = "/Validate")
public class Validate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        int width = 80, height = 25;

        //在内存中创建图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics graphics = image.getGraphics();
        //填充背景色
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);

        //产生随机四位字符
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int tmp = random.nextInt(35);
            if (tmp > 25) {
                sb.append(tmp - 25);
            } else {
                sb.append((char) (tmp + 'A'));
            }
        }
        String randStr = sb.toString();

        //将验证码存入session
        HttpSession session = request.getSession();
        session.setAttribute("randStr", randStr);

        //将验证码写入图像
        graphics.setColor(Color.blue);
        graphics.setFont(new Font("", Font.PLAIN, 25));
        graphics.drawString(randStr, 5, 20);

        graphics.setColor(Color.BLACK);
        //随机产生100个干扰点
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawOval(x, y, 1, 1);
        }

        //输出图像到页面
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
