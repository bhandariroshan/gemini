package com.example.rbhandari.datasyncapplication.datahandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import com.example.rbhandari.datasyncapplication.datamodels.User;
import com.example.rbhandari.datasyncapplication.datamodels.Audit;
import com.example.rbhandari.datasyncapplication.requesthandler.ApiHandler;


public class AuditHandler {

    private static String auditClassName = "AuditMain";
    private static JSONObject auditData = new JSONObject();

    public AuditHandler(){

    }

    public AuditHandler(User user, Long auditId, String parseId, String name, Date createdAt, Date updatedAt){
        try {
            Date date = new Date();

            auditData.put("localCreatedAt", createdAt);
            auditData.put("localUpdatedAt", updatedAt);

            auditData.put("user", user);
            auditData.put("auditId", auditId);
            auditData.put("parseId", parseId);
            auditData.put("name", name);

        }catch (JSONException e){
            System.out.println("Exception occurred while creating Audit.");
        }
    }

    public void createLocalAudit(){
        User user;
        Long auditId;
        String parseId="";
        String name="";
        Date createdAt;
        Date updatedAt;

        try{
            user = (User) auditData.get("user");
            auditId = (Long) auditData.get("auditId");
            parseId = auditData.get("parseId").toString();
            name = auditData.get("name").toString();
            createdAt = (Date) auditData.get("localCreatedAt");
            updatedAt = (Date) auditData.get("localUpdatedAt");
            Audit audit = new Audit(user, name, createdAt, updatedAt, parseId);
            audit.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public void createAuditParse(){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createParseObject(auditData, auditClassName);
    }

    public void getAuditFromParse(String objectId){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getParseObject(auditData, auditClassName, objectId);
    }

    public static void updateLocalAudit(String objectId, Long auditId) {
        try{
            Audit audit = (Audit) (AuditHandler.getAuditByAuditId(auditId.toString())).get(0);
            audit.setParseId(objectId);
            audit.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void createAllLocalAuditToParse(){
        JSONArray audits = AuditHandler.getAllParseIdNotSetAudits();
        for (int i =0; i < audits.length(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                Audit audit = (Audit) audits.get(i);
                AuditHandler auditHandler = new AuditHandler(
                        audit.getUser(),
                        audit.getId(),
                        audit.getParseId(),
                        audit.getName(),
                        audit.getCreated(),
                        audit.getUpdated()
                );
                auditHandler.createAuditParse();
            } catch (Exception e){}

        }
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
