package com.shivshaktihonda.app.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shivshaktihonda.app.pojo.ModelVehicleColorData;
import com.shivshaktihonda.app.pojo.ModelVeriantData;

import java.util.ArrayList;

/**
 * Created by abc on 12/20/2017.
 */

public class AdptSpinnerModelColor extends ArrayAdapter<ModelVehicleColorData> {
    Context context;
    ArrayList<ModelVehicleColorData> vehileColorData;


    public AdptSpinnerModelColor(@NonNull Context context, int resource, ArrayList<ModelVehicleColorData> vehileColorData) {
        super(context, resource,vehileColorData);
        this.context=context;
        this.vehileColorData=vehileColorData;
    }

    @Override
    public int getCount(){
        return vehileColorData.size();
    }

    @Override
    public ModelVehicleColorData getItem(int position){
        return vehileColorData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(10,10,10,10);
        label.setTextSize(16);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(vehileColorData.get(position).model_color_name);
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(20,20,10,20);
        label.setTextSize(16);

        label.setText(vehileColorData.get(position).model_color_name);
        return label;
    }

}
