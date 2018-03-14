package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelUserData {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("user_type")
    public String user_type;
    @SerializedName("name")
    public String name;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("login_datetime")
    public String login_datetime;
}
