package com.reddyz.newz;

import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reddy on 20-Feb-17.
 *
 * Helper methods related to requesting and receiving News data from NewsAPI.org.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the NewsAPI thru input urlString and return a {@link List<NewsData>} list object .
     */
    public static List<NewsData> fetchNews(String urlString) {
        Log.i(LOG_TAG, "fetchNews: urlString ::" + urlString);
        // Enable addDelay() to check progress bar feature.
        //addDelay(4000);

        //create url object
        URL url = createUrl(urlString);

        //request for relevant news info(json format) through the url info.
        String jsonResponse = makeHttpRequest(url);

        //Parse Json formatted response and extract relevant info into List of NewsData objects.
        List<NewsData> newsList = extractArticlesFromJson(jsonResponse);

        return newsList;

    }

    /**
     * Sleep for specified input milliseconds.
     */
    private static void addDelay(int delayMilliSeconds) {
        try {
            Thread.sleep(delayMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "createUrl : MalformedURLException");
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if(url == null) {
            Log.e(LOG_TAG, "makeHttpRequest : url is NULL");
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200) {
                inStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inStream);
            } else {
                Log.e(LOG_TAG, "makeHttpRequest : urlConnection Response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "makeHttpRequest: urlConnection -> IOException");
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "makeHttpRequest: inputStream -> IOException");
                }
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the whole JSON response from the server.
     */
    private static String readFromStream(InputStream inStream) {
        StringBuilder output = new StringBuilder();
        if(inStream != null) {
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inStreamReader);
            String line = null;
            try {
                line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "readFromStream: readLine -> IOException");
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewsData} objects that has been built up from parsing a JSON response.
     */
    private static List<NewsData> extractArticlesFromJson(String jsonResponse) {
        if(jsonResponse.isEmpty()) {
            Log.e(LOG_TAG, "extractArticlesFromJson: jsonResponse is empty");
            return null;
        }

        List<NewsData> newsList = new ArrayList();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray articlesArray = baseJsonResponse.getJSONArray("articles");

            for(int i = 0; i < articlesArray.length(); i++) {
                JSONObject article = articlesArray.getJSONObject(i);
                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");
                String dateTime = article.getString("publishedAt");

                Log.i(LOG_TAG, "Json parse: " + author +" ,"+title+" ,"+description);
                NewsData data = new NewsData(author, title, description, url, urlToImage, dateTime);
                newsList.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return newsList;
    }
}
