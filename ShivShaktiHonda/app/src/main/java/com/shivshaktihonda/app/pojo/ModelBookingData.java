package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelBookingData implements Serializable{
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("inquiry_id")
    public String inquery_id;
    @SerializedName("salesperson_name")
    public String salesperson_name;
    @SerializedName("booking_primary_id")
    public String booking_primary_id;
    @SerializedName("booking_id")
    public String booking_id;
    @SerializedName("amount")
    public String amount;
    @SerializedName("model_total_amount")
    public String model_total_amount;
    @SerializedName("incentive")
    public String incentive;
    @SerializedName("payment_type")
    public String payment_type;

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
    @SerializedName("delivery_datettime")
    public String delivery_datettime;
    @SerializedName("incentive_ex_showroom")
    public String incentive_ex_showroom;
    @SerializedName("incentive_insurance")
    public String incentive_insurance;
    @SerializedName("incentive_rto")
    public String incentive_rto;
    @SerializedName("incentive_amc")
    public String incentive_amc;
    @SerializedName("incentive_ew")
    public String incentive_ew;
    @SerializedName("incentive_mpp")
    public String incentive_mpp;
    @SerializedName("incentive_acc")
    public String incentive_acc;
    @SerializedName("created_on")
    public String created_on;
    @SerializedName("updated_on")
    public String updated_on;



}
