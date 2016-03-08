package cct.github.droid.android_mvc_sample;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import org.json.JSONObject;

import java.util.List;

import cct.github.droid.android_mvc_sample.businessObjects.Const;
import cct.github.droid.android_mvc_sample.data.JSONParser;
import cct.github.droid.android_mvc_sample.model.postItemEntity;

public class JsonTests extends ApplicationTestCase<Application> {
    public JsonTests() {
        super(Application.class);
    }


    JSONParser jsonParser;

    // JUnit Setup
    protected void setUp(){
        jsonParser = new JSONParser();
    }

    // Testing the population of JSON from our testing endpoint
    public void testPopulateObjectFromJson(){

        //not a very good test as it required an internet connection.
        List<postItemEntity> elist = jsonParser.getGetObjFromUrl(Const.APP_POSTS);
        Assert.assertEquals(elist.size(), 100);

    }

    // Testing the population of JSON and conversion to entity list
    public void testPopulateToEntityList() {
        //not a very good test as it required an internet connection.
        List<postItemEntity> lst = null;
        lst = jsonParser.getGetObjFromUrl(Const.APP_POSTS);

        Assert.assertTrue(lst != null);
        Assert.assertTrue(lst.size() > 0);
    }

    // Testing adding a parameter
    public void testAddParameters() {
        int i = -1;
        JSONObject jsonObject = new JSONObject();

        //Json object requires error handling like so
        try {
            jsonObject.put("val1", 1);
            jsonParser.addParameters(jsonObject);
            i = jsonObject.getInt("val1");
        } catch (Exception ex) {
            //do nothing for now
        }

        Assert.assertEquals(i,1);
    }

}