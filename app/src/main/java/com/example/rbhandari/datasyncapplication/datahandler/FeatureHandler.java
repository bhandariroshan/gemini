package com.example.rbhandari.datasyncapplication.datahandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datamodels.Feature;
import com.example.rbhandari.datasyncapplication.datamodels.TypeClass;
import com.example.rbhandari.datasyncapplication.requesthandler.ApiHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class FeatureHandler {
    private static String className = "FeatureMain";
    private JSONObject featureData = new JSONObject();

    public FeatureHandler(String username, String id, String parseId, Long auditId, Long zoneId, Long typeId,Long formId,
                          String belongsTo, String dataType,
                          String key, String valueString, Integer valueInt, Double valueDouble, Date created, Date updated, Boolean isUpdated){
        try {
            featureData.put("userName", username);
            featureData.put("id", id);
            featureData.put("parseId", parseId);
            featureData.put("auditId", auditId);
            featureData.put("zoneId", zoneId);
            featureData.put("typeId", typeId);
            featureData.put("formId", formId);
            featureData.put("belongsTo", belongsTo);
            featureData.put("dataType", dataType);
            featureData.put("key", key);
            featureData.put("valueString", valueString);
            featureData.put("valueInt", valueInt);
            featureData.put("valueDouble", valueDouble);

            featureData.put("created", created);
            featureData.put("updated", updated);
            featureData.put("isUpdated", isUpdated);

        } catch (Exception e){
            System.out.println("Error saving json data.");
        }
    }

    public JSONObject getFeatureData() {
        return featureData;
    }

    public void createFeatureAtLocal(){
        String id;
        String username;
        String parseId;
        Long zoneId;
        Long auditId;
        Long typeId;
        Long formId;
        String belongsTo;
        String dataType;
        String key;
        String valueString;
        Integer valueInt;
        Double valueDouble;
        Date created;
        Date updated;
        Boolean isUpdated;

        try{
            id = featureData.get("id").toString();
            username = featureData.get("userName").toString();
            parseId = featureData.get("parseId").toString();
            zoneId = (Long) featureData.get("zoneId");
            auditId = (Long) featureData.get("auditId");
            typeId = (Long) featureData.get("typeId");
            belongsTo = featureData.get("belongsTo").toString();
            dataType = featureData.get("dataType").toString();
            key = featureData.get("key").toString();
            valueString = featureData.get("valueString").toString();
            valueInt = (Integer) featureData.get("valueInt");
            valueDouble = (Double) featureData.get("valueDouble");
            formId = (Long) featureData.get("formId");
            created = (Date) featureData.get("created");
            updated = (Date) featureData.get("updated");
            isUpdated = (Boolean) featureData.get("isUpdated");

            Feature feature = new Feature(username, parseId, auditId, zoneId, typeId, formId,
                    belongsTo, dataType,
                    key, valueString, valueInt, valueDouble, created, updated, isUpdated);
            feature.save();

        } catch (Exception e) {
            System.out.println("Error getting data.");
        }
    }

    public void createFeatureAtParse(){
        ApiHandler.createParseObject(featureData, className);
    }

    public void getFeatureFromParse(String objectId){
        ApiHandler.getParseObjects(featureData, className, objectId);
    }

    public static void updateFeatureAtLocal(String objectId, String id, Boolean isResponse) {
        try{
            Feature feature = (Feature) (FeatureHandler.getFeatureById(id)).get(0);
            feature.setParseId(objectId);
            if(isResponse){
                feature.setIsUpdated(false);
            }
            feature.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void syncAllLocalFeaturesToParse(){
        JSONArray features = FeatureHandler.getAllParseIdNotSetFeatures();
        JSONArray dataArray = new JSONArray();
        for (int i =0; i < features.length(); i++){
            try {
                Feature feature = (Feature) features.get(i);
                FeatureHandler featureHandler = new FeatureHandler(
                        feature.getUsername(),
                        feature.getId().toString(),
                        feature.getParseId(),
                        feature.getAuditId(),
                        feature.getZoneId(),
                        feature.getTypeId(),
                        feature.getFormId(),
                        feature.getBelongsTo(),
                        feature.getDataType(),
                        feature.getKey(),
                        feature.getValueString(),
                        feature.getValueInt(),
                        feature.getValueDouble(),
                        feature.getCreated(),
                        feature.getUpdated(),
                        feature.getIsUpdated()
                );
                // featureHandler.createFeatureAtParse();
                dataArray.put(featureHandler.getFeatureData());
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
        ApiHandler.doBatchOperation(dataArray, className, ApiHandler.getBatchOperationRequestMethod(), false);
    }

    public static JSONArray getFeatureByParseId(String parseid){
        List<Feature> features = Feature.find(Feature.class,"parse_id=?",parseid);
        JSONArray featureArrray = new JSONArray();
        for(int i = 0;i<features.size();i++)
        {
            Feature feature = features.get(i);
            try
            {
                featureArrray.put(feature);
            }
            catch (Exception e){
                System.out.println("Exception occurred while finding zone.");
            }
        }
        return featureArrray;
    }

    public static JSONArray getFeatureById(String id){
        List<Feature> features = Feature.find(Feature.class,"id=?",id);
        JSONArray featureArray = new JSONArray();
        for(int i = 0;i<features.size();i++)
        {
            Feature feature = features.get(i);
            try
            {
                featureArray.put(feature);
            }
            catch (Exception e){
                System.out.println("Type could not be found.");
            }
        }
        return featureArray;
    }

    private static JSONArray getAllParseIdNotSetFeatures(){
        List<Feature> features = Feature.find(Feature.class,"parse_id=?","");
        JSONArray typeArray = new JSONArray();
        for(int i = 0;i<features.size();i++)
        {
            Feature feature = features.get(i);
            try
            {
                typeArray.put(feature);
            }
            catch (Exception e){
                System.out.println("Zone could not be found.");
            }
        }
        return typeArray;
    }

    public static void syncAllFeaturesFromParse(String username) {
        try{
            JSONObject query = new JSONObject();
            JSONObject parameter = new JSONObject();
            parameter.put("userName", username);
            query.put("where", parameter);
            ApiHandler.getParseObjects(query, className, "");

        } catch (Exception e) {
            Log.e("FeatureHandler", "Exception while creating where query for request.",e);
        }
    }

    public static void saveOrUpdateFeatureFromParse(JSONObject jsonData) {
        Feature feature;
        try{
            String parseId =  jsonData.get("objectId").toString();
            feature = (Feature) FeatureHandler.getFeatureByParseId(parseId).get(0);
        } catch (Exception e) {
            feature = new Feature();
        }

        try {
            feature.setId(Long.valueOf(jsonData.get("id").toString()));
            feature.setAuditId(Long.valueOf(jsonData.get("auditId").toString()));
            feature.setParseId(jsonData.get("objectId").toString());
            feature.setUsername(jsonData.get("userName").toString());

            feature.setFormId(Long.valueOf(jsonData.get("formId").toString()));
            feature.setZoneId(Long.valueOf(jsonData.get("zoneId").toString()));
            feature.setTypeId(Long.valueOf(jsonData.get("typeId").toString()));
            feature.setBelongsTo(jsonData.get("belongsTo").toString());
            feature.setDataType(jsonData.get("dataType").toString());

            feature.setKey(jsonData.get("key").toString());
            feature.setValueString(jsonData.get("valueString").toString());
            try{
                feature.setValueInt((Integer) jsonData.get("valueInt"));
            } catch (Exception e) {}

            try {
                feature.setValueDouble((Double) jsonData.get("valueDouble"));
            } catch (Exception e) {}

            try{
                feature.setCreated(new Date(jsonData.get("created").toString()));
            } catch (Exception e) {
                feature.setCreated(new Date());
            }
            try {
                feature.setUpdated(new Date(jsonData.get("updated").toString()));
            } catch (Exception e) {
                feature.setUpdated(new Date());
            }
            feature.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    private static JSONArray getAllRecentlyUpdatedFeatures(){
        List<Feature> features = Feature.find(Feature.class,"parse_id is not NULL and parse_id !='' and is_updated=1","");
        JSONArray featureArray = new JSONArray();
        for(int i = 0;i<features.size();i++)
        {
            Feature feature = features.get(i);
            try
            {
                featureArray.put(feature);
            }
            catch (Exception e){
                System.out.println("Zone could not be found.");
            }
        }
        return featureArray;
    }

    public static void saveAllFeatureChangesToParse() {
        JSONArray features = FeatureHandler.getAllRecentlyUpdatedFeatures();
        JSONArray data = new JSONArray();
        for (int i =0; i < features.length(); i++){
            try {
                Feature feature = (Feature) features.get(i);
                FeatureHandler featureHandler = new FeatureHandler(
                        feature.getUsername(),
                        feature.getId().toString(),
                        feature.getParseId(),
                        feature.getAuditId(),
                        feature.getZoneId(),
                        feature.getTypeId(),
                        feature.getFormId(),
                        feature.getBelongsTo(),
                        feature.getDataType(),
                        feature.getKey(),
                        feature.getValueString(),
                        feature.getValueInt(),
                        feature.getValueDouble(),
                        feature.getCreated(),
                        feature.getUpdated(),
                        feature.getIsUpdated()
                );
                data.put(featureHandler.getFeatureData());
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
        ApiHandler.doBatchOperation(data, className, ApiHandler.getBatchOperationRequestMethod(), true);
    }
}
