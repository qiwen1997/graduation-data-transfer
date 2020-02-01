<%--
  Created by IntelliJ IDEA.
  User: mi
  Date: 2020/2/1
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
    <style type="text/css">
        *{margin: 0;
            padding: 0;
        }/*去掉页面样式，通用选择器*/
        body{color:white}/*标签选择器*/
        .context{background: #50A3A2;
            position: absolute;
            left: 0;
            top:50%;
            width: 100%;
            height: 400px;
            margin-top: -200px;
            overflow: hidden;/*隐藏滚动条*/
        }/*定位浏览器边距位置 左 上 */
        .container{/*类选择器*/
            max-width: 600px;
            height: 400px;/*左右*/
            padding: 80px 0;/*上下*/
            text-align: center;
            margin: 0 auto;/*居中显示*/
        }
        .container h1{/*派生*/
            font-size: 40px;
            font-weight: 300;}
        form{padding: 20px 0;}
        form input{border: 1px solid #FFF;
            width: 220px;
            padding: 10px 15px;
            display: block;
            margin: 0 auto 10px auto;
            border-radius: 30px;/*圆角*/
            font-size: 18px;
            font-weight: 300;
            text-align: center;
        }
        form button{
            background: white;
            border: 0;
            padding: 10px 15px;
            color: green;
            border-radius: 8px;
            width: 250px;
            font-size: 16px;
        }
        form button:hover{
            background:#CCC; /*鼠标移动上去按钮颜色变灰色*/
        }
    </style>
</head>
<script src="jquery-3.4.1.min.js"></script>
<script>
  function login() {
    $.ajax({
      url: '/login',
      type: 'get',
      dataType: 'text',
      data: {"name": $('#name').val(), "password": $('#password').val()},
      success: function (d) {
        if (d === "success") {
          window.location.href = 'Show.html';
        } else {
          window.location.href = 'error.html';
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status); // 200
        alert(textStatus); // parsererror
        alert(errorThrown); // SyntaxError: Unexpected end of input
      }
    })
  }
</script>
<body>
<div class="context">
    <div class="container">
        <h1>Welcome</h1>
        <form>
            <table>
                             <tr>
                                 <td>用户名</td>
                                 <td><input type="text" id="name" ></td>
                             </tr>
                             <tr>
                                 <td>密 码</td>
                                 <td><input type="password" id="password" ></td>
                             </tr>
                             <tr>
                                 <td colspan="2">
                                     <input type="button" onclick="login()" value="登陆">
                                </td>
                             </tr>
                         </table>
        </form>
    </div>
</div>

</body>
</html>
