package com.zerol.crm.service.web;

import com.zerol.crm.entry.common.MyCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeService {

	public MyCode findCodeByCodeTypIDAndCodeID(String codeTypeID, String codeID);
	public List<MyCode> findCodeListByCodeTypID(String codeTypeID);

	public boolean saveCode(MyCode code);
	public boolean deleteCodeByCodeTypeID(String codeTypeID);
	public boolean deleteCodeByCodeTypeIDAndCodeID(String codeTypeID, String codeID);

}

