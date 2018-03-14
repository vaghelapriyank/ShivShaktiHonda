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

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewAdminPersonReport;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.adaptor.AdptAdminDashBoard;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelGetAllPerson;
import com.shivshaktihonda.app.pojo.ModelGetAllSalespersonData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by abc on 12/8/2017.
 */

public class FragAdminDashboard extends Fragment implements RecyclerClickListener {
    Activity thisActivity;
    RecyclerView rvPersonList;
    AdptAdminDashBoard adptAdminDashBoard;
    private ArrayList<ModelGetAllSalespersonData> salesPersonDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_admin_dashboard,container,false);
        thisActivity=getActivity();
        init(view);

        if(CM.isInternetAvailable(thisActivity)){
            webcallForGetAllSalesPerson();
        }else{
            CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }

        return view;
    }

    private void webcallForGetAllSalesPerson() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.getAllSalesPerson(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAllSalesPerson(response);
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

    private void getResponseForAllSalesPerson(String response) {
        Log.e("RESPONSE",response);

        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelGetAllPerson mainModel=CM.JsonParse(new ModelGetAllPerson(),response);
                salesPersonDataList = mainModel.getAllPersonDataArray;

                setSalespersonListData(salesPersonDataList);
            }else{
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    private void setSalespersonListData(ArrayList<ModelGetAllSalespersonData> salesPersonDataList) {
        adptAdminDashBoard=new AdptAdminDashBoard(thisActivity,salesPersonDataList);
        adptAdminDashBoard.clickListener(this);
        rvPersonList.setAdapter(adptAdminDashBoard);
    }

    private void init(View view) {
        salesPersonDataList=new ArrayList<>();
        rvPersonList=(RecyclerView)view.findViewById(R.id.adminDashboard_rvList);
        rvPersonList.setLayoutManager(new LinearLayoutManager(thisActivity));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.admin_dashboard_screen_title));
    }

    @Override
    public void onClickEvent(View v, int position) {
        CardView cvParent=(CardView)v.findViewById(R.id.adminDasgboard_cvParent);

        if (v==cvParent){
            Intent intent=new Intent(thisActivity, ViewAdminPersonReport.class);
            intent.putExtra(CV.SELECTED_SLAESPERSON,salesPersonDataList.get(position));
            CM.startActivity(intent,thisActivity);
        }
    }
}