package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;

import java.security.acl.LastOwnerException;
import java.util.Date;


public class TypeClass extends SugarRecord<TypeClass>{
    private String parseId;
    private Long zoneId;
    private Long auditId;
    private String name;
    private String subType;
    private Date created;
    private Date updated;

    public TypeClass(){

    }

    public TypeClass(String parseId, Long zoneId, Long auditId, String name, String subType,
                Date created, Date updated){
        this.parseId=parseId;
        this.zoneId=zoneId;
        this.auditId=auditId;
        this.name=name;
        this.subType=subType;
        this.created=created;
        this.updated=updated;
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }

    public Long getAuditId() {
        return auditId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}
