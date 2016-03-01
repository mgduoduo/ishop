<#assign basePath=rc.contextPath>
<#assign cc=JspTaglibs["/WEB-INF/tld/cc_0_1.tld"]>
<link href="${basePath}/resources/css/redmond/jquery-ui-1.7.1.custom.css" rel="stylesheet">
<script type="text/javascript" src="${basePath}/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/datepicker.js"></script>
<script type="text/javascript">
    var jQuery_datepicker = jQuery.noConflict(true);

    function getFormattedDate(date) {
        var year = date.getFullYear();
        var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;
        var day = date.getDate().toString();

        return  year+"-"+ month +"-"+ day;
    }

    jQuery_datepicker(function () {
        if(jQuery("#shopingDateHide").val()!='' && jQuery("#shopingDateHide").val()!=null){
            var date = new Date(jQuery("#shopingDateHide").val());//Jan 28, 2015 12:00:00 AM
            jQuery("#shopingDate").val(getFormattedDate(date));
        }

        jQuery_datepicker("#shopingDate").datepicker({    //  记得要括在大括号之内
            appendText:"(yyyy-mm-dd)",
            dateFormat:"yy-mm-dd",  //设置日期格式
            changeMonth:true,   //是否提供月份选择
            changeYear:true,    //是否提供年份选择
            navigationAsDateFormat:true,
            numberOfMonths: 1,// 可显示几个月份的面板,
            dayNamesMin: ['日','一','二','三','四','五','六'],  //日期简写名称
            monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']  //月份简写名称
        });


    });

//    jQuery(function(){
//        jQuery("#updateOrderInfoForm input").each(function(){
//            jQuery(this).bind('blur', function(){
//                jQuery(this).val(jQuery(this).val().trim());
//            });
//        });
//    });

    function validatorFields(){

        var validIndicator = true;
        if(jQuery("#updateOrderInfoForm select[name='payType']").val()==''
            || jQuery("#updateOrderInfoForm input[name='shopingDate']").val()=='' || jQuery("#updateOrderInfoForm input[name='shopingDate']").val().trim()==''
            || jQuery("#updateOrderInfoForm input[name='totalPrice']").val()=='' || jQuery("#updateOrderInfoForm input[name='totalPrice']").val().trim()==''
            || jQuery("#updateOrderInfoForm input[name='customerName']").val()=='' || jQuery("#updateOrderInfoForm input[name='customerName']").val().trim()==''
            || jQuery("#updateOrderInfoForm input[name='customerPhoneNO']").val()=='' || jQuery("#updateOrderInfoForm input[name='customerPhoneNO']").val().trim()==''
            || jQuery("#updateOrderInfoForm input[name='receiveAdd']").val()=='' || jQuery("#updateOrderInfoForm input[name='receiveAdd']").val().trim()==''
            || jQuery("#updateOrderInfoForm select[name='expressCompany']").val()=='' ){
            validIndicator = false;
        }

        if(validIndicator) {
            // dropdown
            var prodNOArr = jQuery("#updateOrderInfoForm select[name='refProdNO']");
            if (prodNOArr.length > 0) {
                for (var i = 0; i < prodNOArr.length; i++) {
                    if (prodNOArr[i].value == '' || prodNOArr[i].value == 'undefined') {
                        validIndicator = false;
                        break;
                    }
                }
            }
        }

        if(validIndicator){
            //input
            var purchaseCountArr = jQuery("#updateOrderInfoForm input[name='purchaseCount']");
            if(purchaseCountArr.length > 0){
                for(var i=0; i< purchaseCountArr.length; i++){
                    if(purchaseCountArr[i].value == '' || purchaseCountArr[i].value == 'undefined'){
                        validIndicator = false;
                        break;
                    }
                }
            }
        }
        return validIndicator;
    }

    function submitEdit(){

        //jQuery.support.cors = true;// it's importance  for resolving the ajax 405 error.

        jQuery("#warningSpan").html("");
        if(!validatorFields()){
            var warningText = "<br/><font color='red'>所有红色标注的项目都必须不能为空, 请检查后重新提交!</font>";
            jQuery("#warningSpan").html(warningText);
            return false;
        }

        jQuery.ajax({
            type:"POST",
            url:"${basePath}/iShop/updateOrderInfo.do?n="+Math.round(Math.random()*100),
            data:jQuery('#updateOrderInfoForm').serialize(),
            dataType: "text",   //返回值类型       使用json的话也可以，但是需要在JS中编写迭代的html代码，如果格式样式
            error:function(xhr, ajaxOptions, thrownError){
                alert(xhr.status);
            },
            success:function(data){
                //jQuery("#orderInfoForUpdate").html("");
                //jQuery("#orderInfoForUpdate").hide();
                changeMenu('menu_1');// refresh the order listing.
            }
        });
    }

    function delInx(obj){
        if(confirm("是否确认要删除该条记录?")){
            var parentTr = jQuery(obj).parents("tr:eq(0)");

            jQuery(parentTr).find("input[name='deleteIndicator']").val("Y");

            parentTr.remove();
        }
    }
    function addInx(obj){

        var $parentTable = jQuery(obj).parents("table:eq(0)");
        var $lastBodyTr = jQuery($parentTable).find("tr:last").prev("tr");
        var parentCss = $lastBodyTr.attr("class");
        var rowClass = parentCss=='row1' ? 'row2' : 'row1';

        var prodDropdownList = jQuery("#productList").find("option");
        var options = "<option value=''>---请选择---</option>";
        if(prodDropdownList!='' && prodDropdownList.length > 0){
            for(var i=0; i<prodDropdownList.length; i++){
                options = options + "<option value='"+prodDropdownList[i].value+"'>"+prodDropdownList[i].text+"</option>";
            }
        }

        var newRow = "<tr class='"+rowClass+"'>"
        +"<td class='vector1'>产品型号</td>"
        +"<td><select name='refProdNO' onchange='selectProd(this)' class='dropdown_s'>"
                + options
        + "</select><font color='red'> *</font></td>"
        +"<td class='vector1'>购买数量</td><td><input type='text' name='purchaseCount'/><font color='red'> *</font></td>"
        +"<td class='vector1'>库存量</td><td><span name='backupCount'></span></td>"
        +"<td><input type='hidden' name='deleteIndicator' value='N'/>"
        +"<a href='javascript:;' onclick='delInx(this)'><img src='${basePath}/resources/images/delete.gif'/></a>"
        +"&nbsp;<a href='javascript:;' onclick='addInx(this)'><img src='${basePath}/resources/images/ico_add.gif'/></a>"
        +"</td></tr>";

//        jQuery($parentTable).find("tr:last").before(newRow);
        $lastBodyTr.after(newRow);
    }

