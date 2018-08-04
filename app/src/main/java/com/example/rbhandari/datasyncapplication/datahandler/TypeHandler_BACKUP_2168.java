package com.example.rbhandari.datasyncapplication.datahandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datamodels.Audit;
import com.example.rbhandari.datasyncapplication.datamodels.TypeClass;
import com.example.rbhandari.datasyncapplication.datamodels.Zone;
import com.example.rbhandari.datasyncapplication.requesthandler.ApiHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class TypeHandler {
    private static String className = "TypeMain";
    private JSONObject typeData = new JSONObject();

    private JSONObject zoneData = new JSONObject();

    private static boolean checkTypeExists(String id) {
        JSONArray userArray = getTypeById(id);
        return userArray.length()>0;
    }

    public JSONObject getTypeData() {
        return typeData;
    }

    public TypeHandler(String userName, String id, String parseId, Long zoneId, Long auditId, String name, String subType,
                       Date created, Date updated, Boolean isUpdated){
        try {
            typeData.put("username", userName);
            typeData.put("id", id);
            typeData.put("parseId",parseId);
            typeData.put("zoneId",zoneId);
            typeData.put("auditId",auditId);
            typeData.put("subType", subType);
            typeData.put("name", name);
            typeData.put("created", created);
            typeData.put("updated", updated);
            typeData.put("isUpdated", isUpdated);

        }catch (JSONException e){
            System.out.println("Exception Occurred in Constructor.");
        }
    }

    public void createTypeAtLocal(){
        String userName;
        String parseId;
        Long zoneId;
        Long auditId;
        String name;
        String subType;
        Date created;
        Date updated;
        Boolean isUpdated;

        try{
            userName = typeData.get("userName").toString();
            parseId = typeData.get("parseId").toString();
            zoneId = (Long) typeData.get("zoneId");
            auditId = (Long) typeData.get("auditId");

            name = typeData.get("name").toString();
            subType =  typeData.get("subType").toString();

            created = (Date) typeData.get("created");
            updated = (Date) typeData.get("updated");

            isUpdated = (Boolean) typeData.get("isUpdated");

            TypeClass type = new TypeClass(userName, parseId, zoneId, auditId, name, subType, created, updated, isUpdated);
            type.save();

        } catch (Exception e) {
            System.out.println("Error getting data.");
        }
    }

    public void createTypeAtParse(){
        ApiHandler.createParseObject(typeData, className);
    }

    public void getTypeFromParse(String objectId){
        ApiHandler.getParseObjects(typeData, className, objectId);
    }

    public static void updateTypeAtLocal(String objectId, String id) {
        try{
            TypeClass type = (TypeClass) (TypeHandler.getTypeById(id)).get(0);
            type.setParseId(objectId);
            type.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void syncAllLocalTypesToParse(){
        JSONArray types = TypeHandler.getAllParseIdNotSetTypes();
        JSONArray data = new JSONArray();
        for (int i =0; i < types.length(); i++){
            try {
                TypeClass type = (TypeClass) types.get(i);
                TypeHandler typeHandler = new TypeHandler(
                        type.getUserName(),
                        type.getId().toString(),
                        type.getParseId(),
                        type.getZoneId(),
                        type.getAuditId(),
                        type.getName(),
                        type.getSubType(),
                        type.getCreated(),
                        type.getUpdated(),
                        type.getIsUpdated()
                );
                data.put(typeHandler.getTypeData());
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
        ApiHandler.doBatchOperation(data, className, ApiHandler.getBatchOperationRequestMethod(), false);
    }

    public static JSONArray getTypeByParseId(String parseid){
        List<TypeClass> types = TypeClass.find(TypeClass.class,"parse_id=?",parseid);
        JSONArray typeArrray = new JSONArray();
        for(int i = 0;i<types.size();i++)
        {
            TypeClass zone = types.get(i);
            try
            {
                typeArrray.put(zone);
            }
            catch (Exception e){
                System.out.println("Exception occurred while finding zone.");
            }
        }
        return typeArrray;
    }

    public static JSONArray getTypeById(String id){
        List<TypeClass> types = TypeClass.find(TypeClass.class,"id=?",id);
        JSONArray typeArray = new JSONArray();
        for(int i = 0;i<types.size();i++)
        {
            TypeClass type = types.get(i);
            try
            {
                typeArray.put(type);
            }
            catch (Exception e){
                System.out.println("Type could not be found.");
            }
        }
        return typeArray;
    }

    private static JSONArray getAllRecentlyUpdatedTypes(){
        List<TypeClass> types = TypeClass.find(TypeClass.class,"parse_id is not NULL and parse_id !='' and isUpdated=1","");
        JSONArray typeArray = new JSONArray();
        for(int i = 0;i<types.size();i++)
        {
            TypeClass type = types.get(i);
            try
            {
                typeArray.put(type);
            }
            catch (Exception e){
                System.out.println("Zone could not be found.");
            }
        }
        return typeArray;
    }

    private static JSONArray getAllParseIdNotSetTypes(){
        List<TypeClass> types = TypeClass.find(TypeClass.class,"parse_id=?","");
        JSONArray typeArray = new JSONArray();
        for(int i = 0;i<types.size();i++)
        {
            TypeClass type = types.get(i);
            try
            {
                typeArray.put(type);
            }
            catch (Exception e){
                System.out.println("Zone could not be found.");
            }
        }
        return typeArray;
    }

    public static void syncAllTypesFromParse(String username) {
        try{
            JSONObject query = new JSONObject();
            JSONObject parameter = new JSONObject();
            parameter.put("username", username);
            query.put("where", parameter);
            ApiHandler.getParseObjects(query, className, "");

        } catch (Exception e) {
            Log.e("TypeHandler", "Exception while creating where query for request.",e);
        }
    }

    public static void saveOrUpdateTypeFromParse(JSONObject jsonData) {
        TypeClass typeClass;
        try{
            String parseId =  jsonData.get("objectId").toString();
            typeClass = (TypeClass) TypeHandler.getTypeByParseId(parseId).get(0);
        } catch (Exception e) {
            typeClass = new TypeClass();
        }

        try {
            typeClass.setAuditId(Long.valueOf(jsonData.get("auditId").toString()));
            typeClass.setParseId(jsonData.get("objectId").toString());
            typeClass.setUserName(jsonData.get("username").toString());
            typeClass.setParseId(jsonData.get("objectId").toString());
            typeClass.setName(jsonData.get("name").toString());
            typeClass.setSubType(jsonData.get("subType").toString());
            typeClass.setZoneId(Long.valueOf(jsonData.get("zoneId").toString()));
            typeClass.setCreated(new Date(jsonData.get("created").toString()));
            typeClass.setUpdated(new Date(jsonData.get("updated").toString()));
            typeClass.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void saveAllTypeChangesToParse() {
        JSONArray types = TypeHandler.getAllRecentlyUpdatedTypes();
        JSONArray data = new JSONArray();
        for (int i =0; i < types.length(); i++){
            try {
                TypeClass type = (TypeClass) types.get(i);
                TypeHandler typeHandler = new TypeHandler(
                        type.getUserName(),
                        type.getId().toString(),
                        type.getParseId(),
                        type.getZoneId(),
                        type.getAuditId(),
                        type.getName(),
                        type.getSubType(),
                        type.getCreated(),
                        type.getUpdated(),
                        type.getIsUpdated()
                );
                data.put(typeHandler.getTypeData());
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
        ApiHandler.doBatchOperation(data, className, ApiHandler.getBatchOperationRequestMethod(), true);
    }

}
