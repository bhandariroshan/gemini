package com.example.rbhandari.datasyncapplication.requesthandler;

import com.example.rbhandari.datasyncapplication.datamodels.Audit;
import com.example.rbhandari.datasyncapplication.datamodels.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditHandler {

    private static String auditClassName = "AuditMain";
    private static JSONObject auditData = new JSONObject();

    public AuditHandler(User user, Long auditId, String parseId, String name, Date createdAt, Date updatedAt){
        try {
            Date date = new Date();

            auditData.put("localCreatedAt", createdAt);
            auditData.put("localUpdatedAt", updatedAt);

            auditData.put("user", user);
            auditData.put("auditId", auditId);
            auditData.put("parseId", parseId);
            auditData.put("name", name);

        }catch (JSONException e){}
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
            Audit audit = (Audit) (Audit.getAuditByAuditId(auditId.toString())).get(0);
            audit.setParseId(objectId);
            audit.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void createAllLocalAuditToParse(){
        JSONArray audits = Audit.getAllParseIdNotSetAudits();
        for (int i =0; i < audits.length(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                Audit audit = (Audit) audits.get(i);
                AuditHandler auditHandler = new AuditHandler(
                        audit.getUser(),
                        audit.getId(),
                        audit.getParseId(),
                        audit.getName(),
                        audit.getCreatedAt(),
                        audit.getUpdatedAt()
                );
                auditHandler.createAuditParse();
            } catch (Exception e){}

        }
    }
}
