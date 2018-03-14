package com.shivshaktihonda.app.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abc on 12/16/2017.
 */

public class ModelVehiclesData {
    @SerializedName("model_id")
    public String model_id;
    @SerializedName("model_name")
    public String model_name;
    @SerializedName("verient")
    public ArrayList<ModelVeriantData> verientData=new ArrayList<>();
    @SerializedName("color")
    public ArrayList<ModelVehicleColorData> colorData=new ArrayList<>();
}
