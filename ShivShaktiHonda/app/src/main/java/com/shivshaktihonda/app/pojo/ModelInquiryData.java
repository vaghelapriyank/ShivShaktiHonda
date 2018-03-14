package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelInquiryData implements Serializable {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("inquery_id")
    public String inquery_id;
    @SerializedName("salesperson_name")
    public String salesperson_name;
    @SerializedName("model_id")
    public String model_id;
    @SerializedName("model_name")
    public String model_name;
    @SerializedName("model_type_name")
    public String model_type_name;
    @SerializedName("model_type_id")
    public String model_type_id;

    @SerializedName("first_name")
    public String first_name;
    @SerializedName("middle_name")
    public String middle_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("address")
    public String address;

    @SerializedName("tahsil")
    public String tahsil;
    @SerializedName("distict")
    public String distict;
    @SerializedName("vehicle_type")
    public String vehicle_type;
    @SerializedName("color")
    public String color;

    @SerializedName("inquiry_type")
    public String inquiry_type;
    @SerializedName("customer_type")
    public String customer_type;
    @SerializedName("purchase_type")
    public String purchase_type;
    @SerializedName("feedback")
    public String feedback;
    @SerializedName("created_on")
    public String created_on;
    @SerializedName("updated_on")
    public String updated_on;
}
