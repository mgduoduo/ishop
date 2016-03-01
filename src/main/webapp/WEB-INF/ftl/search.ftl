<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<#assign basePath=rc.contextPath>
<#import "/spring.ftl" as spring />
<#assign cc=JspTaglibs["/WEB-INF/tld/cc_0_1.tld"]>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <title>查询</title>
    <link href="${basePath}/resources/css/site.css" rel="stylesheet">
    <link href="${basePath}/resources/css/style.css" rel="stylesheet">
    <link href="${basePath}/resources/css/redmond/jquery-ui-1.7.1.custom.css" rel="stylesheet">
    <script type="text/javascript" src="${basePath}/resources/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/datepicker.js"></script>
    <script type="text/javascript">

        // rename the jquery, to resolve the problem causing by the different version of JQuery
        var jQuery_datepicker = jQuery.noConflict(true);

        jQuery_datepicker(function () {
            jQuery_datepicker("#purchaseDateFrom").datepicker({    //  记得要括在大括号之内
                appendText:"(yyyy-mm-dd)",
                dateFormat:"yy-mm-dd",  //设置日期格式
                changeMonth:true,   //是否提供月份选择
                changeYear:true,    //是否提供年份选择
                navigationAsDateFormat:true,
                numberOfMonths: 1,// 可显示几个月份的面板,
                dayNamesMin: ['日','一','二','三','四','五','六'],  //日期简写名称
                monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],  //月份简写名称
                onClose: function( selectedDate ) {
                    jQuery_datepicker( "#purchaseDateTo" ).datepicker( "option", "minDate", selectedDate );
                }
            });

            jQuery_datepicker("#purchaseDateTo").datepicker({    //  记得要括在大括号之内
                appendText:"(yyyy-mm-dd)",
                dateFormat:"yy-mm-dd",  //设置日期格式
                changeMonth:true,   //是否提供月份选择
                changeYear:true,    //是否提供年份选择
                navigationAsDateFormat:true,
                numberOfMonths: 1,// 可显示几个月份的面板,
                dayNamesMin: ['日','一','二','三','四','五','六'],  //日期简写名称
                monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],  //月份简写名称
                onClose: function( selectedDate ) {
                    jQuery_datepicker( "#purchaseDateFrom" ).datepicker( "option", "maxDate", selectedDate );
                }
            });

        });

        function submitSearch(){
            jQuery('#searchResultDiv').html("");

            jQuery.ajax({
                type:"POST",
                url:"${basePath}/iShop/search.do?n="+Math.round(Math.random()*100),
                data:jQuery('#searchForm').serialize(),
                dataType: "html",   //返回值类型       使用json的话也可以，但是需要在JS中编写迭代的html代码，如果格式样式
                cache:false,
                async: false,
                success:function(data){
                    //var json = eval('('+msg+')');//拼接的json串
                    jQuery('#searchResultDiv').html(data);
                    jQuery("#submitBtn").removeClass("btnUnActive_01");
                    jQuery("#submitBtn").addClass("btnActive_01");
                },
                error:function(error){alert("error");}
            });

        }
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
                查询条件
            </h1>

            <form method="post" id="searchForm" action="${basePath}/iShop/search.do">
                <table class="table">
                    <tr class="row1">
                        <td class="vector1" style="50px;">订单编号</td>
                        <td style="180px;"><input type="text" name="expressNO"/></td>
                        <td class="vector1" style="50px;">客户姓名</td>
                        <td style="180px;"><input type="text" name="customerName"/></td>
                        <td class="vector1" style="90px;">客户QQ/旺旺/手机</td>
                        <td>
                            <select name="customerContactType" class="dropdown_s">
                                <option value="">---请选择---</option>
                                <#if contactTypeCodeList?exists>
                                    <#list contactTypeCodeList as code>
                                        <option value="${code.codeID}">${code.codeDesc}</option>
                                    </#list>
                                </#if>
                            </select>
                            <input type="text" name="customerContactNO"/>
                        </td>

                    </tr>
                    <tr class="row2">
                        <td class="vector1">产品型号</td>
                        <td>
                            <select name="productNO" class="dropdown_s">
                                <option value="">---请选择---</option>
                                <#if productCodeList?exists>
                                    <#list productCodeList as prod>
                                        <option value="${prod.prodNo}">${prod.prodName}</option>
                                    </#list>
                                </#if>
                            </select>
                        </td>
                        <td class="vector1">产品名称</td>
                        <td><input type="text" name="productName"/></td>
                        <td class="vector1">付款方式</td>
                        <td>

                            <select name="payType" class="dropdown_s">
                                <option value="">---请选择---</option>
                                <option value="">---请选择---</option>
                                <#if payTypeCodeList?exists>
                                    <#list payTypeCodeList as code>
                                        <option value="${code.codeID}">${code.codeDesc}</option>
                                    </#list>
                                </#if>
                            </select>
                        </td>
                    </tr>
                    <tr class="row1">
                        <td class="vector1">购买时间&nbsp;从</td>
                        <td><input type="text" readonly="readonly" style="width: 100px;" id="purchaseDateFrom" name="shopingDateFromStr"/></td>
                        <td class="vector1"> &nbsp;至&nbsp; </td>
                        <td><input type="text" readonly="readonly" style="width: 100px;"  id="purchaseDateTo" name="shopingDateToStr"/></td>
                        <td class="vector1">物流状况</td>
                        <td>
                            <select name="expressStatus" class="dropdown_s">
                                <option value="">---请选择---</option>
                                <#if expressStatusCodeList?exists>
                                    <#list expressStatusCodeList as code>
                                        <option value="${code.codeID}">${code.codeDesc}</option>
                                    </#list>
                                </#if>
                            </select>
                        </td>

                    </tr>
                    <tr class="row2">
                        <td colspan="6" align="center">
                            <input type="button" id="submitBtn" value="提交查询" class="btnUnActive_01" onclick="submitSearch()"/>
                        </td>
                    </tr>
                </table>
            </form>

            <hr/>
            <div id="searchResultDiv">
            </div>
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