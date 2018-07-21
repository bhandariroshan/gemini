package com.example.rbhandari.datasyncapplication.requesthandler;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RequestHandler extends AsyncTask <String,Void,JSONObject>{
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private JSONObject jsonData;
    private String appId;
    private String apiKey;
    private String url;
    private String method;
    private OnEventListener<JSONObject> mCallBack;
    public Exception mException;

    public RequestHandler(){

    }

    public RequestHandler(JSONObject jsonData, String appId,
                          String apiKey, String url, String method,
                          OnEventListener callback){
        this.jsonData=jsonData;
        this.apiKey=apiKey;
        this.appId=appId;
        this.url=url;
        this.method=method;
        this.mCallBack=callback;

    }

    @Override
    public JSONObject doInBackground(String... params) {
        try{
            JSONObject response;
            RequestHandler example = this;
            if (this.method.toLowerCase().equals("get")){
                response = example.get(url);

            }
            else if (this.method.toLowerCase().equals("post")){
                response = example.post(this.jsonData, this.appId, this.apiKey, this.url);
            }
            else if (this.method.toLowerCase().equals("delete")){
                response = example.delete(this.appId, this.apiKey, this.url);
            }
            else {
                // For put request
                response = example.put(this.jsonData, this.appId, this.apiKey, this.url);
            }
            return response;
        } catch (Exception e){
            Log.e("RequestHandler", "Error while creating background request.", e);
            return new JSONObject();
        }
    }

    protected void onPostExecute(JSONObject result) {
        if (mCallBack != null) {
            if (mException == null) {
                mCallBack.onSuccess(result);
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }

    private JSONObject get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Parse-Application-Id",appId)
                .addHeader("X-Parse-REST-API-Key",apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonResponse =  new JSONObject();
            try{
                jsonResponse.put("status", "success");
                jsonResponse.put("response", response.body().string());
            } catch (Exception e) {
                Log.e("RequestHandler", "Error while creating json data in get request.", e);
            }
            return jsonResponse;
        }
        catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing get.");
            } catch (Exception ex) {
                Log.e("RequestHandler", "Error while error data in get request.", e);
            }
            return exceptionResponse;
        }
    }

    private JSONObject post(JSONObject data, String appId, String apiKey, String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, data.toString());
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Parse-Application-Id",appId)
                .addHeader("X-Parse-REST-API-Key",apiKey)
                .post(body)
                .build();
        JSONObject returnJSON = new JSONObject();
        try  {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            try{
                returnJSON.put("status", "success");
                returnJSON.put("response", responseString);
            } catch (Exception e){
                Log.e("RequestHandler", "Error while creating data for post request.", e);
            }

            return returnJSON;
        } catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing post.");
            } catch (Exception ex) {
                Log.e("RequestHandler", "Error while creating data for exception in post request.", e);
            }
            return exceptionResponse;
        }
    }

    private JSONObject delete(String appId, String apiKey, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Parse-Application-Id",appId)
                .addHeader("X-Parse-REST-API-Key",apiKey)
                .delete()
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONObject returnJSON = new JSONObject();
            try{
                returnJSON.put("status", "success");
                returnJSON.put("response", responseString);
            }catch (Exception e){
                Log.e("RequestHandler", "Error while creating data for delete request.", e);
            }
            return returnJSON;
        } catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing delete.");
            } catch (Exception ex) {
                Log.e("RequestHandler", "Error while creating data for exception in delete request.", e);
            }
            return exceptionResponse;
        }
    }

    private JSONObject put(JSONObject data, String appId, String apiKey, String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, data.toString());
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Parse-Application-Id",appId)
                .addHeader("X-Parse-REST-API-Key",apiKey)
                .put(body)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONObject returnJSON = new JSONObject();
            try{
                returnJSON.put("status", "success");
                returnJSON.put("response", responseString);
            }catch (Exception e){
                Log.e("RequestHandler", "Error while creating data for put request.", e);
            }
            return returnJSON;
        } catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing put.");
            } catch (Exception ex) {
                Log.e("RequestHandler", "Error while creating data for exception in put request.", e);
            }
            return exceptionResponse;
        }
    }
}
