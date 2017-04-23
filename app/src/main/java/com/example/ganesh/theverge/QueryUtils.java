package com.example.ganesh.theverge;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {

        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        //If the url is null , then return early
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.e(LOG_TAG, "http response :" + urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON result.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    public static ArrayList<TheVergePojo> extractTheVergeDetails(String theVergeJson) {
        if (TextUtils.isEmpty(theVergeJson)) {
            return null;
        }
        ArrayList<TheVergePojo> theVergePojos = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(theVergeJson);
            JSONArray currentJsonResponseFromArray = baseJsonResponse.getJSONArray("articles");
            for (int i = 0; i < currentJsonResponseFromArray.length(); i++) {
                JSONObject theVergeObject = currentJsonResponseFromArray.getJSONObject(i);

                String author = theVergeObject.getString("author");

                String title = theVergeObject.getString("title");

                String description = theVergeObject.getString("description");

                String urlToImage = theVergeObject.getString("urlToImage");

                String url = theVergeObject.getString("url");


                TheVergePojo theVergePojo = new TheVergePojo(urlToImage, author, title, description, url);
                theVergePojos.add(theVergePojo);
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON response!");

        }
        return theVergePojos;
    }

    public static List<TheVergePojo> fetchTheVergeData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<TheVergePojo> theVergePojos = extractTheVergeDetails(jsonResponse);

        return theVergePojos;
    }


}
