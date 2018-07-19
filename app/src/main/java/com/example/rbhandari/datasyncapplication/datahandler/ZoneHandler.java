package com.example.rbhandari.datasyncapplication.datahandler;

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

    public ZoneHandler(String id, String parseId, String name, String type, Long auditId, Date created, Date updated){
        try {
            zoneData.put("id", id);
            zoneData.put("parseId",parseId);
            zoneData.put("name", name);
            zoneData.put("type", type);
            zoneData.put("auditId", auditId);
            zoneData.put("created", created);
            zoneData.put("updated", updated);

        }catch (JSONException e){
            System.out.println("Exception Occurred in Constructor.");
        }
    }

    public void createLocalZone(){
        String parseId="";
        String id = "";
        String name = "";
        String type="";
        Long auditId;
        Date created;
        Date updated;

        try{
            parseId = zoneData.get("parseId").toString();
            name = zoneData.get("name").toString();
            type = zoneData.get("type").toString();
            auditId = (Long) zoneData.get("auditId");
            created = (Date) zoneData.get("created");
            updated = (Date) zoneData.get("updated");

            Zone zone = new Zone(parseId, name, type, auditId, created, updated);
            zone.save();

        } catch (Exception e) {
            System.out.println("Error getting data.");
        }
    }

    public void createZoneParse(){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createParseObject(zoneData, className);
    }

    public void getZoneFromParse(String objectId){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getParseObject(zoneData, className, objectId);
    }

    public static void updateLocalZone(String objectId, String id) {
        try{
            Zone zone = (Zone) (ZoneHandler.getZoneById(id)).get(0);
            zone.setParseId(objectId);
            zone.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void saveAllLocalZonesToParse(){
        JSONArray zones = ZoneHandler.getAllParseIdNotSetZones();
        for (int i =0; i < zones.length(); i++){
            try {
                Zone zone = (Zone) zones.get(i);
                ZoneHandler zoneHandler = new ZoneHandler(
                        zone.getId().toString(),
                        zone.getParseId(),
                        zone.getName(),
                        zone.getType(),
                        zone.getAuditId(),
                        zone.getCreated(),
                        zone.getUpdated()
                );
                zoneHandler.createZoneParse();
            } catch (Exception e){
                System.out.println("Exception occurred while saving.");
            }
        }
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
                System.out.println("Exception occurred while finding zone.");
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
                System.out.println("Zone could not be found.");
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
                System.out.println("Zone could not be found.");
            }
        }
        return zoneArray;
    }
}
