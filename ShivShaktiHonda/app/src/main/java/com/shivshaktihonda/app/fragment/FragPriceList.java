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
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.activity.ViewDeliveryDetail;
import com.shivshaktihonda.app.activity.ViewSpPriceDetail;
import com.shivshaktihonda.app.adaptor.AdptSpDeliveryList;
import com.shivshaktihonda.app.adaptor.AdptSpPriceList;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.pojo.ModelBookings;
import com.shivshaktihonda.app.pojo.ModelPriceList;
import com.shivshaktihonda.app.pojo.ModelPriceListData;
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

public class FragPriceList extends Fragment implements RecyclerClickListener {
    Activity thisActivity;
    RecyclerView rvPriceList;
    AdptSpPriceList adptSpPriceList;
    ArrayList<ModelPriceListData> priceListData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_sp_price_list,container,false);
        thisActivity=getActivity();
        init(view);

        if(CM.isInternetAvailable(thisActivity)){
            webcallForGetPriceList();
        }else{
            CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }
        return view;
    }

    private void webcallForGetPriceList() {
        String userId=CM.getSp(thisActivity, CV.USER_ID,"").toString();

        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.getAllPriceList(v,userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAllPriceList(response);
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

    private void getResponseForAllPriceList(String response) {
        Log.e("RESPONSE",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelPriceList mainModel=CM.JsonParse(new ModelPriceList(),response);
                priceListData= mainModel.priceListDataArray;

                if(priceListData!=null && priceListData.size()!=0){
                    setPriceList(priceListData);
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

    private void setPriceList(ArrayList<ModelPriceListData> priceListData) {
        rvPriceList.removeAllViews();
        adptSpPriceList=new AdptSpPriceList(thisActivity,priceListData);
        adptSpPriceList.clickListener(this);
        rvPriceList.setAdapter(adptSpPriceList);
    }

    private void init(View view) {
        priceListData=new ArrayList<>();
        rvPriceList=(RecyclerView)view.findViewById(R.id.price_rvList);
        rvPriceList.setLayoutManager(new LinearLayoutManager(thisActivity));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.price_list_screen_title));
    }

    @Override
    public void onClickEvent(View v, int position) {
        CardView cvParent=(CardView)v.findViewById(R.id.spPriceList_cvParent);

        if (v==cvParent){
            Intent intent=new Intent(thisActivity, ViewSpPriceDetail.class);
            intent.putExtra(CV.SELECT_MODEL_PRICELIST,priceListData.get(position));
            CM.startActivityForResult(intent,500,thisActivity);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==500 && resultCode==thisActivity.RESULT_OK){
            if(CM.isInternetAvailable(thisActivity)){
                webcallForGetPriceList();
            }else{
                CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
            }
        }
    }
}
