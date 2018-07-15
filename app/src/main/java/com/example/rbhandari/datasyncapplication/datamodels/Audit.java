package com.example.rbhandari.datasyncapplication.datamodels;

import com.orm.SugarRecord;
import org.json.JSONArray;
import java.util.Date;
import java.util.List;


public class Audit extends SugarRecord<Audit> {
    private User user;
    private String name;
    private String parseId;
    private Date localCreatedAt;
    private Date localUpdatedAt;

    public void setCreatedAt(Date createdAt) {
        this.localCreatedAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.localUpdatedAt = updatedAt;
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

    public Date getCreatedAt() {
        return localCreatedAt;
    }

    public Date getUpdatedAt() {
        return localUpdatedAt;
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
        this.localCreatedAt=createdAt;
        this.localUpdatedAt=updatedAt;
        this.parseId=parseId;
    }

    public static JSONArray getAuditByParseId(String parseid){
        List<Audit> audits = Audit.find(Audit.class,"parseId=?",parseid);
        JSONArray auditArray = new JSONArray();
        for(int i = 0;i<audits.size();i++)
        {
            Audit audit = audits.get(i);
            try
            {
                auditArray.put(audit);

            }
            catch (Exception e){}
        }
        return auditArray;
    }

    public static JSONArray getAuditByAuditId(String id){
        List<Audit> audits = Audit.find(Audit.class,"id=?",id);
        JSONArray auditArray = new JSONArray();
        for(int i = 0;i<audits.size();i++)
        {
            Audit audit = audits.get(i);
            try
            {
                auditArray.put(audit);

            }
            catch (Exception e){}
        }
        return auditArray;
    }

    public static JSONArray getAllParseIdNotSetAudits(){
        List<Audit> audits = Audit.find(Audit.class,"parse_Id=?","");
        JSONArray auditArray = new JSONArray();
        for(int i = 0;i<audits.size();i++)
        {
            Audit user = audits.get(i);
            try
            {
                auditArray.put(user);
            }
            catch (Exception e){}
        }
        return auditArray;
    }
}
