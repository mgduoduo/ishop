package com.zerol.crm.common.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaoqiang on 31/1/2015.
 */
public class MoneyFormatTag extends TagSupport {

    private final static Logger LOGGER = LoggerFactory.getLogger(MoneyFormatTag.class);

    private String value;

    @Override
    public int doStartTag() throws JspException {

        String inputDateFormat = "";
        try {

            JspWriter out = this.pageContext.getOut();
            if (value != null && !"".equals(value.trim())) {
                out.println(value.replaceAll(",", ""));
            }

        } catch (Exception e) {
            LOGGER.debug("Invalid money format. ");
            throw new JspException(e.getMessage());

        }
        return SKIP_BODY;
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setLenient(false); //make it strict
            date = formatter.parse(value);
        } catch (ParseException ex) {
            return false;
        }
        return date != null;
    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.value = null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