</script>
<div id="top_content">
    <div id="content">
        <p id="whereami">
        </p>

<#--        <h1>
            订单详情&nbsp;&nbsp;--&nbsp;&nbsp;${orderInfo.expressNO}
        </h1>-->

        <form method="POST" id="updateOrderInfoForm" action="${basePath}/iShop/updateOrderInfo.do">
            <table class="table">
                <tr class="table_header">
                    <td colspan="7">订单详情 &nbsp;&nbsp;--&nbsp;&nbsp; ${orderInfo.expressNO}</td>
                </tr>
                <tr class="row1">
                    <td class="vector1">订单编号</td>
                    <td><input type="hidden" name="tradeID" value="${orderInfo.tradeID}"/>
                        <input type="hidden" name="expressNO" value="${orderInfo.expressNO}"/>
                    <label style="font-size: 24px medium">${orderInfo.expressNO}</label></td>
                    <td class="vector1" style="width: 90px;">付款方式</td>
                    <td>
                        <select name="payType" class="dropdown_s">
                            <option value="">---请选择---</option>
                            <#if payTypeCodeList?exists>
                                <#list payTypeCodeList as code>
                                    <option value="${code.codeID}" <#if orderInfo.payType == code.codeID>selected="selected"</#if>>${code.codeDesc}</option>
                                </#list>
                            </#if>
                        </select><font color="red">&nbsp;*</font>
                    </td>
                    <td class="vector1">购买时间</td>
                    <td><input type="text" readonly="readonly" style="width: 100px;" id="shopingDate" name="shopingDate" value="" />
                        <input type="hidden" id="shopingDateHide" value="${orderInfo.shopingDate}" />
                        <font color="red"> *</font></td>
                    <td></td>
                </tr>
                <tr class="row2">
                    <td class="vector1">总价</td>
                    <td>
                        <input type="text" name="totalPrice" value="<@cc.moneyFormat value="${orderInfo.totalPrice}"/>"/><font color="red"> *</font>&nbsp;元
                    </td>
                    <td class="vector1">买家QQ号</td>
                    <td>
                        <input type="text" name="customerQQ" value="<#if orderInfo.customerQQ??>${orderInfo.customerQQ}</#if>"/>
                    </td>
                    <td class="vector1">买家旺旺号</td>
                    <td>
                        <input type="text" name="customerWW" value="<#if orderInfo.customerWW??>${orderInfo.customerWW}</#if>"/>
                    </td>
                    <td></td>
                </tr>
                <tr class="row1">
                    <td class="vector1">收件人姓名</td>
                    <td><input type="text" name="customerName" value="${orderInfo.customerName}"/><font color="red">&nbsp;*</font></td>
                    <td class="vector1">收件人联系方式</td>
                    <td><input type="text" name="customerPhoneNO" value="${orderInfo.customerPhoneNO}"/><font color="red"> *</font></td>
                    <td class="vector1">收件人地址</td>
                    <td><input type="text" name="receiveAdd" value="${orderInfo.receiveAdd}"/><font color="red"> *</font></td>
                    <td></td>
                </tr>
                <tr class="row2">
                    <td class="vector1">快递公司</td>
                    <td>
                        <select name="expressCompany" class="dropdown_s">
                            <option value="">---请选择---</option>
                            <#if expressCompanyCodeList?exists>
                                <#list expressCompanyCodeList as code>
                                    <option value="${code.codeID}" <#if orderInfo.expressCompany == code.codeID>selected="selected"</#if>>${code.codeDesc}</option>
                                </#list>
                            </#if>
                        </select><font color="red">&nbsp;*</font>
                    <td class="vector1">物流状况</td>
                    <td>

                        <select name="expressStatus" class="dropdown_s">
                            <option value="">---请选择---</option>
                            <#if expressStatusCodeList?exists>
                                <#list expressStatusCodeList as code>
                                    <option value="${code.codeID}" <#if orderInfo.expressStatus == code.codeID>selected="selected"</#if>>${code.codeDesc}</option>
                                </#list>
                            </#if>
                        </select>

                    </td>
                    <td class="vector1">备注</td>
                    <td><input type="text" name="remark" value="<#if orderInfo.remark??>${orderInfo.remark}</#if>"/>
                    </td>
                    <td></td>
                </tr>
            <#if orderInfo.txnList?size gt 0 >
                <tr class="table_header">
                    <td colspan="6">订货清单</td><td>操作</td>
                </tr>
                <#list orderInfo.txnList as txn>
                    <#if txn.product?exists && txn.deleteIndicator == 'N'>
                    <tr class="row<#if txn_index % 2 = 1>1<#else>2</#if>">
                        <td class="vector1">产品型号</td>
                        <td>
                            <input type="hidden" name="refProdNO" value="${txn.refProdNO}"/>${txn.refProdNO}
                            <select id="productList" class="dropdown_s" style="display: none">
                                <#if productCodeList?exists>
                                    <#list productCodeList as prod>
                                        <option value="${prod.prodNo}">${prod.prodName}</option>
                                    </#list>
                                </#if>
                            </select>
                        </td>
