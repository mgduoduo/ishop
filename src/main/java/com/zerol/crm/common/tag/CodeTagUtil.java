package com.zerol.crm.common.tag;

import com.zerol.crm.service.web.CodeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by gaoqiang on 4/2/2015.
 */
public class CodeTagUtil {

    private static CodeService codeService;

    public static CodeService getCodeService() {
        return codeService;
    }
}
