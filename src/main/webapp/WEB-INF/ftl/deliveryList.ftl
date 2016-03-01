<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign basePath=rc.contextPath>
<#import "/spring.ftl" as spring />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>到付信息</title>
    <link href="${basePath}/resources/css/site.css" rel="stylesheet">
    <link href="${basePath}/resources/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="${basePath}/resources/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">

//        jQuery(document).ready(function () {
//            jQuery("a.bnrFrmTabLbl").click(function () {
//                jQuery(".bnrFrmTabMidActive").removeClass("bnrFrmTabMidActive").addClass("bnrFrmTabMid");
//                jQuery(this).parent().removeClass("bnrFrmTabMid").addClass("bnrFrmTabMidActive");
//            });
//        });
    </script>
</head>
<body>

<div id="wrap">
    <#include "./header.ftl">
    <div id="top_content">
        <div id="content">
            <p id="whereami">
            </p>

            <h1>
                库存详情
            </h1>
            <table class="table">
                <tr class="table_header">
                    <td>操作</td>
                    <td>型号</td>
                    <td>商品名称</td>
                    <td>剩余数量</td>
                    <td>供应商</td>
                    <td>进货价</td>
                    <td>描述</td>
                    <td>备注</td>
                </tr>

                <#if prodList?exists >
                    <#list prodList as prod>
                        <tr class="row1">
                            <td><input type="checkbox"/></td>
                            <td>${prod.prodNo}</td>
                            <td>${prod.prodName}</td>
                            <td>${prod.backupCount}</td>
                            <td><#if prod.supplier??>${prod.supplier}</#if></td>
                            <td><#if prod.prodPrice??>${prod.prodPrice}</#if></td>
                            <td><#if prod.prodDesc??>${prod.prodDesc}</#if></td>
                            <td><#if prod.remark??>${prod.remark}</#if></td>
                        </tr>
                    </#list>
                </#if>
            </table>
            <h1>
                view photo:
            </h1>

            <table>

                <tr>
                    <td>

                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="footer">
        <div id="footer_bg">
            去<<我的店铺>>看看
        </div>
    </div>
</div>

</body>
</html>