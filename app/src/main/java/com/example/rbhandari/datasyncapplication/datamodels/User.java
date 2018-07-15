package com.example.rbhandari.datasyncapplication.datamodels;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class User extends SugarRecord<User> {
    private String parseid;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;

    public User(){
    }

    public User(String parseid, String username, String email,
                String firstName, String lastName, String password, String phone){
        this.parseid = parseid;
        this.username = username;
        this.email = email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.password=password;
        this.phone=phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setParseid(String parseid) {
        this.parseid = parseid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParseId() {
        return parseid;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
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
            catch (Exception e){}
        }
        return userArray;
    }

    public static JSONArray getAllParseIdNotSetUsers(){
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

    public static boolean checkUserExists(String username) {
        JSONArray userArray = getUserByUsername(username);
        if (userArray.length()>0){
            return true;
        }
        return false;
    }
}

