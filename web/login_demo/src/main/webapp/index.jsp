<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <style>
            /* 容器大小 */
            .container {
                height: 500px;
                width: 800px;
                overflow: hidden;
            }

            /* .wrap */
            .wrap {
                position: relative;
                left: 0px;
                width: 2400px;
                animation: animateImg ease 5s infinite normal;
            }

            /* 图片大小 */
            .wrap img {
                width: 800px;
                float: left;
                height: 500px;
                display: block;
            }

            /* 动画 */
            @keyframes animateImg {
                0% {
                    left: 0px;
                }
                20% {
                    left: -0px;
                }
                40% {
                    left: -800px;
                }
                60% {
                    left: -800px;
                }
                80% {
                    left: -1600px;
                }
                100% {
                    left: -1600px;
                }
            }

            .top {
                width: 1260px;
                height: 90px;
                background-color: dodgerblue;
            }

            .login {
                position: absolute;
                top: 98px;
                left: 810px;
                width: 458px;
                height: 500px;
                background-color: pink;
            }

            .shiftButton {
                position: absolute;
                top: 98px;
                left: 810px;
                height: 40px;
                background-color: yellow;
                font-size: 15px;
                border: none;
            }

            .inputBox {
                height: 25px;
                width: 230px;
            }

            .validateImg {
                position: absolute;
                top: 148px;
                left: 240px;
                border: 0;
            }

            .loginButton {
                position: absolute;
                top: 250px;
                left: 175px;
                width: 70px;
                height: 30px;
                font-size: 18px;
            }
        </style>
        <title>统一身份认证</title>
    </head>
    <body>
        <!-- 头部 -->
        <div class="top">
            <div style="height: 13px"></div>
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <img src="img/logo.png"/>
        </div>

        <div class="container">

            <!-- 轮播图 -->
            <div class="wrap">
                <img src="img/1.jpg" alt=""/>
                <img src="img/2.jpg" alt=""/>
                <img src="img/3.jpg" alt=""/>
            </div>

            <!-- 登录框 -->
            <div class="login" id="accountLoginDiv" style="display: block">
                <h1 align="center">账号登录</h1><br><br>
                <div style="position: absolute;left: 55px">
                    <form method="post" name="loginForm1">
                        请输入账号: &nbsp&nbsp&nbsp<input type="text" id="account" class="inputBox"/><br><br><br>
                        请输入密码: &nbsp&nbsp&nbsp<input type="password" id="password" class="inputBox"/><br><br><br>
                        请输入验证码: &nbsp<input type="text" id="validateCode1" class="inputBox" maxlength="4"
                                            style="width: 100px"/>
                        <img id="validateImg1" class="validateImg" src="/Validate"><br><br>
                        <span id="infoDiv1" align="center"
                              style="color: red;font-size: large;text-align: center;display:none; "></span>
                        <button id="submitButton1" type="button" class="loginButton">登录</button>
                    </form>
                </div>
            </div>
            <div class="login" id="mailboxLoginDiv" style="display: none">
                <h1 align="center">动态码登录</h1><br><br>
                <div style="position: absolute;left: 55px">
                    <form method="post" id="loginForm2">
                        请输入邮箱: &nbsp&nbsp&nbsp<input type="text" name="mail" class="inputBox"/><br><br><br>
                        请输入动态码: &nbsp<input type="text" name="dynamicCode" class="inputBox" maxlength="6" style="width:
                        100px"/>
                        &nbsp&nbsp
                        <button id="dynamicButton" type="button" style="font-size: 15px;">获取动态码</button>
                        <br><br><br>
                        请输入验证码: &nbsp<input type="text" id="validateCode2" class="inputBox" maxlength="4"
                                            style="width: 100px"/>
                        <img id="validateImg2" class="validateImg" src="/Validate"><br><br>
                        <span id="infoDiv2" align="center"
                              style="color: red;font-size: large;text-align: center;display:none; "></span>
                        <button id="submitButton2" type="button" class="loginButton">登录</button>
                    </form>
                </div>
            </div>
            <button id="shiftLoginButton" class="shiftButton">切换登录方式</button>
        </div>
    </body>

    <script>
        let div1 = document.getElementById("accountLoginDiv");
        let div2 = document.getElementById("mailboxLoginDiv");
        let img1 = document.getElementById("validateImg1");
        let img2 = document.getElementById("validateImg2");
        let submitButton1 = document.getElementById("submitButton1");
        let submitButton2 = document.getElementById("submitButton2");
        let dynamicButton = document.getElementById("dynamicButton");
        let infoDiv1 = document.getElementById("infoDiv1");
        let infoDiv2 = document.getElementById("infoDiv2");
        let status = ['block', 'none'];
        let statusCode = ['验证码错误', '账号错误', '密码错误', '动态码错误'];
        let flag = 0;

        window.onload = function () {

            //切换按钮
            document.getElementById("shiftLoginButton").onclick = function () {

                flag++;
                flag %= 2;
                div1.style.display = status[flag];
                div2.style.display = status[(flag + 1) % 2];
                if(1 == flag)
                    img2.setAttribute("src", "/Validate" + "?time=" + Math.random());
                else
                    img1.setAttribute("src", "/Validate" + "?time=" + Math.random());
            }

            //切换验证码
            img1.onclick = function () {
                img1.setAttribute("src", "/Validate" + "?time=" + Math.random());
            }
            img2.onclick = function () {
                img2.setAttribute("src", "/Validate" + "?time=" + Math.random());
            }

            //登录按钮1
            submitButton1.onclick = function () {

                let loginData = {};
                loginData['account'] = loginForm1.account.value;
                loginData['password'] = loginForm1.password.value;
                loginData['validateCode'] = document.getElementById("validateCode1").value;
                loginData['type'] = "account";

                let xmlHttp = new XMLHttpRequest();
                xmlHttp.open("POST", "/LoginServlet", true);
                xmlHttp.onreadystatechange = function () {
                    if (xmlHttp.readyState == 4) {

                        let data = JSON.parse(xmlHttp.responseText);
                        if (data["status"] != 0) {
                            infoDiv1.innerText = statusCode[data["status"] - 1];
                            infoDiv1.style.display = 'block';
                        } else {
                            window.location.assign("welcome.jsp");
                        }
                    }
                }
                xmlHttp.send(JSON.stringify(loginData));
            }

            //获取动态验证码
            dynamicButton.onclick = function () {

                let data = {};
                data["mail"] = loginForm2.mail.value;
                let xmlHttp = new XMLHttpRequest();
                xmlHttp.open("POST", "/DynamicCode", true);
                xmlHttp.send(JSON.stringify(data));
            }

            submitButton2.onclick = function () {

                let loginData = {};
                loginData['dynamicCode'] = loginForm2.dynamicCode.value;
                loginData['validateCode'] = document.getElementById("validateCode2").value;
                loginData['type'] = "dynamic";

                let xmlHttp = new XMLHttpRequest();
                xmlHttp.open("POST", "/LoginServlet", true);
                xmlHttp.onreadystatechange = function () {

                    if (xmlHttp.readyState == 4) {
                        let data = JSON.parse(xmlHttp.responseText);

                        if (data["status"] != 0) {
                            infoDiv2.innerText = statusCode[data["status"] - 1];
                            infoDiv2.style.display = 'block';
                        } else {
                            window.location.assign("welcome.jsp");
                        }
                    }
                }
                xmlHttp.send(JSON.stringify(loginData));
            }
        }
    </script>

</html>