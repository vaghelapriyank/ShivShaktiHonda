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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;

/**
 * Created by abc on 12/10/2017.
 */

public class ViewAddDeliveryForm extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    EditText edtEngineNumber,edtChesisNumber,edtBatteryNumber,edtKeyNumber,edtDCNumber,
        edtPriceDiscount,edtDifference;
    TextView edtPriceTotal,edtIncentive,txtRemainingAmount;
    TextView txtCustomerName,txtCustomerNumber,txtModelName;
    RadioButton rbCash,rbFinance,rbExchange,rbPaytm,rbOther;

    Button btnSave;
    String strPaymentType="cash";

    Intent intent;
    ModelBookingData modelBookingData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_delilvery_form);
        init();
        clickEvent();
        changeListenr();
        toolbar.setPadding(0, CM.getStatusBarHeight(ViewAddDeliveryForm.this),0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.delivery_form_screen_title));

        intent=getIntent();
        if (intent!=null){
            modelBookingData=(ModelBookingData) intent.getSerializableExtra(CV.SELECTED_BOOKING);
            edtPriceTotal.setText(modelBookingData.model_total_amount);
            txtRemainingAmount.setText("" + (Integer.parseInt(modelBookingData.model_total_amount)-Integer.parseInt(modelBookingData.amount)));
            edtIncentive.setText(modelBookingData.incentive);
            txtCustomerName.setText(modelBookingData.first_name+" "+modelBookingData.last_name);
            txtCustomerNumber.setText(modelBookingData.mobile_number);
            txtModelName.setText(modelBookingData.model_name);
        }

    }

    private void changeListenr() {
        rbCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="cash";
                }
            }
        });

        rbFinance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="finance";
                }
            }
        });
        rbExchange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="exchange";
                }
            }
        });
        rbPaytm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="paytm";
                }
            }
        });
        rbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="other";
                }
            }
        });
    }

    private void clickEvent() {
        btnSave.setOnClickListener(this);
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.deliveryForm_toolbar);
        edtEngineNumber=(EditText) findViewById(R.id.formDelivery_edtEngineNumber);
        edtChesisNumber=(EditText) findViewById(R.id.formDelivery_edtChesisNumber);
        edtBatteryNumber=(EditText) findViewById(R.id.formDelivery_edtBatteryNumber);
        edtKeyNumber=(EditText) findViewById(R.id.formDelivery_edtKeyNumber);
        edtDCNumber=(EditText) findViewById(R.id.formDelivery_edtDCNumber);
        edtPriceTotal=(TextView) findViewById(R.id.formDelivery_edtTotalPrice);
        edtIncentive=(TextView) findViewById(R.id.formDelivery_edtIncentive);
        edtPriceDiscount=(EditText) findViewById(R.id.formDelivery_edtDiscountPrice);
        edtDifference=(EditText) findViewById(R.id.formDelivery_edtDifferencePrice);
        rbCash=(RadioButton) findViewById(R.id.deliveryForm_rbCash);
        rbFinance=(RadioButton) findViewById(R.id.deliveryForm_rbFinance);
        rbExchange=(RadioButton) findViewById(R.id.deliveryForm_rbExchange);
        rbPaytm=(RadioButton) findViewById(R.id.deliveryForm_rbPaytm);
        rbOther=(RadioButton) findViewById(R.id.deliveryForm_rbOther);
        btnSave = (Button) findViewById(R.id.formDellivery_btnDone);
        txtCustomerName = (TextView) findViewById(R.id.formDelivery_txtCustomerName);
        txtCustomerNumber = (TextView) findViewById(R.id.formDelivery_txtCustomerMobile);
        txtModelName = (TextView) findViewById(R.id.formDelivery_txtModelName);
        txtRemainingAmount = (TextView) findViewById(R.id.formDelivery_edtRemainingAmount);

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
        if (view==btnSave){
            if (edtEngineNumber.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter Engine Number");
            }else if (edtChesisNumber.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter Chesis Number");
            }else if (edtBatteryNumber.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter battery Number");
            }else if (edtKeyNumber.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter Key Number");
            }else if (edtDCNumber.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter DC Number");
            }else if (edtPriceDiscount.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter Price Discount");
            }else if (edtDifference.getText().toString().trim().equalsIgnoreCase("")){
                CM.ShowDialogue(ViewAddDeliveryForm.this,"Please Enter Difference");
            }else{
                if(CM.isInternetAvailable(ViewAddDeliveryForm.this)){
                    webcallForAddDelivery(modelBookingData.booking_primary_id,edtEngineNumber.getText().toString(),edtChesisNumber.getText().toString(),edtBatteryNumber.getText().toString(),edtKeyNumber.getText().toString(),
                            edtDCNumber.getText().toString(),edtPriceTotal.getText().toString(),edtPriceDiscount.getText().toString(),edtDifference.getText().toString(),strPaymentType );
                }else{
                    CM.showDialogueNoInternet(ViewAddDeliveryForm.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
                }
            }
        }
    }

    private void webcallForAddDelivery(String bookingId, String engieNumber, String chesisNumber, String batteryNumber, String keyNumber, String dcNumber, String priceTotal, String edtDiscount, String priceDifference, String strPaymentType) {
        String userId=CM.getSp(ViewAddDeliveryForm.this, CV.USER_ID,"").toString();

        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddDeliveryForm.this, true, true);

            WebService.addDeliveryForm(v,userId,bookingId,engieNumber,chesisNumber,batteryNumber,keyNumber,dcNumber,priceTotal,edtDiscount,priceDifference,strPaymentType, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddDelivery(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddDeliveryForm.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getResponseForAddDelivery(String response) {
        Log.i("REsponse",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);
        if (responsecode.equalsIgnoreCase("1")){
//            String deliveryId= CM.getValueFromJson(WebServiceTag.TAG_STR_BOOKING_PRIMARY_ID, response);
//            CM.ShowDialogue(ViewAddInquiry.this,msg);
            setResult(RESULT_OK);
            ViewAddDeliveryForm.this.finish();
        }else{
            CM.ShowDialogue(ViewAddDeliveryForm.this,msg);
        }
    }
}
