package cct.github.droid.android_mvc_sample.model;

import com.google.gson.annotations.SerializedName;

public class postItemEntity {

    @SerializedName("userId")
    public int userId;

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("body")
    public String body;

}
