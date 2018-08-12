package com.example.rbhandari.datasyncapplication.requesthandler;

import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ApiHandler {
    private static String parseApiRoot = "http://ec2-18-220-200-115.us-east-2.compute.amazonaws.com:80/parse/";
    private static String applicationId = "47f916f7005d19ddd78a6be6b4bdba3ca49615a0";
    private static String masterKey = "NLI214vDqkoFTJSTtIE2xLqMme6Evd0kA1BbJ20S";
    private static String classPath = "classes/";
    private static String bathOperationPath = "batch";
    private static String retrieveObjectRequestMethod = "GET";
    private static String createObjectRequestMethod = "POST";
    private static String updateObjectRequestMethod = "PUT";
    private static String deleteObjectRequestMethod = "DELETE";
    private static String batchOperationRequestMethod = "POST";

    public static String getBatchOperationRequestMethod() {
        return batchOperationRequestMethod;
    }

    public static String getBathOperationPath() {
        return bathOperationPath;
    }

    public static String getClassPath() {
        return classPath;
    }

    public static String getMasterKey() {
        return masterKey;
    }

    public static String getApplicationId() {
        return applicationId;
    }

    public static String getCreateObjectRequestMethod() {
        return createObjectRequestMethod;
    }

    public static String getDeleteObjectRequestMethod() {
        return deleteObjectRequestMethod;
    }

    public static String getParseApiRoot() {
        return parseApiRoot;
    }

    public static String getRetrieveObjectRequestMethod() {
        return retrieveObjectRequestMethod;
    }

    public static String getUpdateObjectRequestMethod() {
        return updateObjectRequestMethod;
    }

    public static void createParseObject(final JSONObject data, final String classname){
        String createObjectUrl = parseApiRoot + classPath + classname;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    data,
                    getApplicationId(),
                    getMasterKey(),
                    createObjectUrl,
                    getCreateObjectRequestMethod(),
                    new OnEventListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            ResponseHandler responseHandler = new ResponseHandler();
                            responseHandler.route(data, result, classname, getCreateObjectRequestMethod());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("APIHandler", "Error in  create request", e);
                        }
                    });
            requestHandler.execute();

        } catch (Exception e){
            Log.e("APIHandler", "Error while creating parse object.", e);
        }
    }

    public static void updateParseObject(final JSONObject data, String objectID, final String classname){
        String updateObjectUrl = parseApiRoot + classPath + classname + "/" + objectID;
        try{
            RequestHandler requestHandler = new RequestHandler(
                    data,
                    getApplicationId(),
                    getMasterKey(),
                    updateObjectUrl,
                    getUpdateObjectRequestMethod(),
                    new OnEventListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            ResponseHandler responseHandler = new ResponseHandler();
                            responseHandler.route(data, result, classname, getUpdateObjectRequestMethod());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("APIHandler", "Error in update request", e);
                        }
                    });
            requestHandler.execute();

        } catch (Exception e){
            Log.e("APIHandler", "Error while creating parse object.", e);
        }
    }

    public static void deleteParseObject(final String objectID, String classname){
        String deleteObjecteUrl = parseApiRoot + classPath + classname + "/" + objectID;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    new JSONObject(),
                    getApplicationId(),
                    getMasterKey(),
                    deleteObjecteUrl,
                    getDeleteObjectRequestMethod(),
                    new OnEventListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {

                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("APIHandler", "Eror in request", e);
                        }
                    });
            requestHandler.execute();

        } catch (Exception e){
            Log.e("APIHandler", "Error while creating parse object.", e);
        }
    }

    public static void getParseObjects(final JSONObject query, final String classname, String objectId){
        String createUrl;
        if (objectId.equals("")){
            createUrl = parseApiRoot + classPath + classname;
        } else {
            createUrl = parseApiRoot + classPath + classname + "/" + objectId;;
        }

        try{
            RequestHandler requestHandler = new RequestHandler(
                    query,
                    getApplicationId(),
                    getMasterKey(),
                    createUrl,
                    getRetrieveObjectRequestMethod(),
                    new OnEventListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            ResponseHandler responseHandler = new ResponseHandler();
                            responseHandler.route(query, result, classname, getRetrieveObjectRequestMethod());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("APIHandler", "Eror in request", e);
                        }
                    });
            requestHandler.execute();

        } catch (Exception e){
            Log.e("APIHandler", "Error while executing get parse object.", e);
        }
    }

    private static JSONObject createBatchOperationData(String className, String methodType, JSONArray data, Boolean updateObject){
        JSONArray jsonArray = new JSONArray();
        JSONObject returnData = new JSONObject();

        for (int i =0; i<data.length(); i++){
            try {
                JSONObject jsonObject = new JSONObject();
                if (updateObject){
                    jsonObject.put("method", ApiHandler.getUpdateObjectRequestMethod());
                    String parseId = ((JSONObject) data.get(i)).get("parseId").toString();
                    jsonObject.put("path", "/parse/classes/"+className + "/" + parseId);
                } else{
                    jsonObject.put("method", methodType);
                    jsonObject.put("path", "/parse/classes/"+className);
                }

                jsonObject.put("body", data.get(i));
                jsonArray.put(jsonObject);
            } catch (Exception e){
                Log.e("APIHandler", "Error while creating Batch Operation data.", e);
            }
        }

        try{
            returnData.put("requests", jsonArray);
        } catch (Exception e){
            Log.e("APIHandler", "Error while creating jsonObject.", e);
        }
        return returnData;
    }

    public static void doBatchOperation(final JSONArray data, final String classname, String actionType, Boolean isUpdate){
        JSONObject postData = ApiHandler.createBatchOperationData(
                classname,
                actionType,
                data,
                isUpdate
        );
        String createUrl = parseApiRoot + bathOperationPath;
        try{
            RequestHandler requestHandler = new RequestHandler(
                    postData,
                    getApplicationId(),
                    getMasterKey(),
                    createUrl,
                    getBatchOperationRequestMethod(),
                    new OnEventListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            ResponseHandler responseHandler = new ResponseHandler();
                            responseHandler.routeBatchOpData(data, result, classname, getBatchOperationRequestMethod());
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e("APIHandler", "Eror in request", e);
                        }
                    });
            requestHandler.execute();

        } catch (Exception e){
            Log.e("APIHandler", "Error while executing batch request.", e);
        }
    }
}