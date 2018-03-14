package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelGetAllSalespersonData implements Serializable {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("salesperson_id")
    public String salesperson_id;
    @SerializedName("name")
    public String name;
    @SerializedName("user_name")
    public String user_name;
    @SerializedName("email")
    public String email;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("password")
    public String password;

}
