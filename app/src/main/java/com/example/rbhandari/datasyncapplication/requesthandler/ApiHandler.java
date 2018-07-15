package com.example.rbhandari.datasyncapplication.requesthandler;

import org.json.JSONObject;
import java.io.IOException;

public class ApiHandler {
    private String parseApiRoot = "http://ec2-18-220-200-115.us-east-2.compute.amazonaws.com:80/parse/";
    private String applicationId = "47f916f7005d19ddd78a6be6b4bdba3ca49615a0";
    private String masterKey = "NLI214vDqkoFTJSTtIE2xLqMme6Evd0kA1BbJ20S";
    private String classPath = "classes/";

    public void createParseObject(JSONObject data, String classname){
        String userCreateUrl = this.parseApiRoot + this.classPath + classname;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    data, this.applicationId, this.masterKey, userCreateUrl, "post", classname);
            requestHandler.run();

        } catch (IOException e){
            System.out.println("Exception Occurred.");
        }
    }

    public void getParseObject(JSONObject data, String classname, String objectId){
        String userCreateUrl = this.parseApiRoot + this.classPath + classname + "/" + objectId;

        try{
            RequestHandler requestHandler = new RequestHandler(
                    data, applicationId, masterKey, userCreateUrl, "get", classname);
            requestHandler.run();

        } catch (IOException e){
            System.out.println("Exception Occurred.");
        }
    }
}