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
public class DateFormatTag  extends TagSupport {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateFormatTag.class);

    private String value;

    private String formatTo;

    @Override
    public int doStartTag() throws JspException {
        //2015-01-18
        String defaultOutputDateFormat = "yyyy-MM-dd";
        //Jan 28, 2015
        String datetimeFormat_0 = "MMM dd, yyyy";
        //Jan 28, 2015 12:00:00 AM
        String datetimeFormat_1 = "MMM dd, yyyy HH:mm:ss aa";
        //Wed Jan 28 00:00:00 GMT 2015
        String datetimeFormat_2 = "E MMM dd HH:mm:ss 'GMT' yyyy";

        String inputDateFormat = "";
        try {

            JspWriter out = this.pageContext.getOut();

            if (formatTo == null || "".equals(formatTo.trim())) {
                formatTo = "yyyy-MM-dd";
            }

            boolean isValidDate = false;

//            if(isValidFormat(datetimeFormat_0, value)
//                    || isValidFormat(datetimeFormat_1, value)
//                    || isValidFormat(datetimeFormat_2, value)
//                    || isValidFormat(defaultOutputDateFormat, value)){
//                isValidDate = true;
//            }

            if(!isValidDate && isValidFormat(defaultOutputDateFormat, value)){
                isValidDate = true;
                inputDateFormat = defaultOutputDateFormat;
            }
            if(!isValidDate && isValidFormat(datetimeFormat_0, value)){
                isValidDate = true;
                inputDateFormat = datetimeFormat_0;
            }
            if(!isValidDate && isValidFormat(datetimeFormat_1, value)){
                isValidDate = true;
                inputDateFormat = datetimeFormat_1;
            }
            if(!isValidDate && isValidFormat(datetimeFormat_2, value)){
                isValidDate = true;
                inputDateFormat = datetimeFormat_2;
            }
            if(!isValidDate){
                throw new JspException("Invalid Date Format!");
            }

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
            Date date = inputFormat.parse(value);

            SimpleDateFormat outputFormat = new SimpleDateFormat(defaultOutputDateFormat);
            String newValue = outputFormat.format(date);

            out.println(newValue);

        } catch (Exception e) {
            LOGGER.debug("Cannot convert to date by value="+value +", formatTo="+formatTo);
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
        this.formatTo = null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormatTo() {
        return formatTo;
    }

    public void setFormatTo(String formatTo) {
        this.formatTo = formatTo;
    }
}
