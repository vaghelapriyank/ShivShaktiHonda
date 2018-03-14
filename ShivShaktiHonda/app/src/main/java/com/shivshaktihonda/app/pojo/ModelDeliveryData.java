package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelDeliveryData implements Serializable {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("delivery_id")
    public String delivery_id;
    @SerializedName("salesperson_name")
    public String salesperson_name;
    @SerializedName("engin_number")
    public String engin_number;
    @SerializedName("booking_primary_id")
    public String booking_primary_id;
    @SerializedName("inquiry_id")
    public String inquiry_id;
    @SerializedName("model_id")
    public String model_id;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("middle_name")
    public String middle_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("mobile_number")
    public String mobile_number;
    @SerializedName("model_name")
    public String model_name;
    @SerializedName("chesis_number")
    public String chesis_number;
    @SerializedName("battery_number")
    public String battery_number;
    @SerializedName("key_number")
    public String key_number;
    @SerializedName("dc_number")
    public String dc_number;
    @SerializedName("payment_type")
    public String payment_type;

    @SerializedName("booking_amount")
    public String booking_amount;
    @SerializedName("price")
    public String price;
    @SerializedName("total")
    public String total;
    @SerializedName("disc")
    public String disc;
    @SerializedName("diff")
    public String diff;

    @SerializedName("created_on")
    public String created_on;
    @SerializedName("updated_on")
    public String updated_on;
}
