<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign basePath=rc.contextPath>
<#import "/spring.ftl" as spring />
<#assign cc=JspTaglibs["/WEB-INF/tld/cc_0_1.tld"]>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <title>订单管理</title>
    <link href="${basePath}/resources/css/site.css" rel="stylesheet">
    <link href="${basePath}/resources/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="${basePath}/resources/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">


    </script>
</head>
<body>

<div id="wrap">
    <#include "./header.ftl">

    <#include "./orderList.ftl">

    <div id="footer">
        <div id="footer_bg">
            去<<我的店铺>>看看
        </div>
    </div>
</div>

</body>
</html>