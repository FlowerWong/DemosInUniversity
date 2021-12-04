<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.Random" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"

%>
<%
    response.setHeader("Cache-Control", "no-cache");
    int width = 80, height = 25;

    //在内存中创建图像
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //获取画笔
    Graphics graphics = image.getGraphics();
    //填充背景色
    graphics.setColor(new Color(200, 200, 200));
    graphics.fillRect(0, 0, width, height);
//
//    //产生随机四位数
//    Random random = new Random();
//    int randNum = random.nextInt(8999) + 1000;
//    String randStr = String.valueOf(randNum);
//
//    //将验证码存入session
//    session.setAttribute("randStr", randStr);
//
//    //将验证码写入图像
//    graphics.setColor(Color.ORANGE);
//    graphics.setFont(new Font("", Font.PLAIN, 25));
//    graphics.drawString(randStr, 10, 17);
//
//    //随机产生100个干扰点
//    for (int i = 0; i < 100; i++) {
//        int x = random.nextInt(width);
//        int y = random.nextInt(height);
//        graphics.drawOval(x, y, 1, 1);
//    }

    //输出图像到页面
    ImageIO.write(image, "JPEG", response.getOutputStream());

%>
