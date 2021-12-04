package csu.edu.wxh.demo.servlets;

import org.json.JSONObject;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author FlowerWang
 */
@WebServlet(name = "DynamicCode", value = "/DynamicCode")
public class DynamicCode extends HttpServlet {

    private final String myEmailAccount = "980563334@qq.com";//发送的邮箱
    private final String myEmailPassword = "vleixdzippyobech";
    private String myEmailSMTPServer = "smtp.qq.com";
    private String receiveMailAccount;
    private String info;


    public void send() throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPServer);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = createMessage(session, myEmailAccount, receiveMailAccount, info);

        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public MimeMessage createMessage(Session session, String sendMail, String receiveMail, String info) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "邮箱验证码测试", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "xx用户", "UTF-8"));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("中南大学主页登录验证码", "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent("您好，您的验证码为: " + info, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        InputStream is = request.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while(is.read(buffer) > 0){
            out.write(buffer);
        }

        try {
            JSONObject data = new JSONObject(out.toString());
            receiveMailAccount = data.getString("mail");

            //产生随机六位字符
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int tmp = random.nextInt(35);
                if (tmp > 25) {
                    sb.append(tmp - 25);
                } else {
                    sb.append((char) (tmp + 'A'));
                }
            }
            info = sb.toString();

            HttpSession session = request.getSession();
            session.setAttribute("dynamicCode",sb.toString());

            send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
