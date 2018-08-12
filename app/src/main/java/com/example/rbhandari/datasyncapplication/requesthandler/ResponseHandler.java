package com.example.rbhandari.datasyncapplication.requesthandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datahandler.FeatureHandler;
import com.example.rbhandari.datasyncapplication.datahandler.TypeHandler;
import com.example.rbhandari.datasyncapplication.datahandler.UserHandler;
import com.example.rbhandari.datasyncapplication.datahandler.AuditHandler;
import com.example.rbhandari.datasyncapplication.datahandler.ZoneHandler;
import com.example.rbhandari.datasyncapplication.datamodels.Feature;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class ResponseHandler {
    public void route(JSONObject requestData, JSONObject responseData, String className, String method){
        if (method.toLowerCase().equals("get")){
            getHandler(requestData, responseData, className);
        } else{
            postHandler(requestData, responseData, className);
        }
    }

    private static void getHandler(JSONObject requestData, JSONObject responseData, String className){

            try{
                JSONObject response = new JSONObject(responseData.get("response").toString());
                JSONArray results = (JSONArray) response.get("results");

                for (int i=0; i < results.length(); i++){
                    JSONObject jsonData = (JSONObject) results.get(i);
                    if (className.toLowerCase().equals("user")){
                        UserHandler.saveOrUpdateUserFromParse(jsonData);
                    } else if (className.toLowerCase().equals("auditmain")) {
                        AuditHandler.saveOrUpdateAuditFromParse(jsonData);
                    } else if (className.toLowerCase().equals("zonemain")) {
                        ZoneHandler.saveOrUpdateZoneFromParse(jsonData);
                    } else if (className.toLowerCase().equals("typemain")) {
                        TypeHandler.saveOrUpdateTypeFromParse(jsonData);
                    } else if (className.toLowerCase().equals("featuremain")) {
                        FeatureHandler.saveOrUpdateFeatureFromParse(jsonData);
                    }

                }

            } catch (Exception e) {
                Log.e("ResponseHandler", "Exception Occurred in responseHandler", e);
            }

    }

    private static void postHandler(JSONObject requestData, JSONObject responseData, String className){
        JSONObject newData = new JSONObject();
        try {
            newData.put("response", new JSONObject(responseData.get("response").toString()));
        } catch (Exception e) {
            Log.e("ResponseHandler", "Error while parsing response", e);
        }
        if (className.toLowerCase().equals("user")){
            String username="";
            String objectId = "";
            try{
                username = requestData.get("userName").toString();
                JSONObject response = (JSONObject) newData.get("response");
                objectId = response.get("objectId").toString();
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }

            UserHandler.updateLocalUser(objectId, username, true);
        }

        else if (className.toLowerCase().equals("auditmain")){
            String auditId;
            String objectId = "";
            try{
                auditId = requestData.get("auditId").toString();

                JSONObject response = (JSONObject) responseData.get("response");
                objectId = response.get("objectId").toString();

                AuditHandler.updateLocalAudit(objectId, auditId, true);

            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }

        }
        else if (className.toLowerCase().equals("zonemain")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) newData.get("response");
                objectId = response.get("objectId").toString();
                ZoneHandler.updateZoneAtLocal(objectId, localId, true);
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }
        }

        else if (className.toLowerCase().equals("typemain")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) newData.get("response");
                objectId = response.get("objectId").toString();
                TypeHandler.updateTypeAtLocal(objectId, localId, true);
            } catch (Exception e){
                Log.e("ResponseHandler", "Error in JSON value extraction", e);
            }
        }

        else if (className.toLowerCase().equals("featuremain")){
            String objectId = "";
            String localId ="";
            try{
                localId = requestData.get("id").toString();
                JSONObject response = (JSONObject) newData.get("response");
                objectId = response.get("objectId").toString();
                FeatureHandler.updateFeatureAtLocal(objectId, localId, true);
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
                if (className.toLowerCase().equals("featuremain")){
                    FeatureHandler.updateFeatureAtLocal(objectId, localId, true);
                } else if (className.toLowerCase().equals("auditmain")){
                    AuditHandler.updateLocalAudit(objectId, localId, true);
                } else if (className.toLowerCase().equals("typemain")){
                    TypeHandler.updateTypeAtLocal(objectId, localId, true);
                } else if (className.toLowerCase().equals("zonemain")){
                    ZoneHandler.updateZoneAtLocal(objectId, localId, true);
                } else if (className.toLowerCase().equals("user")){
                    UserHandler.updateLocalUser(objectId, localId, true);
                }
            }
        } catch (Exception e) {
            Log.e("ResponseHandler", "Error in JSON value extraction", e);
        }
    }
}
