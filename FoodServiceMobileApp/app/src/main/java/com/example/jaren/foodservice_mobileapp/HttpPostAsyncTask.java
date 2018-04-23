package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static android.content.ContentValues.TAG;

// https://medium.com/@lewisjkl/android-httpurlconnection-with-asynctask-tutorial-7ce5bf0245cd

public class HttpPostAsyncTask extends AsyncTask<String, Void, Void>{
    JSONObject postData;

    public HttpPostAsyncTask(Map<String, String> postData){
        Log.d("tag", "public httpostasync");
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d("tag", "doinbackground");
        try {
            URL url = new URL (params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestMethod("POST");

            if (this.postData != null){
                OutputStream os = urlConnection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(postData.toString());
                Log.d("postdata", "wroteyee");
                writer.flush();
                writer.close();
                os.close();
            }

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200){
                Log.d("statuscode", "success 200");
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);
                JSONObject jsonObj = new JSONObject(response);
            }
            else {
                Log.d("statuscodeno", "nope" + statusCode);
                Log.d(TAG, Integer.toString(statusCode));
            }


        } catch (Exception e){
            Log.d("exception", e.getLocalizedMessage());
        }
        return null;
    }

    public String convertInputStreamToString (InputStream inputStream){
        Log.d("converto", "converted");
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String content;

        try{
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            while ((content = br.readLine()) != null){
                sb.append(content);
            }
            isr.close();
            br.close();
        } catch (IOException e){
            Log.d("converto", e.getLocalizedMessage());
        }

        String mystring = sb.toString();
        return mystring;
    }
}