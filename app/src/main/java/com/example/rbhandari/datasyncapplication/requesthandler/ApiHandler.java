package com.example.rbhandari.datasyncapplication.requesthandler;

import org.json.JSONObject;

import java.io.IOException;
import java.util.TreeMap;

class ApiHandler extends RequestHandler {
    private String parseApiRoot = "http://ec2-18-220-200-115.us-east-2.compute.amazonaws.com:80/parse/";
    private String applicationId = "47f916f7005d19ddd78a6be6b4bdba3ca49615a0";
    private String masterKey = "NLI214vDqkoFTJSTtIE2xLqMme6Evd0kA1BbJ20S";
    private String classPath = "classes/";

    private String preAuditClassName = "PreAuditMain";
    private String auditClassName = "AuditMain";
    private String zoneClassName = "ZoneMain";
    private String roomClassName = "RoomMain";
    private String plugLoadClassName = "PlugLoadMain";

    public String getApplicationId() {
        return this.applicationId;
    }
    public String getParseApiRoot(){
        return this.parseApiRoot;
    }
    public String getMasterKey() {
        return this.masterKey;
    }
    public String getClassPath() { return this.classPath; }

    public boolean createParseObject(JSONObject data, String classname){
        Boolean returnData;
        String userCreateUrl = this.getParseApiRoot() + this.getClassPath() + classname;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    data, getApplicationId(), getMasterKey(), userCreateUrl, "post", classname);
            requestHandler.run();
            returnData = true;

        } catch (IOException e){
            System.out.println("Exception Occurred.");
            returnData = false;
        }

        return returnData;
    }

    public void getParseObject(JSONObject data, String classname, String objectId){
        String userCreateUrl = this.getParseApiRoot() + this.getClassPath() + classname + "/" + objectId;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    data, getApplicationId(), getMasterKey(), userCreateUrl, "get", classname);
            requestHandler.run();

        } catch (IOException e){
            System.out.println("Exception Occurred.");
        }
    }
}