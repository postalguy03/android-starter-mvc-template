package cct.github.droid.android_mvc_sample.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

import cct.github.droid.android_mvc_sample.businessObjects.Const;

public class JSONParser {

    static InputStream is = null;
    private JSONObject _json = null;
    final static ObjectMapper mapper = new ObjectMapper();

    // constructor
    public JSONParser() {

    }
    public void addParameters(JSONObject json){
        _json = json;
    }
    //Return the RAW Json from the request
    public JsonObject getJsonPostFromUrl(String url) {
        JsonObject ret = null;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Const.APP_ENDPOINT + url);
            httpPost.setEntity(new StringEntity(_json.toString(), HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JsonParser parser = new JsonParser();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            JsonElement element = parser.parse(reader);
            if (element.isJsonObject())
                ret = element.getAsJsonObject();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return ret;

    }
    public JsonObject getJsonGetFromUrl(String url) {
        JsonObject ret = null;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpGet = new HttpPost(Const.APP_ENDPOINT + url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JsonParser parser = new JsonParser();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            JsonElement element = parser.parse(reader);
            if (element.isJsonObject())
                ret = element.getAsJsonObject();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return ret;

    }

    //Post request - ObjectMapper to cast to your Entity/Model
    public <T> T getPostObjFromUrl(String url) {

        T data = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Const.APP_ENDPOINT + url);
            StringEntity entity = new StringEntity(_json.toString(), HTTP.UTF_8);
            //The contentType may have to be modified based on your servers recieving methods & encoding
            //entity.setContentType("application/json; charset=UTF-8");
            //entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;");
            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity ent = httpResponse.getEntity();
            if (ent != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ent.getContent(), "UTF-8"));
                data = fromJSON(new TypeReference<T>() {}, reader);
                if (reader != null)
                    reader.close();
                reader = null;
                ent.consumeContent();
            }

        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
    //Get request - ObjectMapper to cast to your Entity/Model
    public <T> T getGetObjFromUrl(String url) {

        T data = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Const.APP_ENDPOINT + url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity ent = httpResponse.getEntity();
            if (ent != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ent.getContent(), "UTF-8"));
                data = fromJSON(new TypeReference<T>() { }, reader);
                if (reader != null)
                    reader.close();
                reader = null;
                ent.consumeContent();
            }

        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static <T> T fromJSON(final TypeReference<T> type, final BufferedReader jsonPacket) {
        T data = null;

        try {
            data = mapper.readValue(jsonPacket, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }

}