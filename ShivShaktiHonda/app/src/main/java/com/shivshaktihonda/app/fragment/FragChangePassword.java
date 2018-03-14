package com.shivshaktihonda.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewBookingDetail;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.adaptor.AdptSpBookingList;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.pojo.ModelBookings;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abc on 12/8/2017.
 */

public class FragChangePassword extends Fragment implements View.OnClickListener {
    Activity thisActivity;
    EditText oldPwd,newPwd,confirmPwd;
    Button btnUpdate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_changepassword,container,false);
        thisActivity=getActivity();
        init(view);
        clickEvent();
        setHasOptionsMenu(true);

        return view;
    }

    private void clickEvent() {
//        imgNextMonth.setOnClickListener(this);
//        imgPreMonth.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share,menu);
        MenuItem emailItem=menu.findItem(R.id.action_email);

        if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
            emailItem.setVisible(true);
        }else{
            emailItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_email){
//            callApiAndSendMailOfList(month,year);
        }
        return super.onOptionsItemSelected(item);
    }



    private void init(View view) {
        oldPwd=(EditText) view.findViewById(R.id.cp_edtOldPwd);
        newPwd =(EditText) view.findViewById(R.id.cp_edtNewPwd);
        confirmPwd=(EditText) view.findViewById(R.id.cp_edtConfirmPwd);
        btnUpdate=(Button) view.findViewById(R.id.cp_btnUpdate);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.change_password_title));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View view) {
        if (view==btnUpdate){
            if (oldPwd.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(thisActivity,thisActivity.getResources().getString(R.string.old_password_empty));
            }else if (newPwd.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(thisActivity,thisActivity.getResources().getString(R.string.new_password_empty));
            }else if (confirmPwd.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(thisActivity,thisActivity.getResources().getString(R.string.confirm_password_empty));
            }else{
                if(CM.isInternetAvailable(thisActivity)){
                    webcallForChangePassword(CM.getSp(thisActivity, CV.USER_ID,"").toString(),oldPwd.getText().toString(),newPwd.getText().toString(),confirmPwd.getText().toString());
                }else{
                    CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
                }
            }
        }
    }

    private void webcallForChangePassword(String userId, String oldPwd, String newPwd, String confirmPwd) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.changePassword(v,userId,oldPwd,newPwd,confirmPwd , new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForChangePwd(response);
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

    private void getResponseForChangePwd(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                oldPwd.setText("");
                newPwd.setText("");
                confirmPwd.setText("");
                CM.ShowDialogue(thisActivity,msg);
            }else{
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