<#--                        <td class="vector1">产品名称</td>
                        <td>
                            <#if txn.product.prodName??>${txn.product.prodName}</#if>
                        </td>
                        <td class="vector1">产品价格</td>
                        <td>
                             <#if txn.product.prodPrice??><@cc.moneyFormat value="${txn.product.prodPrice}"/></#if>
                        </td>
                    </tr>
                    <tr class="row2">-->
                        <td class="vector1">购买数量</td>
                        <td>
                            <input type="text" name="purchaseCount" value="<#if txn.purchaseCount??>${txn.purchaseCount}</#if>"/><font color="red"> *</font></td>
                        </td>
                        <td class="vector1">库存量</td>
                        <td>
                            <#if txn.product.backupCount??>${txn.product.backupCount}</#if>
                        </td>
<#--                        <td class="vector1">发货备注</td>
                        <td>
                            <#if orderInfo.remark??>${orderInfo.remark}</#if>
                        </td>-->
                        <td>
                            <#--<input type="checkbox" id="del_inx" onclick="delInx(this)"/>-->
                            <input type="hidden" name="deleteIndicator" value="${txn.deleteIndicator}"/>
                            <a href="javascript:;" onclick="delInx(this)"><img src="${basePath}/resources/images/delete.gif"/></a>&nbsp;<a href="javascript:;" onclick="addInx(this)"><img src="${basePath}/resources/images/ico_add.gif"/></a>
                        </td>
                    </tr>
                        </#if>
                </#list>
            </#if>
                <tr class="row1">
                    <td colspan="7" align="center">
                        <input type="button" id="submitBtn" value="修改" class="btnUnActive_01"
                               onclick="submitEdit()"/>
                        <br/><span id="warningSpan"></span>
                    </td>
                </tr>
            </table>
        </form>

    </div>
</div>