package csu.edu.wxh.demo.servlets;

import csu.edu.wxh.demo.beans.User;
import csu.edu.wxh.demo.dao.UserDao;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author FlowerWang
 */
@WebServlet(name = "AccountLoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //以字节流的形式读取传来的数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletInputStream is = request.getInputStream();
        byte[] buffer = new byte[1024];

        while (is.read(buffer) > 0) {
            outputStream.write(buffer);
        }
        outputStream.close();

        //解析前端传来的JSON数据
        JSONObject loginData = null;
        //返回前端的结果
        /*      status属性
                0   登陆成功
                1   验证码错误
                2   账号错误
                3   密码错误
                4   动态码错误
        * */
        JSONObject result = new JSONObject();


        try {
            //将字符串转化为JSON类型
            loginData = new JSONObject(outputStream.toString());

            //获取验证码
            HttpSession session = request.getSession();
            String randStr = (String) session.getAttribute("randStr");

            //获取用户输入的验证码
            String validateCode = loginData.getString("validateCode");

            //判断验证码是否相同
            if (!validateCode.toUpperCase().equals(randStr)) {
                result.put("status", 1);
            } else {
                //账号密码登录
                if (loginData.getString("type").equals("account")) {

                    Map<String, User> users = new UserDao().queryAllUser();
                    String account = loginData.getString("account");
                    String password = loginData.getString("password");

                    if (users.containsKey(account)) {
                        if (users.get(account).getPassword().equals(password)) {
                            result.put("status", 0);
                        } else {
                            result.put("status", 3);
                        }
                    } else {
                        result.put("status", 2);
                    }
                } else {

                    String dynamicCode = loginData.getString("dynamicCode");

                    if(dynamicCode.equals((String)session.getAttribute("dynamicCode"))){
                        result.put("status", 0);
                    }else{
                        result.put("status", 4);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(result.toString());
    }
}
