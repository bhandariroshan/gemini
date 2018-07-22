package com.example.rbhandari.datasyncapplication.datahandler;

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

    public FeatureHandler(String id, String parseId, Long auditId, Long zoneId, Long typeId,Long formId,
                          String belongsTo, String dataType,
                          String key, String valueString, Integer valueInt, Double valueDouble, Date created, Date updated){
        try {
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

        } catch (Exception e){
            System.out.println("Error saving json data.");
        }
    }

    public JSONObject getFeatureData() {
        return featureData;
    }

    public void createFeatureAtLocal(){
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

        try{
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

            Feature feature = new Feature(parseId, auditId, zoneId, typeId, formId,
                    belongsTo, dataType,
                    key, valueString, valueInt, valueDouble, created, updated);
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

    public static void updateFeatureAtLocal(String objectId, String id) {
        try{
            Feature feature = (Feature) (FeatureHandler.getFeatureById(id)).get(0);
            feature.setParseId(objectId);
            feature.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void saveAllLocalFeaturesToParse(){
        JSONArray features = FeatureHandler.getAllParseIdNotSetFeatures();
        JSONArray dataArray = new JSONArray();
        for (int i =0; i < features.length(); i++){
            try {
                Feature feature = (Feature) features.get(i);
                FeatureHandler featureHandler = new FeatureHandler(
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
                        feature.getUpdated()
                );
                // featureHandler.createFeatureAtParse();
                dataArray.put(featureHandler.getFeatureData());
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
        ApiHandler.doBatchOperation(dataArray, className, ApiHandler.getBatchOperationRequestMethod());
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

}
