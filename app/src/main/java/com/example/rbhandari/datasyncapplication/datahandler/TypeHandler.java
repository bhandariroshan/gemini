package com.example.rbhandari.datasyncapplication.datahandler;

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

    public TypeHandler(String id, String parseId, Long zoneId, Long auditId, String name, String subType,
                       Date created, Date updated){
        try {
            typeData.put("id", id);
            typeData.put("parseId",parseId);
            typeData.put("zoneId",zoneId);
            typeData.put("auditId",auditId);
            typeData.put("subType", subType);
            typeData.put("name", name);
            typeData.put("created", created);
            typeData.put("updated", updated);

        }catch (JSONException e){
            System.out.println("Exception Occurred in Constructor.");
        }
    }

    public void createLocalType(){
        String parseId;
        Long zoneId;
        Long auditId;
        String name;
        String subType;
        Date created;
        Date updated;

        try{
            parseId = typeData.get("parseId").toString();
            zoneId = (Long) typeData.get("zoneId");
            auditId = (Long) typeData.get("auditId");

            name = typeData.get("name").toString();
            subType =  typeData.get("subType").toString();

            created = (Date) typeData.get("created");
            updated = (Date) typeData.get("updated");

            TypeClass type = new TypeClass(parseId, zoneId, auditId, name, subType, created, updated);
            type.save();

        } catch (Exception e) {
            System.out.println("Error getting data.");
        }
    }

    public void createTypeParse(){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createParseObject(typeData, className);
    }

    public void getTypeFromParse(String objectId){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getParseObject(typeData, className, objectId);
    }

    public static void updateLocalType(String objectId, String id) {
        try{
            TypeClass type = (TypeClass) (TypeHandler.getTypeById(id)).get(0);
            type.setParseId(objectId);
            type.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void saveAllLocalTypesToParse(){
        JSONArray types = TypeHandler.getAllParseIdNotSetTypes();
        for (int i =0; i < types.length(); i++){
            try {
                TypeClass type = (TypeClass) types.get(i);
                TypeHandler typeHandler = new TypeHandler(
                        type.getId().toString(),
                        type.getParseId(),
                        type.getZoneId(),
                        type.getAuditId(),
                        type.getName(),
                        type.getSubType(),
                        type.getCreated(),
                        type.getUpdated()
                );
                typeHandler.createTypeParse();
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
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

}
