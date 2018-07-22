package com.example.rbhandari.datasyncapplication.datahandler;

import android.os.AsyncTask;

import org.json.JSONObject;

public class SyncHandler extends AsyncTask<String,Void,String>{
    @Override
    public String doInBackground(String... params) {
        return "";
    }

    public static String createBackupToParse() {
        return "Scuccessfully completed sync";
    }

    public static String loadBackupFromParse(){
        return "Scuccessfully completed backup";
    }
}
