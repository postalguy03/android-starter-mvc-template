package cct.github.droid.android_mvc_sample;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ApplicationTestCase;
import android.widget.ImageView;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;

import cct.github.droid.android_mvc_sample.businessObjects.ImageFunctions;


public class ImageTests extends ApplicationTestCase<Application> {
    public ImageTests() {
        super(Application.class);
    }

    ImageFunctions imageFunctions;

    // JUnit Setup
    protected void setUp(){

    }

    // Testing the Image Scaling version 1
    public void testImageScaling_toSquare(){

        // Load our sample Bitmap from a resource file (embeded in the app under "Drawable"
        Bitmap bm = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.happygilmore);

        Bitmap bmfinal = imageFunctions.giveRoundedSquarePic(bm, 100);

        Assert.assertTrue(bmfinal != null);
        Assert.assertTrue(bmfinal.getWidth() == 100);

    }

    // Testing the Image Scaling version 2
    public void testImageScaling_toCircle() {
        // Load our sample Bitmap from a resource file (embeded in the app under "Drawable"
        Bitmap bm = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.happygilmore);

        Bitmap bmfinal = imageFunctions.giveCirclePic(bm, 100);

        Assert.assertTrue(bmfinal != null);
        Assert.assertTrue(bmfinal.getWidth() == 100);

    }

    // Testing the Image Scaling version 3
    public void testImageScaling_toSquare_FromByteArray() {
        // Load our sample Bitmap from a resource file (embeded in the app under "Drawable") then convert to byte array
        Bitmap bm = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.happygilmore);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bitmap bmfinal = imageFunctions.giveCirclePicFromByte(byteArray, 100);

        Assert.assertTrue(bmfinal != null);
        Assert.assertTrue(bmfinal.getWidth() == 100);

    }



}