package com.zerol.crm.entry.common;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gaoqiang on 30/1/2015.
 */
@Entity
@Table(name = "TB_CODE_INT")
public class MyCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "CODETYPE_ID")
    private String codeTypeID;

    @Column(name = "CODE_ID")
    private String codeID;

    @Column(name = "CODE_DESC")
    private String codeDesc;

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

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
