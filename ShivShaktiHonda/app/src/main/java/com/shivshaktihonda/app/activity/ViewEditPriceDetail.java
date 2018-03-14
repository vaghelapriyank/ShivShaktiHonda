package com.shivshaktihonda.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelDelivery;
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

/**
 * Created by abc on 12/14/2017.
 */

public class ViewEditPriceDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar priceDetail_toolbar;
    Button btnEdit;
    Intent intent;
    ModelPriceListData modelPriceListData;
    EditText edtExShowRoom,edtInsurance,edtRTO,edtExtraWar,edtAMC,edtMPP,edtACC,edtTotal,edtVeriantModel;
    EditText edtIncentiveAmount,edtIncentiveExShowRoom,edtIncentiveInsurance,edtIncentiveRTO,edtIncentiveExtraWar,edtIncentiveAMC,edtIncentiveMPP,edtIncentiveACC,edtIncentiveTotal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_price_detail);
        init();
        clickEvent();
        priceDetail_toolbar.setPadding(0, CM.getStatusBarHeight(ViewEditPriceDetail.this),0,0);
        setSupportActionBar(priceDetail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Price");

        intent=getIntent();
        if (intent!=null){
            modelPriceListData=(ModelPriceListData)intent.getSerializableExtra(CV.SELECT_MODEL_PRICELIST);
            if (modelPriceListData!=null){
                setDataIntoValues(modelPriceListData);
            }
        }
    }

    private void setDataIntoValues(ModelPriceListData modelPriceListData) {
       edtVeriantModel.setText(modelPriceListData.veriant);
       edtExShowRoom.setText(modelPriceListData.ex_showroom);
       edtInsurance.setText(modelPriceListData.insurance);
       edtRTO.setText(modelPriceListData.rto);
       edtExtraWar.setText(modelPriceListData.extra_warranty);
       edtAMC.setText(modelPriceListData.amc);
       edtMPP.setText(modelPriceListData.mpp);
       edtACC.setText(modelPriceListData.acc);
       edtTotal.setText(modelPriceListData.total);
       edtIncentiveAmount.setText(modelPriceListData.incentive);
       edtIncentiveExShowRoom.setText(modelPriceListData.ex_showroom_per);
       edtIncentiveInsurance.setText(modelPriceListData.insurance_per);
       edtIncentiveRTO.setText(modelPriceListData.rto_per);
       edtIncentiveAMC.setText(modelPriceListData.amc_per);
       edtIncentiveExtraWar.setText(modelPriceListData.ew_per);
       edtIncentiveMPP.setText(modelPriceListData.mpp_per);
       edtIncentiveACC.setText(modelPriceListData.acc_per);
    }

    private void clickEvent() {
        btnEdit.setOnClickListener(this);
    }

    private void init() {
        priceDetail_toolbar=(Toolbar)findViewById(R.id.pd_toolbar);
        btnEdit=(Button) findViewById(R.id.pd_btnEdit);
        edtExShowRoom=(EditText) findViewById(R.id.pd_edtExShowroom);
        edtInsurance=(EditText) findViewById(R.id.pd_edtInsurance);
        edtRTO=(EditText) findViewById(R.id.pd_edtRTO);
        edtExtraWar=(EditText) findViewById(R.id.pd_edtExtraWar);
        edtAMC=(EditText) findViewById(R.id.pd_edtAmc);
        edtMPP=(EditText) findViewById(R.id.pd_edtMPP);
        edtACC=(EditText) findViewById(R.id.pd_edtAcc);
        edtTotal=(EditText) findViewById(R.id.pd_edtTotal);
        edtVeriantModel=(EditText) findViewById(R.id.pd_edtVariant);

        edtIncentiveAmount=(EditText) findViewById(R.id.pd_edtIncentiveAmount);
        edtIncentiveExShowRoom=(EditText) findViewById(R.id.pd_edtIncentiveExshowroom);
        edtIncentiveInsurance=(EditText) findViewById(R.id.pd_edtIncentiveInsurance);
        edtIncentiveRTO=(EditText) findViewById(R.id.pd_edtIncentiveRTO);
        edtIncentiveExtraWar=(EditText) findViewById(R.id.pd_edtIncentiveEW);
        edtIncentiveAMC=(EditText) findViewById(R.id.pd_edtIncentiveAMC);
        edtIncentiveMPP=(EditText) findViewById(R.id.pd_edtIncentiveMPP);
        edtIncentiveACC=(EditText) findViewById(R.id.pd_edtIncentiveACC);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==btnEdit){
            if (edtVeriantModel.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_veriant_model_empty));
            }else if (edtExShowRoom.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_exshowroom_empty));
            }else if (edtInsurance.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_insurance_empty));
            }else if (edtRTO.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_rto_empty));
            }else if (edtExtraWar.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_extra_war_empty));
            }else if (edtAMC.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_amc_empty));
            }else if (edtMPP.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_mpp_empty));
            }else if (edtACC.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_acc_empty));
            }else if (edtTotal.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_total_empty));
            }else if (edtIncentiveAmount.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_amount_empty));
            }else if (edtIncentiveExShowRoom.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_ex_showroom_empty));
            }else if (edtIncentiveInsurance.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_insurance_empty));
            }else if (edtIncentiveRTO.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_rto_empty));
            }else if (edtIncentiveAMC.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_amc_empty));
            }else if (edtIncentiveExtraWar.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_extra_war_empty));
            }else if (edtIncentiveMPP.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_mpp_empty));
            }else if (edtIncentiveACC.getText().toString().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewEditPriceDetail.this,getResources().getString(R.string.err_msg_incentive_acc_empty));
            }else{
                if(CM.isInternetAvailable(ViewEditPriceDetail.this)){
                        webcallForEditPriceList(modelPriceListData.model_type_id,edtVeriantModel.getText().toString(),edtExShowRoom.getText().toString(),edtInsurance.getText().toString(),edtRTO.getText().toString(),
                                edtExtraWar.getText().toString(),edtAMC.getText().toString(),edtMPP.getText().toString(),
                                edtACC.getText().toString(),edtTotal.getText().toString(),
                                edtIncentiveAmount.getText().toString(),edtIncentiveExShowRoom.getText().toString(),
                                edtIncentiveInsurance.getText().toString(),edtIncentiveRTO.getText().toString(),
                                edtIncentiveAMC.getText().toString(),edtIncentiveExtraWar.getText().toString(),edtIncentiveMPP.getText().toString(),
                                edtIncentiveACC.getText().toString());
                }else{
                    CM.showDialogueNoInternet(ViewEditPriceDetail.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
                }
            }
        }
    }

    private void webcallForEditPriceList(String model_type_id, String variantModel,String exShowroomPrice, String insuaranceAmount, String rto, String extraWar, String amc, String mpp, String acc, String total, String incentiveAmount, String incentiveExShowroom, String insuranceIncentive, String RTOIncentive, String AMCIncentive, String extraWarIncentive, String MPPIncentive, String ACCIncentive) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditPriceDetail.this, true, true);

            WebService.editPriceList(v,model_type_id,variantModel,exShowroomPrice,insuaranceAmount,rto,extraWar,amc,mpp,acc,total,incentiveAmount,incentiveExShowroom,insuranceIncentive,RTOIncentive,AMCIncentive,extraWarIncentive,MPPIncentive,ACCIncentive, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForEditPriceList(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewEditPriceDetail.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForEditPriceList(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEditPriceDetail.this);
                builder.setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_OK);
                                ViewEditPriceDetail.this.finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                CM.ShowDialogue(ViewEditPriceDetail.this,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
