package com.example.rbhandari.datasyncapplication.datahandler;

import android.util.Log;

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
    private JSONObject auditData = new JSONObject();

    public AuditHandler(){

    }

    public AuditHandler(String userName, Long auditId, String parseId, String name, Date createdAt, Date updatedAt){
        try {
            Date date = new Date();

            auditData.put("localCreatedAt", createdAt);
            auditData.put("localUpdatedAt", updatedAt);

            auditData.put("userName", userName);
            auditData.put("auditId", auditId);
            auditData.put("parseId", parseId);
            auditData.put("name", name);

        }catch (JSONException e){
            Log.e("AuditHandler", "Exception occurred while creating initializing handler data.", e);
        }
    }

    public JSONObject getAuditData() {
        return auditData;
    }

    public void createLocalAudit(){
        String userName;
        Long auditId;
        String parseId="";
        String name="";
        Date createdAt;
        Date updatedAt;

        try{
            userName = auditData.get("userName").toString();
            auditId = (Long) auditData.get("auditId");
            parseId = auditData.get("parseId").toString();
            name = auditData.get("name").toString();
            createdAt = (Date) auditData.get("localCreatedAt");
            updatedAt = (Date) auditData.get("localUpdatedAt");
            Audit audit = new Audit(userName, name, createdAt, updatedAt, parseId);
            audit.save();
        } catch (Exception e) {
            Log.e("AuditHandler", "Exception occurred while initializing data.", e);
        }
    }

    public void createAuditParse(){
        ApiHandler.createParseObject(auditData, auditClassName);
    }

    public void getAuditFromParse(String objectId){
        ApiHandler.getParseObjects(auditData, auditClassName, objectId);
    }

    public static void updateLocalAudit(String objectId, Long auditId) {
        try{
            Audit audit = (Audit) (AuditHandler.getAuditByAuditId(auditId.toString())).get(0);
            audit.setParseId(objectId);
            audit.save();
        } catch (Exception e) {
            Log.e("AuditHandler", "Exception occurred while creating updating data.", e);
        }
    }

    public static void syncAllLocalAuditToParse(){
        JSONArray audits = AuditHandler.getAllParseIdNotSetAudits();
        JSONArray data = new JSONArray();
        for (int i =0; i < audits.length(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                Audit audit = (Audit) audits.get(i);
                AuditHandler auditHandler = new AuditHandler(
                        audit.getUserName(),
                        audit.getId(),
                        audit.getParseId(),
                        audit.getName(),
                        audit.getCreated(),
                        audit.getUpdated()
                );
                data.put(auditHandler.getAuditData());
            } catch (Exception e){
                Log.e("AuditHandler", "Exception occurred while creating json data.", e);
            }

        }
        ApiHandler.doBatchOperation(data, auditClassName, ApiHandler.getBatchOperationRequestMethod());
    }

    public static JSONArray getAuditByParseId(String parseid){
        List<Audit> audits = Audit.find(Audit.class,"parse_id=?",parseid);
        JSONArray auditArray = new JSONArray();
        for(int i = 0;i<audits.size();i++)
        {
            Audit audit = audits.get(i);
            try
            {
                auditArray.put(audit);

            }
            catch (Exception e){
                Log.e("AuditHandler", "Exception occurred while creating json data in getAuditByParseId.", e);
            }
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
            catch (Exception e){
                Log.e("AuditHandler", "Exception occurred while creating json data in getAuditByAuditId.", e);
            }
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
            catch (Exception e){
                Log.e("AuditHandler", "Exception occurred while creating json array in getAllParseIdNotSetAudits.", e);
            }
        }
        return auditArray;
    }

    public static void syncAllUserAuditsFromParse(String username){
        try{
            JSONObject query = new JSONObject();
            JSONObject parameter = new JSONObject();
            parameter.put("username", username);
            query.put("where", parameter);
            ApiHandler.getParseObjects(query, auditClassName, "");

        } catch (Exception e) {
            Log.e("UserHandler", "Exception while creating where query for request.",e);
        }
    }

    public static void saveOrUpdateAuditFromParse(JSONObject jsonData) {
        Audit audit;
        try{
            String parseId =  jsonData.get("objectId").toString();
            audit = (Audit) AuditHandler.getAuditByParseId(parseId).get(0);
        } catch (Exception e) {
            audit = new Audit();
        }

        try {
            audit.setId(Long.valueOf(jsonData.get("auditId").toString()));
            audit.setUserName(jsonData.get("username").toString());
            audit.setName(jsonData.get("name").toString());
            audit.setParseId(jsonData.get("objectId").toString());
            audit.setCreated(new Date(jsonData.get("localCreatedAt").toString()));
            audit.setUpdated(new Date(jsonData.get("localUpdatedAt").toString()));
            audit.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
