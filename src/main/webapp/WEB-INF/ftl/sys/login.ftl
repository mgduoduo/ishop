<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign basePath=rc.contextPath>
<#import "/spring.ftl" as spring />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
    <link href="${basePath}/resources/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="${basePath}/resources/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/common.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/MD5.js"></script>
    <script type="text/javascript">

        function logonCheck(){
            var username = jQuery("#username").val().trim();
            var password = jQuery("#pwd").val();
            if(username !='' && username != null
                    && password !='' && password != null){
                jQuery("#username").val(username);
                password = hex_md5(hex_md5(password));// js MD5 2次加密
                jQuery("#password").val(password);

                jQuery("#loginForm").submit();
            }else {
                validInput(jQuery("#username"));
                validInput(jQuery("#password"));

                if (username == '' || username == null) {
                    //jQuery("#username_msg").html("账号不能为空");
                }
                if (password == '' || password == null) {
                    //jQuery("#password_msg").html("密码不能为空");
                }

            }
        }

        function validInput(obj){
           if(jQuery(obj).val() == '' || jQuery(obj).val() == null){
               jQuery(obj).next("span").html("");
               jQuery(obj).next("span").html("不能为空");
           }else{
               jQuery(obj).next("span").html("");
           }
        }

    </script>
</head>
<body>

<form id="loginForm" action="${basePath}/login/logon.do" method="post">
    <div class="loginPageDiv">
        <table width="350" cellpadding="0" cellspacing="0" border="0" align="center">
            <tbody>
            <tr>
                <td colspan="2" height="280">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td class="loginPageLbl" align="center">账号</td>
                <td class="inputFieldClass" align="left">
                    <input type="text" id="username" name="username" onchange="validInput(this)"/><span id="username_msg" style="font: italic"></span>
                </td>
            </tr>
            <tr>
                <td class="loginPageLbl" align="center">密码</td>
                <td class="inputFieldClass" align="left">
                    <input type="password" id="pwd" onchange="validInput(this)"/><span id="password_msg"></span>
                    <input type="hidden" id="password" name="password"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center">
                    <a href="javascript:;" onclick="return logonCheck();"
                       onmouseout="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Image1','','${basePath}/resources/images/butn-login-U.png',1)"><img
                            src="${basePath}/resources/images/butn-login-S.png" name="Image1"
                            width="77" height="31" border="0" id="Image1"
                            />
                        </a>
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</form>

</body>
</html>