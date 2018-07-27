package com.example.rbhandari.datasyncapplication.datahandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datamodels.Audit;
import com.example.rbhandari.datasyncapplication.datamodels.User;
import com.example.rbhandari.datasyncapplication.datamodels.Zone;
import com.example.rbhandari.datasyncapplication.requesthandler.ApiHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ZoneHandler {
    private static String className = "ZoneMain";
    private JSONObject zoneData = new JSONObject();

    private static boolean checkZoneExists(String id) {
        JSONArray userArray = getZoneById(id);
        return userArray.length()>0;
    }

    public ZoneHandler(String id, String username, String parseId, String name, String type, Long auditId, Date created, Date updated){
        try {
            zoneData.put("username", username);
            zoneData.put("id", id);
            zoneData.put("parseId",parseId);
            zoneData.put("name", name);
            zoneData.put("type", type);
            zoneData.put("auditId", auditId);
            zoneData.put("created", created);
            zoneData.put("updated", updated);

        }catch (JSONException e){
            Log.e("ZoneHandler", "Exception occurred while getting setting zone data.", e);
        }
    }

    public JSONObject getZoneData() {
        return zoneData;
    }

    public void createLocalZone(){
        String parseId="";
        String userName="";
        String id = "";
        String name = "";
        String type="";
        Long auditId;
        Date created;
        Date updated;

        try{
            parseId = zoneData.get("parseId").toString();
            userName = zoneData.get("username").toString();
            name = zoneData.get("name").toString();
            type = zoneData.get("type").toString();
            auditId = (Long) zoneData.get("auditId");
            created = (Date) zoneData.get("created");
            updated = (Date) zoneData.get("updated");

            Zone zone = new Zone(parseId, userName, name, type, auditId, created, updated);
            zone.save();

        } catch (Exception e) {
            Log.e("ZoneHandler", "Exception occurred while getting setting zone json data.", e);
        }
    }

    public void createZoneParse(){
        ApiHandler.createParseObject(zoneData, className);
    }

    public void getZoneFromParse(String objectId){
        ApiHandler.getParseObjects(zoneData, className, objectId);
    }

    public static void updateZoneAtLocal(String objectId, String id) {
        try{
            Zone zone = (Zone) (ZoneHandler.getZoneById(id)).get(0);
            zone.setParseId(objectId);
            zone.save();
        } catch (Exception e) {
            Log.e("ZoneHandler", "Exception occurred while updating zone data.", e);
        }
    }

    public static void syncAllLocalZonesToParse(){
        JSONArray zones = ZoneHandler.getAllParseIdNotSetZones();
        JSONArray data = new JSONArray();
        for (int i =0; i < zones.length(); i++){
            try {
                Zone zone = (Zone) zones.get(i);
                ZoneHandler zoneHandler = new ZoneHandler(
                        zone.getId().toString(),
                        zone.getUsername(),
                        zone.getParseId(),
                        zone.getName(),
                        zone.getType(),
                        zone.getAuditId(),
                        zone.getCreated(),
                        zone.getUpdated()
                );
                data.put(zoneHandler.getZoneData());
            } catch (Exception e){
                Log.e("ZoneHandler", "Exception occurred while getting creating json data.", e);
            }
        }
        ApiHandler.doBatchOperation(data, className, ApiHandler.getBatchOperationRequestMethod());
    }

    public static JSONArray getZoneByParseId(String parseid){
        List<Zone> zones = Zone.find(Zone.class,"parse_id=?",parseid);
        JSONArray zoneArray = new JSONArray();
        for(int i = 0;i<zones.size();i++)
        {
            Zone zone = zones.get(i);
            try
            {
                zoneArray.put(zone);

            }
            catch (Exception e){
                Log.e("ZoneHandler", "Exception occurred while getting getting zone data.", e);
            }
        }
        return zoneArray;
    }

    public static JSONArray getZoneById(String id){
        List<Zone> zones = Zone.find(Zone.class,"id=?",id);
        JSONArray zoneArray = new JSONArray();
        for(int i = 0;i<zones.size();i++)
        {
            Zone zone = zones.get(i);
            try
            {
                zoneArray.put(zone);
            }
            catch (Exception e){
                Log.e("ZoneHandler", "Exception occurred while getting getting zone data in getZoneById.", e);
            }
        }
        return zoneArray;
    }

    private static JSONArray getAllParseIdNotSetZones(){
        List<Zone> zones = Zone.find(Zone.class,"parse_id=?","");
        JSONArray zoneArray = new JSONArray();
        for(int i = 0;i<zones.size();i++)
        {
            Zone zone = zones.get(i);
            try
            {
                zoneArray.put(zone);
            }
            catch (Exception e){
                Log.e("ZoneHandler", "Exception occurred while getting getting zone data in getAllParseIdNotSetZones.", e);
            }
        }
        return zoneArray;
    }

    public static void syncAllZonesFromParse(String username) {
        try{
            JSONObject query = new JSONObject();
            JSONObject parameter = new JSONObject();
            parameter.put("username", username);
            query.put("where", parameter);
            ApiHandler.getParseObjects(query, className, "");

        } catch (Exception e) {
            Log.e("ZoneHandler", "Exception while creating where query for request.",e);
        }
    }

    public static void saveOrUpdateZoneFromParse(JSONObject jsonObject) {
        Zone zone;
        try{
            String parseId =  jsonObject.get("objectId").toString();
            zone = (Zone) ZoneHandler.getZoneByParseId(parseId).get(0);
        } catch (Exception e) {
            zone = new Zone();
        }

        try {
            zone.setUsername(jsonObject.get("username").toString());
            zone.setParseId(jsonObject.get("objectId").toString());
            zone.setId((Long) jsonObject.get("zoneId"));
            zone.setAuditId((Long) jsonObject.get("auditId"));

            zone.setName(jsonObject.get("name").toString());
            zone.setType(jsonObject.get("type").toString());
            zone.setCreated((Date) jsonObject.get("created"));
            zone.setUpdated((Date) jsonObject.get("updated"));
            zone.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }

    }
}
