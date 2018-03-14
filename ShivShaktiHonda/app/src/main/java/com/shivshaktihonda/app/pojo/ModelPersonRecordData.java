package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelPersonRecordData implements Serializable{
    @SerializedName("incentive")
    public String incentive;
    @SerializedName("tot_inquiry")
    public String tot_inquiry;
    @SerializedName("tot_booking")
    public String tot_booking;
    @SerializedName("tot_delivery")
    public String tot_delivery;
}
