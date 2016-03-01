package com.zerol.crm.common.tag;

/**
 * Created by gaoqiang on 2/2/2015.
 */

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/***
 * 分页控件
 */
public class PageTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String curPage;
    private String totalPage;
    private String pageSize;
    private String totalCount;
    private String formId;

    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        int curPage = Integer.valueOf(this.curPage);
        int totalPage = Integer.valueOf(this.totalPage);
        int pageSize = Integer.valueOf(this.pageSize);
        int totalCount = Integer.valueOf(this.totalCount);

        int pageNumber = 0;
        if (totalPage%pageSize==0) {
            pageNumber = totalPage/pageSize;
        } else {
            pageNumber = (totalPage/pageSize)+1;
        }
        if (curPage < 1) {
            curPage = 1;
        }

        try {
            if (pageNumber > 0) {
                // it's better to use javascript defined in your page, and please also make sure if the js exists.

                /*out.print("<script type='text/javascript'>" +
                        "function go(pageNum){" +
                        "var f = document.getElementById('" + formId + "');"+
                        "f.action = f.action + '?pageNum=' + pageNum + '&pageSize="+pageSize+"';"+
                        "f.submit();"+
                        "}" +
                        "</script>");*/

                //print pagination footer.
                out.print("<div class='pagination'>");
                out.print("<div class='rightPagination'><ul>");
                out.print("<li style='width:100px;'>共&nbsp;"+totalPage+"&nbsp;页&nbsp;"+totalCount+"&nbsp;条</li>");
                out.print("<li style='width:100px;'>每页");
                out.print("<select name='pageSize' id='pageSize' onchange='go(1);'>");
                if(pageSize == 10){
                    out.print("<option value='10' selected='true'>10</option><option value='20'>20</option><option value='50'>50</option>");
                }else if(pageSize == 20){
                    out.print("<option value='10'>10</option><option value='20' selected='true'>20</option><option value='50'>50</option>");
                }else if(pageSize == 50){
                    out.print("<option value='10'>10</option><option value='20'>20</option><option value='50' selected='true'>50</option>");
                }else{
                    out.print("<option value='"+pageSize+"'>"+pageSize+"</option><option value='10'>10</option><option value='20'>20</option><option value='50'>50</option>");
                }
                out.print("</select>条</li>");
                out.print("<li style='width:100px;'>当前第&nbsp;"+curPage+"/"+totalPage+"&nbsp;页</li>");
                out.print("</ul></div>");


                out.print("<div class='leftPagination'><ul>");

                int start = 1;
                int end = totalPage;
                for(int i=4;i>=1;i--){
                    if((curPage-i)>=1){
                        start = curPage-i;
                        break;
                    }
                }
                for(int i=4;i>=1;i--){
                    if((curPage+i)<=totalPage){
                        end = curPage+i;
                        break;
                    }
                }
                //如果小于9则右侧补齐
                if(end-start+1<=9){
                    Integer padLen = 9-(end-start+1);
                    for(int i=padLen;i>=1;i--){
                        if((end+i)<=totalPage){
                            end = end+i;
                            break;
                        }
                    }
                }

                //如果还小于9左侧补齐
                if(end-start+1<=9){
                    Integer padLen = 9-(end-start+1);
                    for(int i=padLen;i>=1;i--){
                        if((start-i)>=1){
                            start = start-i;
                            break;
                        }
                    }
                }

                if(curPage>1){
                    if(start>1){
                        out.print("<li><a href='javascript:go(1)'>首页</a></li>");
                    }
                    out.print("<li><a href='javascript:go("+(curPage-1)+")'>&lt;&lt;</a></li>");
                }

                for(int i=start;i<=end;i++){
                    if(i==curPage){
                        out.print("<li class='active'>" + i + "</li>");
                    }else{
                        out.print("<li><a href='javascript:go("+i+")'>" + i + "</a></li>");
                    }
                }
                if(curPage<totalPage){
                    out.print("<li><a href='javascript:go("+(curPage+1)+")'>&gt;&gt;</a></li>");
                    if(end<totalPage){
                        out.print("<li><a href='javascript:go("+totalPage+")'>尾页</a></li>");
                    }
                }

                out.print("</ul></div>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.doStartTag();

    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.curPage = null;
        this.totalCount = null;
        this.totalPage = null;
        this.pageSize = null;
        this.formId = null;
    }

    public static Integer getStartIndex(Integer pageNum, Integer pageSize){
        Integer res = 0;
        if(pageNum>0){
            res = (pageNum-1)*pageSize;
        }
        return res;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}