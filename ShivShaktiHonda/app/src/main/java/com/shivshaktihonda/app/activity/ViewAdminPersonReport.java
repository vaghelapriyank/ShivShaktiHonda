package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelGetAllSalespersonData;
import com.shivshaktihonda.app.pojo.ModelPersonRecord;
import com.shivshaktihonda.app.pojo.ModelPersonRecordData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abc on 12/14/2017.
 */

public class ViewAdminPersonReport  extends AppCompatActivity {
    Toolbar adminDashboard_toolbar;
    TextView txtName,txtIncentive,txtInquiry,txtBooking,txtDelivery,txtMonth;
    Intent intent;
    ModelGetAllSalespersonData selectedSalespersonData;
    private ArrayList<ModelPersonRecordData> personRecordData;
    Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_salesperson_report);
        init();

        adminDashboard_toolbar.setPadding(0, CM.getStatusBarHeight(ViewAdminPersonReport.this),0,0);
        setSupportActionBar(adminDashboard_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.person_report_screen_title));

        intent=getIntent();
        if (intent!=null){
            selectedSalespersonData=(ModelGetAllSalespersonData)intent.getSerializableExtra(CV.SELECTED_SLAESPERSON);
        }

        if(CM.isInternetAvailable(ViewAdminPersonReport.this)){
            webcallForGetPersonalData(selectedSalespersonData.user_id);
        }else{
            CM.showDialogueNoInternet(ViewAdminPersonReport.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }
    }

    private void webcallForGetPersonalData(String user_id) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAdminPersonReport.this, true, true);

            WebService.getPersonalData(v,user_id, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForPersonalData(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAdminPersonReport.this, error, false);
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
                CM.ShowDialogue(ViewAdminPersonReport.this,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void setPersonRecordData(ArrayList<ModelPersonRecordData> personRecordData) {
        txtName.setText(selectedSalespersonData.name);
        txtIncentive.setText(personRecordData.get(0).incentive);
        txtInquiry.setText(personRecordData.get(0).tot_inquiry);
        txtBooking.setText(personRecordData.get(0).tot_booking);
        txtDelivery.setText(personRecordData.get(0).tot_delivery);
        txtMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
    }

    private void init() {
        calendar=Calendar.getInstance();
        personRecordData=new ArrayList<>();
        adminDashboard_toolbar=(Toolbar)findViewById(R.id.personReport_toolbar);
        txtName=(TextView) findViewById(R.id.personReport_txtPersonName);
        txtIncentive=(TextView) findViewById(R.id.personReport_txtIncentive);
        txtInquiry=(TextView) findViewById(R.id.personReport_txtInquiry);
        txtBooking=(TextView) findViewById(R.id.personReport_txtBooking);
        txtDelivery=(TextView) findViewById(R.id.personReport_txtDelivery);
        txtMonth=(TextView) findViewById(R.id.personReport_txtMonth);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
