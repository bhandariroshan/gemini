package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;

import java.util.Date;

public class Zone extends SugarRecord<Zone>{
    private String parseId;
    private Long auditId;
    private String username;

    private String name;
    private String type;
    private Date created;
    private Date updated;

    public Zone(){

    }

    public Zone(String parseId, String username, String name, String type, Long auditId, Date created, Date updated ){
        this.parseId = parseId;
        this.username = username;
        this.name = name;
        this.type = type;
        this.auditId = auditId;
        this.created = created;
        this.updated = updated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAudit(Audit audit) {
        this.auditId = auditId;
    }

    public Long getAuditId(){
        return this.auditId;
    }

    public String getType() {
        return this.type;
    }

    public Date getCreated(){
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return updated;
    }
}
