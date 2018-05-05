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

class HttpPostCallbackResult
{
    public int statusCode;
    public JSONObject jsonObj;

    public HttpPostCallbackResult(int statusCode, JSONObject jsonObj){
        this.statusCode = statusCode;
        this.jsonObj = jsonObj;
    }
}

public class HttpPostAsyncTask extends AsyncTask<String, Void, HttpPostCallbackResult>{
    JSONObject postData;
    Callback callback;

    public interface Callback {
        void onPostExecute(HttpPostCallbackResult result);
    }

    public HttpPostAsyncTask(Map<String, String> postData, Callback callback){
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }

        this.callback = callback;
    }

    @Override
    protected HttpPostCallbackResult doInBackground(String... params) {
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
                writer.flush();
                writer.close();
                os.close();
            }

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);
                return new HttpPostCallbackResult(statusCode, new JSONObject(response));
            }
            else {
                Log.d(TAG, Integer.toString(statusCode));
                return new HttpPostCallbackResult(statusCode, null);
            }
        } catch (Exception e){
            Log.d("exception", e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpPostCallbackResult result)
    {
        callback.onPostExecute(result);
    }

    public String convertInputStreamToString (InputStream inputStream){
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