package com.zerol.crm.common.tag;

import com.zerol.crm.entry.common.MyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.List;

/**
 * Created by gaoqiang on 30/1/2015.
 */
public class ShowCodeListAsRadioGroupTag extends BaseTag {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCodeListAsRadioGroupTag.class);

    private String codeTypeID;

    private String name;
    private String value;
    private String styleClass;

//    private CodeService codeService;
//
//    public void setCodeService(CodeService codeService) {
//        this.codeService = codeService;
//    }
//
//    public CodeService getCodeService() {
//        if(codeService == null){
//            //从上下文中取出已经初始化的service实例
//            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
//            Map<String, Object> oMap =  (Map) applicationContext.getBeansOfType(CodeService.class);
//            codeService = (CodeService)oMap.get("codeServiceImpl");
//        }
//        return codeService;
//    }

    public int doStartTag() throws JspException {
        try {

            JspWriter out = this.pageContext.getOut();

            List<MyCode> codeList = getCodeService().findCodeListByCodeTypID(codeTypeID);

            if (codeList != null && !codeList.isEmpty()) {
                for(MyCode code: codeList){
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("<input type='radio'");
                    if(null!=name && !"".equals(name.trim())){
                        stringBuffer.append(" name='" + name + "' ");
                    }
                    if(null!=styleClass && !"".equals(styleClass.trim())){
                        stringBuffer.append(" class='" + styleClass + "' ");
                    }
                    stringBuffer.append(" value='" + code.getCodeID() + "' ");
                    if(code.getCodeID().equals(value)){
                        stringBuffer.append(" checked='true' ");
                    }
                    stringBuffer.append(">");
                    stringBuffer.append(code.getCodeDesc());
                    stringBuffer.append("</input>");
                    out.println(stringBuffer.toString());
                }
            }

        } catch (Exception e) {
            LOGGER.debug("Failed to convert for radio group, value="+value +", codeTypeID="+codeTypeID);
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
        this.name = null;
        this.value = null;
        this.styleClass = null;
    }

    public String getCodeTypeID() {
        return codeTypeID;
    }

    public void setCodeTypeID(String codeTypeID) {
        this.codeTypeID = codeTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
