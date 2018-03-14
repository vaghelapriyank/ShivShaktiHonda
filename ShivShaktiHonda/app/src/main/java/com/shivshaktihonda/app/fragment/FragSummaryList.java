package com.shivshaktihonda.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.activity.ViewSpPriceDetail;
import com.shivshaktihonda.app.activity.ViewSummaryDetail;
import com.shivshaktihonda.app.adaptor.AdptSpPriceList;
import com.shivshaktihonda.app.adaptor.AdptSpSummaryList;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelPriceList;
import com.shivshaktihonda.app.pojo.ModelPriceListData;
import com.shivshaktihonda.app.pojo.ModelSummary;
import com.shivshaktihonda.app.pojo.ModelSummaryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abc on 12/8/2017.
 */

public class FragSummaryList extends Fragment implements RecyclerClickListener, View.OnClickListener {
    Activity thisActivity;
    ImageView imgPreMonth,imgNextMonth;
    TextView txtCurMonth;
    RecyclerView rvSummaryList;
    AdptSpSummaryList adptSpSummaryList;
    Calendar calendar;
    String month, year;
    DecimalFormat formatter ;
    private ArrayList<ModelSummaryData> summaryListData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_summary,container,false);
        thisActivity=getActivity();
        init(view);
        clickEvent();
        if(CM.isInternetAvailable(thisActivity)){
            webcallForGetMonthSummary(month,year);
        }else{
            CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }
        return view;
    }

    private void clickEvent() {
        imgNextMonth.setOnClickListener(this);
        imgPreMonth.setOnClickListener(this);
    }

    private void webcallForGetMonthSummary(String month,String year) {

        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.getAllModelSummaries(v,month,year, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAllSummaries(response);
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

    private void getResponseForAllSummaries(String response) {
        Log.e("RESPONSE",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelSummary mainModel=CM.JsonParse(new ModelSummary(),response);
                summaryListData= mainModel.summaryDataArray;

                if(summaryListData!=null && summaryListData.size()!=0){
                    setSummaryList(summaryListData);
                }
            }else{
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    private void setSummaryList(ArrayList<ModelSummaryData> summaryListData) {
        rvSummaryList.removeAllViews();
        adptSpSummaryList=new AdptSpSummaryList(thisActivity,summaryListData);
        adptSpSummaryList.clickListener(this);
        rvSummaryList.setAdapter(adptSpSummaryList);
    }

    private void init(View view) {
        summaryListData=new ArrayList<>();
        formatter = new DecimalFormat("00");
        imgPreMonth=(ImageView)view.findViewById(R.id.summary_ivPreMonth);
        imgNextMonth=(ImageView)view.findViewById(R.id.summary_ivNextMonth);
        txtCurMonth=(TextView) view.findViewById(R.id.summary_txtMonthYear);
        rvSummaryList=(RecyclerView)view.findViewById(R.id.summary_rvList);
        rvSummaryList.setLayoutManager(new LinearLayoutManager(thisActivity));

        calendar=Calendar.getInstance();
        txtCurMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
        month=formatter.format((calendar.getTime().getMonth()+1));
        year=String.valueOf(calendar.get(Calendar.YEAR));
        Log.e("YEAR",year);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.summary_list_screen_title));
    }

    @Override
    public void onClickEvent(View v, int position) {
        CardView cvParent=(CardView)v.findViewById(R.id.spPriceList_cvParent);

        if (v==cvParent){
            Intent intent=new Intent(thisActivity, ViewSummaryDetail.class);
            intent.putExtra(CV.SELECT_MODEL_SUMMARY,summaryListData.get(position));
            CM.startActivity(intent,thisActivity);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*        if (requestCode==500 && resultCode==thisActivity.RESULT_OK){
        }*/
    }

    @Override
    public void onClick(View view) {
        if (view==imgNextMonth){
            setNextMonth();
        }else if (view==imgPreMonth){
            setPreviousMonth();
        }
    }

    private void setNextMonth() {
        if (calendar!=null){
            calendar.add(Calendar.MONTH,1);
            txtCurMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
            month = formatter.format((calendar.getTime().getMonth()+1));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("YEAR",year);
            webcallForGetMonthSummary(month,year);
        }
    }

    private void setPreviousMonth() {
        if (calendar!=null){
            calendar.add(Calendar.MONTH,-1);
            txtCurMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
            month = formatter.format((calendar.getTime().getMonth()+1));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("YEAR",year);
            webcallForGetMonthSummary(month,year);
        }
    }

}
