package cct.github.droid.android_mvc_sample.businessObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;


import java.io.ByteArrayOutputStream;

public class ImageFunctions {

    public Bitmap giveRoundedSquarePic(Bitmap bitmap, int mapIconSize) {


        bitmap =  Bitmap.createScaledBitmap(bitmap, mapIconSize, mapIconSize, true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(h / 2, w / 2);
        Bitmap output =  Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Paint p = new Paint();
        p.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);

        c.drawRect(0, 0, (float) mapIconSize, (float) mapIconSize,  p);
        //c.drawRoundRect(0, 0, (float)mapIconSize, (float)mapIconSize, 10, 10, p);

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 0, 0, p);

        return output;
    }
    public Bitmap giveSquarePicMaxWidth(Bitmap bitmap, int maxWidth) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        // if (height > width) {
        //     height = maxWidth;
        //     width = (width * maxWidth) / height;
        // } else
        if (width != height) {
            height = (height * maxWidth) / width;
            width = maxWidth;
        } else  {
            height = maxWidth;
            width = maxWidth;
        }

        bitmap =  Bitmap.createScaledBitmap(bitmap, width, height, true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(h / 2, w / 2);
        Bitmap output =  Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Paint p = new Paint();
        p.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);

        c.drawRect(0, 0, (float) maxWidth, (float) height,  p);
        //c.drawRoundRect(0, 0, (float)mapIconSize, (float)mapIconSize, 10, 10, p);

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 0, 0, p);

        return output;
    }

    public Bitmap giveCirclePic(Bitmap _bitmap, int mapIconSize) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        _bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bytes.toByteArray(), 0, bytes.toByteArray().length), mapIconSize, mapIconSize, true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(w / 2, w / 2);
        Bitmap output =  Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888);

        Paint p = new Paint();
        p.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);

        c.drawCircle((w / 2), (w / 2), radius, p);

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 0, 0, p);

        return output;
    }

    public Bitmap giveCirclePicFromByte(byte[] bytes, int mapIconSize) {

        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length), mapIconSize, mapIconSize, true);

        bitmap =  Bitmap.createScaledBitmap(bitmap, mapIconSize, mapIconSize, true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(h / 2, w / 2);
        Bitmap output =  Bitmap.createBitmap(w, h , Bitmap.Config.ARGB_8888);

        Paint p = new Paint();
        p.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);

        c.drawCircle((w / 2), (h / 2), radius, p);

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 0, 0, p);

        bitmap.recycle();
        return output;
    }

}
