package com.example.rbhandari.datasyncapplication.datahandler;

import android.os.AsyncTask;

import com.example.rbhandari.datasyncapplication.datamodels.TypeClass;
import com.example.rbhandari.datasyncapplication.datamodels.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyncHandler extends AsyncTask<String,Void,String>{
    private String action = "";

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String doInBackground(String... params) {

        if (action.toLowerCase().equals("createbackuptoparse")){
            createBackupToParse();
        } else {
            loadBackupFromParse();
        }
        return "";
    }

    public static String createBackupToParse() {
        try{
            AuditHandler.syncAllLocalAuditToParse();
            ZoneHandler.syncAllLocalZonesToParse();
            TypeHandler.syncAllLocalTypesToParse();
            FeatureHandler.syncAllLocalFeaturesToParse();
            return "Successfully completed sync";
        } catch (Exception e) {
            return "Exception occurred while syncing data to server.";
        }
    }

    public static String loadBackupFromParse(){
        JSONArray users = UserHandler.getOneUser();
        try{
            String username = ((User) users.get(0)).getUsername();
            AuditHandler.syncAllUserAuditsFromParse(username); // correct
            ZoneHandler.syncAllZonesFromParse(username); // correct
            TypeHandler.syncAllTypesFromParse(username); //correct
            FeatureHandler.syncAllFeaturesFromParse(username);
            return "Successfully completed backup";
        } catch (Exception e) {
            return "Exception occurred while creating backup";
        }
    }

    public static String backAllUpdatesToParse(){
        return "";
    }
}
