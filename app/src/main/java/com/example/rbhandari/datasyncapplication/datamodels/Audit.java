package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;
import org.json.JSONArray;
import java.util.Date;
import java.util.List;


public class Audit extends SugarRecord<Audit> {
    private User user;
    private String name;
    private String parseId;
    private Date created;
    private Date updated;

    public void setCreated(Date createdAt) {
        this.created = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdated(Date updatedAt) {
        this.updated = updatedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
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

    public Audit(){

    }

    public Audit(User user, String name, Date createdAt, Date updatedAt, String parseId){
        this.user=user;
        this.name=name;
        this.created=createdAt;
        this.updated=updatedAt;
        this.parseId=parseId;
    }
}
