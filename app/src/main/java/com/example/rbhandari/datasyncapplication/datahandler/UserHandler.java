package com.example.rbhandari.datasyncapplication.datahandler;

import android.util.Log;

import com.example.rbhandari.datasyncapplication.datamodels.User;
import com.example.rbhandari.datasyncapplication.requesthandler.RequestHandler;
import com.example.rbhandari.datasyncapplication.requesthandler.ApiHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserHandler {

    private static String userClassName = "User";
    private JSONObject userData = new JSONObject();

    private static boolean checkUserExists(String username) {
        JSONArray userArray = getUserByUsername(username);
        return userArray.length()>0;
    }

    public UserHandler(String username, String password, String email,
                       String first_name, String last_name, String phone){
        try {
            userData.put("username", username);
            userData.put("password", password);
            userData.put("first_name", first_name);
            userData.put("email",email);
            userData.put("phone",phone);
            userData.put("last_name", last_name);
        }catch (JSONException e){
            Log.e("UserHandler", "Exception occurred while setting user data.", e);
        }
    }

    public static JSONArray getOneUser() {
        List<User> users = User.find(User.class,"");
        JSONArray userArray = new JSONArray();
        for(int i = 0;i<users.size();i++)
        {
            User user = users.get(i);
            try
            {
                userArray.put(user);
            }
            catch (Exception e){
                Log.e("UserHandler", "Exception occurred while getting user in getUserByUsername.", e);
            }
        }
        return userArray;
    }

    public void createLocalUser(){
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
        } catch (Exception e) {
            System.out.println("Exception Occurrred");
        }

        if(!UserHandler.checkUserExists(username)){
            User user = new User("", username, email,
                    first_name, last_name, password, phone);
            user.save();
        }
    }

    public JSONObject getUserData() {
        return userData;
    }

    private void createUserParse(){
        ApiHandler.createParseObject(userData, userClassName);
    }

    public void getUserFromParse(String objectId){
        ApiHandler.getParseObjects(userData, userClassName, objectId);
    }

    public static void updateLocalUser(String objectId, String username) {
        try{
            User user = (User) (UserHandler.getUserByUsername(username)).get(0);
            user.setParseid(objectId);
            user.save();
        } catch (Exception e) {
            Log.e("UserHandler", "Exception occurred while updating user.", e);
        }
    }

    public static void createAllLocalUsersToParse(){
        JSONArray users = UserHandler.getAllParseIdNotSetUsers();
        JSONArray data = new JSONArray();
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
                data.put(userHandler.getUserData());
            } catch (Exception e){
                Log.e("UserHandler", "Exception occurred while creating json data.", e);
            }
        }
        ApiHandler.doBatchOperation(data, userClassName, ApiHandler.getBatchOperationRequestMethod());
    }

    public static JSONArray getUserByParseId(String parseid){
        List<User> users = User.find(User.class,"parseid=?",parseid);
        JSONArray userArray = new JSONArray();
        for(int i = 0;i<users.size();i++)
        {
            User user = users.get(i);
            try
            {
                userArray.put(user);

            }
            catch (Exception e){
                Log.e("UserHandler", "Exception occurred while getting user in getUserByParseId.", e);
            }
        }
        return userArray;
    }

    public static JSONArray getUserByUsername(String username){
        List<User> users = User.find(User.class,"username=?",username);
        JSONArray userArray = new JSONArray();
        for(int i = 0;i<users.size();i++)
        {
            User user = users.get(i);
            try
            {
                userArray.put(user);
            }
            catch (Exception e){
                Log.e("UserHandler", "Exception occurred while getting user in getUserByUsername.", e);
            }
        }
        return userArray;
    }

    public static void syncUserFromParse(String username){
        try{
            JSONObject query = new JSONObject();
            JSONObject parameter = new JSONObject();
            parameter.put("username", username);
            query.put("where", parameter);
            ApiHandler.getParseObjects(query, userClassName, "");

        } catch (Exception e) {
            Log.e("UserHandler", "Exception while creating where query for request.",e);
        }
    }

    private static JSONArray getAllParseIdNotSetUsers(){
        List<User> users = User.find(User.class,"parseid=?","");
        JSONArray userArray = new JSONArray();
        for(int i = 0;i<users.size();i++)
        {
            User user = users.get(i);
            try
            {
                userArray.put(user);
            }
            catch (Exception e){
                Log.e("UserHandler", "Exception occurred while getting user in getAllParseIdNotSetUsers.", e);
            }
        }
        return userArray;
    }

    public static void saveOrUpdateUserFromParse(JSONObject userData) {
        User user;
        try{
            String username =  userData.get("objectId").toString();
            user = (User) UserHandler.getUserByUsername(username).get(0);
        } catch (Exception e) {
            user = new User();
        }

        try {
            user.setUsername(userData.get("username").toString());
            user.setParseid(userData.get("objectId").toString());
            user.setEmail(userData.get("email").toString());
            user.setLastName(userData.get("last_name").toString());
            user.setFirstName(userData.get("first_name").toString());
            user.setPassword(userData.get("password").toString());
            user.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
