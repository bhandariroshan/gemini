package com.example.rbhandari.datasyncapplication.datahandler;

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
            System.out.println("Exception occurred.");
        }
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

    private void createUserParse(){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.createParseObject(userData, userClassName);
    }

    public void getUserFromParse(String objectId){
        ApiHandler apiHandler = new ApiHandler();
        apiHandler.getParseObject(userData, userClassName, objectId);
    }

    public static void updateLocalUser(String objectId, String username) {
        try{
            User user = (User) (UserHandler.getUserByUsername(username)).get(0);
            user.setParseid(objectId);
            user.save();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void createAllLocalUsersToParse(){
        JSONArray users = UserHandler.getAllParseIdNotSetUsers();
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
            catch (Exception e){}
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
                System.out.println("Exception occurred");
            }
        }
        return userArray;
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
            catch (Exception e){}
        }
        return userArray;
    }
}
