package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
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

/**
 * Created by abc on 12/25/2017.
 */

public class ViewAddSalesPerson extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText edtSPName,edtSPEmail,edtSPMobileNumber,edtSPUserName,edtSPPassword;
    Button btnAdd;
    TextView txtPassword;
    Intent intent;
    ModelGetAllSalespersonData selectedSalespersonData;
    boolean isFromEditMode=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_salesperson);
        init();
        clickEvent();
        toolbar.setPadding(0, CM.getStatusBarHeight(ViewAddSalesPerson.this),0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.salesperson_add_screen_title));

        intent=getIntent();
        if (intent!=null){
            selectedSalespersonData=(ModelGetAllSalespersonData)intent.getSerializableExtra(CV.SELECTED_SALESPERSONDATA);
            isFromEditMode=intent.getBooleanExtra(CV.IS_FROM_EDIT_MODE,false);
        }

        if (isFromEditMode){
            setSalespersonData(selectedSalespersonData);
        }else{
            btnAdd.setText("ADD");
            txtPassword.setVisibility(View.VISIBLE);
            edtSPPassword.setVisibility(View.VISIBLE);
        }

    }

    private void setSalespersonData(ModelGetAllSalespersonData selectedSalespersonData) {
        edtSPName.setText(selectedSalespersonData.name);
        edtSPEmail.setText(selectedSalespersonData.email);
        edtSPMobileNumber.setText(selectedSalespersonData.mobile_number);
        edtSPUserName.setText(selectedSalespersonData.user_name);
        txtPassword.setVisibility(View.GONE);
        edtSPPassword.setVisibility(View.GONE);
        btnAdd.setText("UPDATE");
    }

    private void clickEvent() {
        btnAdd.setOnClickListener(this);
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.addSP_toolbar);
        edtSPName=(EditText)findViewById(R.id.addsp_edtName);
        edtSPEmail=(EditText)findViewById(R.id.addsp_edtEmail);
        edtSPMobileNumber=(EditText)findViewById(R.id.addsp_edtMobileNumber);
        edtSPUserName=(EditText)findViewById(R.id.addsp_edtUsername);
        edtSPPassword=(EditText)findViewById(R.id.addsp_edtPassword);
        txtPassword=(TextView) findViewById(R.id.addsp_txtPassword);
        btnAdd=(Button) findViewById(R.id.addsp_btnAdd);
    }

    @Override
    public void onClick(View view) {
        if (view==btnAdd){
            if (edtSPName.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddSalesPerson.this,getResources().getString(R.string.err_msg_name_empty));
            }else if (edtSPEmail.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddSalesPerson.this,getResources().getString(R.string.err_msg_email_empty));
            }else if (edtSPMobileNumber.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddSalesPerson.this,getResources().getString(R.string.err_msg_mobile_number_empty));
            }else if (edtSPUserName.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddSalesPerson.this,getResources().getString(R.string.err_msg_username_empty));
            }else if (edtSPPassword.getVisibility()==View.VISIBLE && edtSPPassword.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddSalesPerson.this,getResources().getString(R.string.err_msg_pwd_empty));
            }else if(CM.isInternetAvailable(ViewAddSalesPerson.this)){
                if (isFromEditMode){
                    webcallToEditSalesPerson(selectedSalespersonData.user_id,edtSPName.getText().toString(),edtSPEmail.getText().toString(),edtSPMobileNumber.getText().toString(),edtSPUserName.getText().toString(),edtSPPassword.getText().toString());
                }else{
                    webcallToAddSalesPerson(edtSPName.getText().toString(),edtSPEmail.getText().toString(),edtSPMobileNumber.getText().toString(),edtSPUserName.getText().toString(),edtSPPassword.getText().toString());
                }
            }else{
                CM.showDialogueNoInternet(ViewAddSalesPerson.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
            }
        }
    }

    private void webcallToEditSalesPerson(String userId,String name, String email, String mobile,String userName, String pwd) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddSalesPerson.this, true, true);

            WebService.editSalesPerson(v,userId,name,email,mobile,userName,pwd, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddSalesPerson(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddSalesPerson.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void webcallToAddSalesPerson(String name, String email, String mobile,String userName, String pwd) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddSalesPerson.this, true, true);

            WebService.addSalesPerson(v,name,email,mobile,userName,pwd, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddSalesPerson(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddSalesPerson.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getResponseForAddSalesPerson(String response) {
        Log.e("RESPONSE",response);

        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                setResult(RESULT_OK);
                ViewAddSalesPerson.this.finish();
            }else{
                CM.ShowDialogue(ViewAddSalesPerson.this,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
