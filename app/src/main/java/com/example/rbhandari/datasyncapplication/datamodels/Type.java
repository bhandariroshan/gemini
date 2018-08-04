package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;

import java.security.acl.LastOwnerException;
import java.util.Date;

public class Type extends SugarRecord<Type>{
    private String parseId;
    private Long zoneId;
    private Long auditId;
    private Long typeId;

    private String name;
    private String subType;

    private Date created;
    private Date updated;

    public String getParseId() {
        return parseId;
    }

    public Type(String parseId, Long zoneId, Long auditId, Long typeId, String name, String subType,
                Date created, Date updated){
        this.parseId=parseId;
        this.zoneId=zoneId;
        this.auditId=auditId;
        this.typeId=typeId;
        this.name=name;
        this.subType=subType;
        this.created=created;
        this.updated=updated;
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

    public Long getTypeId() {
        return typeId;
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

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}
