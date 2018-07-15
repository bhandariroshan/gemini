package com.example.rbhandari.datasyncapplication.requesthandler;

import com.example.rbhandari.datasyncapplication.datahandler.UserHandler;
import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler;

import org.json.JSONObject;

public class ResponseHandler {
    public static void route(JSONObject requestData, JSONObject responseData, String className, String method){
        if (className.toLowerCase().equals("user") && method.toLowerCase().equals("post")){
            String username="";
            String objectId = "";
            try{
                username = requestData.get("username").toString();
                JSONObject response = (JSONObject) responseData.get("response");
                objectId = response.get("objectId").toString();
            } catch (Exception e){}

            UserHandler.updateLocalUser(objectId, username);
        }

        else if (className.toLowerCase().equals("auditmain") && method.toLowerCase().equals("post")){
                Long auditId;
                String objectId = "";
                try{
                    auditId = (Long) requestData.get("auditId");

                    JSONObject response = (JSONObject) responseData.get("response");
                    objectId = response.get("objectId").toString();

                    AuditHandler.updateLocalAudit(objectId, auditId);

                } catch (Exception e){
                    System.out.println("Exception Occurred");
                }

            }
    }
}
