package com.example.rbhandari.datasyncapplication.requesthandler;

import android.os.AsyncTask;
import android.util.JsonReader;

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
    private String className;

    public RequestHandler(){

    }

    public RequestHandler(JSONObject jsonData, String appId,
                          String apiKey, String url, String method, String className){
        this.jsonData=jsonData;
        this.apiKey=apiKey;
        this.appId=appId;
        this.url=url;
        this.method=method;
        this.className=className;
    }

    @Override
    public JSONObject doInBackground(String... params) {
        try{
            JSONObject response;
            RequestHandler example = this;
            if (this.method != "post"){
                response = example.get(url);
                System.out.println(response);

            }
            else{
                response = example.post(this.jsonData, this.appId, this.apiKey, this.url);
                System.out.println(response);
            }
            return response;
        } catch (Exception e){
            JSONObject exception = new JSONObject();
            return exception;
        }
    }

    protected void onPostExecute(JSONObject result) {
        ResponseHandler.route(jsonData, result, className, method);
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
                JSONObject data = new JSONObject(response.body().string());
                jsonResponse.put("status", "success");
                jsonResponse.put("response", data);
            } catch (Exception e) {}
            return jsonResponse;
        }
        catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing get.");
            } catch (Exception ex) {}
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
        try  {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONObject responseData = new JSONObject(responseString);
            JSONObject returnJSON = new JSONObject();
            try{
                returnJSON.put("status", "success");
                returnJSON.put("response", responseData);
            }catch (Exception e){}
            return returnJSON;
        } catch (Exception e){
            JSONObject exceptionResponse =  new JSONObject();
            try{
                exceptionResponse.put("status","fail");
                exceptionResponse.put("message","Exception occurred while executing post.");
            } catch (Exception ex) {}
            return exceptionResponse;
        }
    }

    public void run()
            throws IOException {
        RequestHandler requestHandler = this;
        requestHandler.execute();
    }
}
