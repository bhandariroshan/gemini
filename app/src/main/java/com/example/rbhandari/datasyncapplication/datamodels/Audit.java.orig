package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;
import org.json.JSONArray;
import java.util.Date;
import java.util.List;


public class Audit extends SugarRecord<Audit> {
    private String userName;
    private String name;
    private String parseId;
    private Date created;
    private Date updated;
    private Boolean isUpdated;

    public void setCreated(Date createdAt) {
        this.created = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdated(Date updatedAt) {
        this.updated = updatedAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return this.userName;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getParseId() {
        return parseId;
    }

    public void setIsUpdated(Boolean updated) {
        this.isUpdated = updated;
    }

    public Boolean getIsUpdated(){
        return this.isUpdated;
    }

    public Audit(){

    }

    public Audit(String username, String name, Date createdAt, Date updatedAt, String parseId, Boolean isUpdated){
        this.userName=username;
        this.name=name;
        this.created=createdAt;
        this.updated=updatedAt;
        this.parseId=parseId;
        this.isUpdated=isUpdated;
    }
}
