package com.zerol.crm.common.tag;

import com.zerol.crm.entry.common.MyCode;
import com.zerol.crm.service.web.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Created by gaoqiang on 30/1/2015.
 */
public class ShowCodeDescTag extends BaseTag {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCodeDescTag.class);

    private String codeTypeID;
    private String codeID;

    @Override
    public int doStartTag() throws JspException {
        try {

            JspWriter out = this.pageContext.getOut();

            this.pageContext.getAttribute("");
            ServletContext application = this.pageContext.getSession().getServletContext();
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
            CodeService actManager = (CodeService)context.getBean("CodeService");

            MyCode code = actManager.findCodeByCodeTypIDAndCodeID(codeTypeID, codeID);
            if (code == null) {
                return SKIP_BODY;
            } else {
                out.println(code.getCodeDesc());
            }

        } catch (Exception e) {
            LOGGER.debug("Cannot convert to code by codeID="+codeID +", codeTypeID="+codeTypeID);
            throw new JspException(e.getMessage());

        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.codeTypeID = null;
        this.codeID = null;
    }

    public String getCodeTypeID() {
        return codeTypeID;
    }

    public void setCodeTypeID(String codeTypeID) {
        this.codeTypeID = codeTypeID;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }
}
