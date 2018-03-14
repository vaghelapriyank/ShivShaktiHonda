package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelPersonRecord {
    @SerializedName("status")
    public String status;
    @SerializedName("errorcode")
    public String errorcode;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public ArrayList<ModelPersonRecordData> personRecordDataArray = new ArrayList<ModelPersonRecordData>();

}
