package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelSummaryData implements Serializable{
    @SerializedName("model_name")
    public String model_name;
    @SerializedName("model_type_name")
    public String model_type_name;
    @SerializedName("model_type_id")
    public String model_type_id;
    @SerializedName("tot_inquiry")
    public String tot_inquiry;
    @SerializedName("tot_booking")
    public String tot_booking;
    @SerializedName("tot_delivery")
    public String tot_delivery;
    @SerializedName("tot_amount")
    public String tot_amount;

}
