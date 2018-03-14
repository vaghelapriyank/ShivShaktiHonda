package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelPriceListData implements Serializable {
    @SerializedName("model_type_id")
    public String model_type_id;
    @SerializedName("model_id")
    public String model_id;
    @SerializedName("model_name")
    public String model_name;
    @SerializedName("veriant")
    public String veriant;
    @SerializedName("price_type")
    public String price_type;
    @SerializedName("ex_showroom")
    public String ex_showroom;
    @SerializedName("insurance")
    public String insurance;
    @SerializedName("rto")
    public String rto;
    @SerializedName("extra_warranty")
    public String extra_warranty;
    @SerializedName("amc")
    public String amc;
    @SerializedName("3mpp")
    public String mpp;
    @SerializedName("acc")
    public String acc;
    @SerializedName("total")
    public String total;

    @SerializedName("incentive")
    public String incentive;
    @SerializedName("ex_showroom_per")
    public String ex_showroom_per;
    @SerializedName("insurance_per")
    public String insurance_per;
    @SerializedName("rto_per")
    public String rto_per;
    @SerializedName("amc_per")
    public String amc_per;
    @SerializedName("ew_per")
    public String ew_per;
    @SerializedName("3m_per")
    public String mpp_per;
    @SerializedName("acc_per")
    public String acc_per;
    @SerializedName("created_on")
    public String created_on;







}
