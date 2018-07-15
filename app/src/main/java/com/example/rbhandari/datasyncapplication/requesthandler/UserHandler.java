package com.example.rbhandari.datasyncapplication.requesthandler;

import com.example.rbhandari.datasyncapplication.datamodels.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserHandler {

    private static String userClassName = "User";
    private static JSONObject userData = new JSONObject();

    public UserHandler(String username, String password, String email,
                       String first_name, String last_name, String phone){
        try {
            userData.put("username", username);
            userData.put("password", password);
            userData.put("first_name", first_name);
            userData.put("last_name", last_name);
        }catch (JSONException e){}
    }

    public String createLocalUser(){
        String username = "";
        String email="";
        String first_name="";
        String last_name="";
        String password="";
        String phone="";

        try{
            username = userData.get("username").toString();
            email = userData.get("email").toString();
            first_name = userData.get("first_name").toString();
            last_name = userData.get("last_name").toString();
            password = userData.get("password").toString();
            phone = userData.get("phone").toString();
        } catch (Exception e) {}
        if(!User.checkUserExists(username)){
            User user = new User("", username, email,
                    first_name, last_name, password, phone);
            user.save();
        }
        return "";
    }

    public void createUserParse(){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createParseObject(userData, userClassName);
    }

    public void getUserFromParse(String objectId){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getParseObject(userData, userClassName, objectId);
    }

    public static void updateLocalUser(String objectId, String username) {
        try{
            User user = (User) (User.getUserByUsername(username)).get(0);
            user.setParseid(objectId);
            user.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void createAllLocalUsersToParse(){
        JSONArray users = User.getAllParseIdNotSetUsers();
        for (int i =0; i < users.length(); i++){
            JSONObject jsonObject = new JSONObject();
            try {
                User user = (User) users.get(i);
                UserHandler userHandler = new UserHandler(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone()
                 );
                userHandler.createUserParse();
            } catch (Exception e){}

        }
    }
}
