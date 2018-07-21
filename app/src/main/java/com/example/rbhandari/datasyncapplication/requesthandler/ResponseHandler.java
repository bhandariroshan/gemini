package com.example.rbhandari.datasyncapplication.requesthandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datahandler.FeatureHandler;
import com.example.rbhandari.datasyncapplication.datahandler.TypeHandler;
import com.example.rbhandari.datasyncapplication.datahandler.UserHandler;
import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler;
import com.example.rbhandari.datasyncapplication.datahandler.ZoneHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseHandler {
    public void route(JSONObject requestData, JSONObject responseData, String className, String method){
        JSONObject newData = new JSONObject();
        try {
            newData.put("response", new JSONObject(responseData.get("response").toString()));
        } catch (Exception e) {
            Log.e("ResponseHandler", "Error whilw parsing response", e);
        }
        if (className.toLowerCase().equals("user") && method.toLowerCase().equals("post")){
            String username="";
            String objectId = "";
            try{
                username = requestData.get("username").toString();
                JSONObject response = (JSONObject) newData.get("response");
                objectId = response.get("objectId").toString();
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }

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
                    Log.e("ResponseHandler", "Error in JSON value extraction", e);
                }

            }
        else if (className.toLowerCase().equals("zonemain") && method.toLowerCase().equals("post")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) responseData.get("response");
                objectId = response.get("objectId").toString();
                ZoneHandler.updateLocalZone(objectId, localId);
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }
        }

        else if (className.toLowerCase().equals("typemain") && method.toLowerCase().equals("post")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) responseData.get("response");
                objectId = response.get("objectId").toString();
                TypeHandler.updateLocalType(objectId, localId);
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }
        }

        else if (className.toLowerCase().equals("featuremain") && method.toLowerCase().equals("post")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) responseData.get("response");
                objectId = response.get("objectId").toString();
                FeatureHandler.updateFeatureAtLocal(objectId, localId);
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }
        }
    }

    public void routeBatchOpData(JSONArray requestData, JSONObject responseData, String className, String method){
        try{
            JSONArray data = new JSONArray(responseData.get("response").toString());
            for (int i=0; i<data.length(); i++){
                String localId = ((JSONObject) requestData.get(i)).get("id").toString();
                String objectId =((JSONObject)((JSONObject) data.get(i)).get("success")).get("objectId").toString();
                FeatureHandler.updateFeatureAtLocal(objectId, localId);
            }
            System.out.println("aaaa");
        } catch (Exception e) {
            Log.e("ResponseHandler", "Error in JSON value extraction", e);
        }
    }
}
