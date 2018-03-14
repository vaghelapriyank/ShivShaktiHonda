package com.shivshaktihonda.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.pojo.ModelBookings;
import com.shivshaktihonda.app.pojo.ModelPersonRecord;
import com.shivshaktihonda.app.pojo.ModelPersonRecordData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abc on 12/8/2017.
 */

public class FragPersonDashboard extends Fragment {
    Activity  thisActivity;
    private ArrayList<ModelPersonRecordData> personRecordData;
    TextView txtIncentive,txtInquiriesCount,txtBookingsCount,txtDeliveryCount;
    TextView txtMonth;
    Calendar calendar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_person_dashboard,container,false);
        thisActivity=getActivity();
        init(view);


        if(CM.isInternetAvailable(thisActivity)){
            webcallForGetPersonalData();
        }else{
            CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }

        return view;
    }

    private void init(View view) {
        txtMonth=(TextView)view.findViewById(R.id.spDash_txtMonth);
        txtIncentive=(TextView)view.findViewById(R.id.spDash_txtIncentive);
        txtInquiriesCount=(TextView)view.findViewById(R.id.spDash_txtInquiry);
        txtBookingsCount=(TextView)view.findViewById(R.id.spDash_txtBooking);
        txtDeliveryCount=(TextView)view.findViewById(R.id.spDash_txtDelivery);
        calendar=Calendar.getInstance();
    }

    private void webcallForGetPersonalData() {
        String userId=CM.getSp(thisActivity, CV.USER_ID,"").toString();

        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.getPersonalData(v,userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForPersonalData(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(thisActivity, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForPersonalData(String response) {
        Log.e("RESPONSE",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelPersonRecord mainModel=CM.JsonParse(new ModelPersonRecord(),response);
                personRecordData = mainModel.personRecordDataArray;

                setPersonRecordData(personRecordData);
            }else{
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    private void setPersonRecordData(ArrayList<ModelPersonRecordData> personRecordData) {
        txtMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
        txtIncentive.setText(personRecordData.get(0).incentive);
        txtInquiriesCount.setText(personRecordData.get(0).tot_inquiry);
        txtBookingsCount.setText(personRecordData.get(0).tot_booking);
        txtDeliveryCount.setText(personRecordData.get(0).tot_delivery);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.dashboard_screen_title));
    }
}
