package com.zerol.crm.service;

import com.zerol.crm.entry.common.MyCode;
import com.zerol.crm.repository.CodeRepository;
import com.zerol.crm.service.web.CodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CodeServiceImpl implements CodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CodeServiceImpl.class);

    @Autowired
    private CodeRepository codeRepository;

    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public MyCode findCodeByCodeTypIDAndCodeID(String codeTypeID, String codeID) {
        return codeRepository.findCodeByCodeTypIDAndCodeID(codeTypeID, codeID);
    }

    @Override
    public List<MyCode> findCodeListByCodeTypID(String codeTypeID) {
        return codeRepository.findCodeListByCodeTypID(codeTypeID);
    }

    @Override
    @Transactional
    public boolean saveCode(MyCode code) {
        try {
            if (code == null || StringUtils.isEmpty(code.getCodeTypeID()) || StringUtils.isEmpty(code.getCodeTypeID())) {
                LOGGER.debug("Cannot save empty code info. One(or more) of code/codeTypeID/codeID is empty.");
                return false;
            }
            MyCode existedCode = this.findCodeByCodeTypIDAndCodeID(code.getCodeTypeID(), code.getCodeID());
            if (existedCode != null) {
                LOGGER.debug("The code mapping existed in DB already.");
                return false;
            }
            codeRepository.saveCode(code);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteCodeByCodeTypeID(String codeTypeID) {
        try {
            codeRepository.deleteCodeByCodeTypeID(codeTypeID);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteCodeByCodeTypeIDAndCodeID(String codeTypeID, String codeID) {
        try {
            codeRepository.deleteCodeByCodeTypeIDAndCodeID(codeTypeID, codeID);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}