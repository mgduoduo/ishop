<#assign contextPath=rc.contextPath>
<script type="text/javascript" src="${contextPath}/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

    function changeMenu(menuID) {
        if (menuID == 'menu_1') {
            //orderManage
            jQuery("#menuForm").attr("action","${contextPath}/iShop/orderManage.do");
        } else if (menuID == 'menu_2') {
            //search
            jQuery("#menuForm").attr("action","${contextPath}/iShop/preSearch.do");
        } else if (menuID == 'menu_3') {
            //deliveryList
            jQuery("#menuForm").attr("action","${contextPath}/iShop/deliveryList.do");
        } else if (menuID == 'menu_4') {
            //backupProductManage
            jQuery("#menuForm").attr("action","${contextPath}/iShop/backupProductManage.do");
        } else if (menuID == 'menu_5') {
            //sysManage
            jQuery("#menuForm").attr("action","${contextPath}/iShop/sysManage.do");
        } else {
            jQuery("#menuForm").attr("action","${contextPath}/login/logout.do");
        }

        jQuery("#menuForm").submit();
    }

    jQuery(document).ready(function () {
        var menuID = jQuery("#menuID").val();
        var obj = jQuery("#" + menuID);// current selected menu.
        jQuery(".bnrFrmTabMidActive").removeClass("bnrFrmTabMidActive").addClass("bnrFrmTabMid");
        jQuery(obj).parent().removeClass("bnrFrmTabMid").addClass("bnrFrmTabMidActive");
    });

    /*function SetWinHeight(obj) {
        var win = obj;
        if (document.getElementById) {
            if (win && !window.opera) {
                if (win.contentDocument && win.contentDocument.body.offsetHeight)
                    win.height = win.contentDocument.body.offsetHeight;
                else if (win.Document && win.Document.body.scrollHeight)
                    win.height = win.Document.body.scrollHeight;
            }
        }
    }*/
</script>

<div style="display: none;">
    <form id="menuForm" action="${contextPath}/iShop/orderManage.do" method="post">
        <input type="hidden" id="menuID" name="menuID" value="${menuID}"/>
    </form>
</div>

<div id="top_content">
    <div id="header">
        <div id="rightheader">
            <p>
                亲，今天大笑了吗！<a href="${contextPath}/login/logout.do">退出系统</a>
            </p>
        </div>
        <div id="topheader">
            <h1 id="title">
                <a href="${contextPath}/iShop/orderManage.do">xxxxxx 系统</a>
            </h1>
        </div>
        <div>
            <table border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                    <td width="140">
                        <table border="0" width="140" height="29" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td class="bnrFrmTabMidActive">
                                    <a href="javascript:;" id="menu_1" onclick="changeMenu('menu_1')"
                                       class="bnrFrmTabLbl">订单管理</a></td>
                                <td width="1"><img
                                        src="${contextPath}/resources/images/bg-navMenu-right-line.gif"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="140">
                        <table border="0" width="140" height="29" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td class="bnrFrmTabMid">
                                    <a href="javascript:;" id="menu_2" onclick="changeMenu('menu_2')"
                                       class="bnrFrmTabLbl">查&nbsp;&nbsp;&nbsp;&nbsp;询</a></td>
                                <td width="1"><img
                                        src="${contextPath}/resources/images/bg-navMenu-right-line.gif"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="140">
                        <table border="0" width="140" height="29" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td class="bnrFrmTabMid">
                                    <a href="javascript:;" id="menu_3" onclick="changeMenu('menu_3')"
                                       class="bnrFrmTabLbl">到付用户</a></td>
                                <td width="1"><img
                                        src="${contextPath}/resources/images/bg-navMenu-right-line.gif"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="140">
                        <table border="0" width="140" height="29" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td class="bnrFrmTabMid">
                                    <a href="javascript:;" id="menu_4" onclick="changeMenu('menu_4')"
                                       class="bnrFrmTabLbl">存货管理</a></td>
                                <td width="1"><img
                                        src="${contextPath}/resources/images/bg-navMenu-right-line.gif"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="90">
                        <table border="0" width="140" height="29" cellpadding="0" cellspacing="0">
                            <tbody>
                            <tr>
                                <td class="bnrFrmTabMid">
                                    <a href="javascript:;" id="menu_5" onclick="changeMenu('menu_5')"
                                       class="bnrFrmTabLbl">系统管理</a></td>
                                <td width="1"><img src="${contextPath}/resources/images/bg-navMenu-right-line.gif"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
            <div id="navigation">
            </div>
        </div>
    </div>
</div>

