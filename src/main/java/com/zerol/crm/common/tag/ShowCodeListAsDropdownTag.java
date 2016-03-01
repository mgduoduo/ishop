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
public class ShowCodeListAsDropdownTag extends BaseTag {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShowCodeListAsDropdownTag.class);

    private String codeTypeID;

    private String multiSelect;

    private String value;
    private String name;
    private String id;
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

            StringBuffer stringBuffer = new StringBuffer();
            //head
            stringBuffer.append("<select ");
            if(null!=multiSelect && !"".equals(multiSelect)){
                multiSelect = multiSelect.trim().toLowerCase();
                if(!"true".equals(multiSelect) || !"multiSelect".equals(multiSelect)){
                    stringBuffer.append(" multiSelect='multiSelect' ");
                }
            }

            if(null!=name && !"".equals(name.trim())){
                stringBuffer.append(" name='"+name+"' ");
            }
            if(null!=id && !"".equals(id.trim())){
                stringBuffer.append(" id='"+id+"' ");
            }
            if(null!=styleClass && !"".equals(styleClass.trim())){
                stringBuffer.append(" class='"+styleClass+"' ");
            }
            stringBuffer.append(">");

            out.println(stringBuffer.toString());

            out.println("<option value=''>---</option>");
            if (codeList != null && !codeList.isEmpty()) {
                for(MyCode code: codeList){
                    String defaultSelectOption = "";
                    if(code.getCodeID().equals(value)){

                        defaultSelectOption = "selected='"+(code.getCodeID().equals(value))+"'";
                    }
                    out.println("<option value='"+code.getCodeID()+"' "+defaultSelectOption+" >"+code.getCodeDesc()+"</option>");
                }
            }
            out.println("</select>");

        } catch (Exception e) {
            LOGGER.debug("Failed to convert for dropdown list, value="+value +", codeTypeID="+codeTypeID);
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
        this.multiSelect = null;
        this.name = null;
        this.value = null;
        this.id = null;
        this.styleClass = null;
    }

    public String getCodeTypeID() {
        return codeTypeID;
    }

    public void setCodeTypeID(String codeTypeID) {
        this.codeTypeID = codeTypeID;
    }

    public String getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(String multiSelect) {
        this.multiSelect = multiSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
