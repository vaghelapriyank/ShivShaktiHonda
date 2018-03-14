package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelSummaryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 1/9/2018.
 */

public class ViewSummaryDetail extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTotalInquiry,txtTotalBooking,txtTotalDelivery,txtTotalCollection;
    Intent intent;
    ModelSummaryData selectedSummary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_summary_detail);
        init();
        toolbar.setPadding(0, CM.getStatusBarHeight(ViewSummaryDetail.this),0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(getResources().getString(R.string.salespersondetail_screen_title));
        intent=getIntent();
        if (intent!=null){
            selectedSummary=(ModelSummaryData)intent.getSerializableExtra(CV.SELECT_MODEL_SUMMARY);
            if (selectedSummary!=null){
                setSummaryData(selectedSummary);
            }
        }
    }

    private void setSummaryData(ModelSummaryData selectedSummary) {
        getSupportActionBar().setTitle(selectedSummary.model_name);
        txtTotalInquiry.setText(selectedSummary.tot_inquiry);
        txtTotalBooking.setText(selectedSummary.tot_booking);
        txtTotalDelivery.setText(selectedSummary.tot_delivery);
        txtTotalCollection.setText(selectedSummary.tot_amount);
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.sd_toolbar);
        txtTotalInquiry=(TextView)findViewById(R.id.sd_txtTotalInquiries);
        txtTotalBooking=(TextView)findViewById(R.id.sd_txtTotalBooking);
        txtTotalDelivery=(TextView)findViewById(R.id.sd_txtTotalDelivery);
        txtTotalCollection=(TextView)findViewById(R.id.sd_txtTotalCollection);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
