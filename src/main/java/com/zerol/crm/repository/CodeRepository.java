package com.zerol.crm.repository;

import com.zerol.crm.entry.common.MyCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository {

   MyCode findCodeByCodeTypIDAndCodeID(String codeTypeID, String codeID);

   List<MyCode> findCodeListByCodeTypID(String codeTypeID);

   void saveCode(MyCode code);

   void deleteCodeByCodeTypeID(String codeTypeID);

   void deleteCodeByCodeTypeIDAndCodeID(String codeTypeID, String codeID);
}