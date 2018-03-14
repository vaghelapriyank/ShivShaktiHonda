package com.shivshaktihonda.app.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.pojo.ModelInquiry;
import com.shivshaktihonda.app.pojo.ModelInquiryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by abc on 12/9/2017.
 */

public class ViewAddBookingForm extends AppCompatActivity implements View.OnClickListener {
    Toolbar booking_toolbar;
    EditText edtBookingId,edtAmount;
    TextView txtDeliveryDate;
    RadioButton rbCash,rbCHQ,rbOther;
    Button btnSave;
    String strPaymentType="cash";
    private Calendar calendar;
    TextView txtYear,txtMonthDemoView;
    private int year, month, day;
    private DatePickerDialog datePickerDialog;
    Calendar mCalendar;
    Intent intent;
    ModelInquiryData modelInquiryData;
    ModelBookingData selectedBookingData;
    boolean isFromEditMode=false;

    //    private String inquiryId="";

    RadioButton rbExShowroomYes,rbExShowroomNo,rbInsuranceYes,rbInsuranceNo,rbRtoYes,rbRtoNo,
            rbAmcYes,rbAmcNo,rbExtraWarrantyYes,rbExtraWarrantyNo,rbMppYes,rbMppNo,rbAccYes,rbAccNo;
    TextView txtCustomerName,txtCustomerMobile,txtModelName;
    String isExShowroomInclude="No",isInsuranceInclude="No",isRtoInclude="No",isAmcInclude="No",isEWInclude="No",isMppInclude="No",isAccInclude="No";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_booking_form);
        init();
        clickEvent();
        changeListener();

        intent=getIntent();

        if (intent!=null){
//            inquiryId=intent.getStringExtra(CV.SELECtED_INQUIRY_ID);
            modelInquiryData=(ModelInquiryData) intent.getSerializableExtra(CV.SELECtED_INQUIRY);
            selectedBookingData=(ModelBookingData) intent.getSerializableExtra(CV.SELECTED_BOOKING_DATA);
            isFromEditMode=intent.getBooleanExtra(CV.IS_FROM_EDIT_MODE,false);
            if (modelInquiryData!=null){
                txtCustomerName.setText(modelInquiryData.first_name+" "+modelInquiryData.last_name);
                txtCustomerMobile.setText(modelInquiryData.mobile_number);
                txtModelName.setText(modelInquiryData.model_name);
            }
        }

        if (isFromEditMode){
            setBookingDataForEdit(selectedBookingData);
        }

        booking_toolbar.setPadding(0, CM.getStatusBarHeight(ViewAddBookingForm.this),0,0);
        setSupportActionBar(booking_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.booking_form_screen_title));

    }

    private void setBookingDataForEdit(ModelBookingData selectedBookingData) {
        txtCustomerName.setText(selectedBookingData.first_name +" "+selectedBookingData.last_name);
        txtCustomerMobile.setText(selectedBookingData.mobile_number);
        txtModelName.setText(selectedBookingData.model_name);
        edtBookingId.setText(selectedBookingData.booking_id);
        edtAmount.setText(selectedBookingData.amount);
        try {
            txtDeliveryDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(selectedBookingData.delivery_datettime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedBookingData.payment_type.equalsIgnoreCase("cash")){
            strPaymentType="cash";
            rbCash.setChecked(true);
            rbCHQ.setChecked(false);
            rbOther.setChecked(false);
        }else if (selectedBookingData.payment_type.equalsIgnoreCase("cheque")){
            strPaymentType="cheque";
            rbCash.setChecked(false);
            rbCHQ.setChecked(true);
            rbOther.setChecked(false);
        }else if (selectedBookingData.payment_type.equalsIgnoreCase("other")){
            strPaymentType="other";
            rbCash.setChecked(false);
            rbCHQ.setChecked(false);
            rbOther.setChecked(true);
        }

        if (selectedBookingData.incentive_ex_showroom.equalsIgnoreCase("Yes")){
            rbExShowroomYes.setChecked(true);
            rbExShowroomNo.setChecked(false);
            isExShowroomInclude="Yes";
        }else{
            isExShowroomInclude="No";
            rbExShowroomYes.setChecked(false);
            rbExShowroomNo.setChecked(true);
        }

        if (selectedBookingData.incentive_insurance.equalsIgnoreCase("Yes")){
            rbInsuranceYes.setChecked(true);
            rbInsuranceNo.setChecked(false);
            isInsuranceInclude="Yes";
        }else{
            isInsuranceInclude="No";
            rbInsuranceYes.setChecked(false);
            rbInsuranceNo.setChecked(true);
        }

        if (selectedBookingData.incentive_rto.equalsIgnoreCase("Yes")){
            rbRtoYes.setChecked(true);
            rbRtoNo.setChecked(false);
            isRtoInclude="Yes";
        }else{
            isRtoInclude="No";
            rbRtoYes.setChecked(false);
            rbRtoNo.setChecked(true);
        }

        if (selectedBookingData.incentive_amc.equalsIgnoreCase("Yes")){
            rbAmcYes.setChecked(true);
            rbAmcNo.setChecked(false);
            isAmcInclude="Yes";
        }else{
            isAmcInclude="No";
            rbAmcYes.setChecked(false);
            rbAmcNo.setChecked(true);
        }

        if (selectedBookingData.incentive_amc.equalsIgnoreCase("Yes")){
            rbAmcYes.setChecked(true);
            rbAmcNo.setChecked(false);
            isAmcInclude="Yes";
        }else{
            isAmcInclude="No";
            rbAmcYes.setChecked(false);
            rbAmcNo.setChecked(true);
        }

        if (selectedBookingData.incentive_ew.equalsIgnoreCase("Yes")){
            rbExtraWarrantyYes.setChecked(true);
            rbExtraWarrantyNo.setChecked(false);
            isEWInclude="Yes";
        }else{
            isEWInclude="No";
            rbExtraWarrantyYes.setChecked(false);
            rbExtraWarrantyNo.setChecked(true);
        }

        if (selectedBookingData.incentive_mpp.equalsIgnoreCase("Yes")){
            rbMppYes.setChecked(true);
            rbMppNo.setChecked(false);
            isMppInclude="Yes";
        }else{
            isMppInclude="No";
            rbMppYes.setChecked(false);
            rbMppNo.setChecked(true);
        }

        if (selectedBookingData.incentive_acc.equalsIgnoreCase("Yes")){
            rbAccYes.setChecked(true);
            rbAccNo.setChecked(false);
            isAccInclude="Yes";
        }else{
            isAccInclude="No";
            rbAccYes.setChecked(false);
            rbAccNo.setChecked(true);
        }

    }

    private void changeListener() {
        rbCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="cash";
                }
            }
        });

        rbCHQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    strPaymentType="cheque";
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

        rbExShowroomYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isExShowroomInclude="Yes";
                }
            }
        });

        rbExShowroomNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isExShowroomInclude="No";
                }
            }
        });

        rbInsuranceYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isInsuranceInclude="Yes";
                }
            }
        });

        rbInsuranceNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isInsuranceInclude="No";
                }
            }
        });

        rbRtoYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isRtoInclude="Yes";
                }
            }
        });

        rbRtoNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isRtoInclude="No";
                }
            }
        });

        rbAmcYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAmcInclude="Yes";
                }
            }
        });

        rbAmcNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAmcInclude="No";
                }
            }
        });

        rbExtraWarrantyYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isEWInclude="Yes";
                }
            }
        });

        rbExtraWarrantyNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isEWInclude="No";
                }
            }
        });

        rbMppYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isMppInclude="Yes";
                }
            }
        });

        rbMppNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isMppInclude="No";
                }
            }
        });

        rbAccYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAccInclude="Yes";
                }
            }
        });

        rbAccNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAccInclude="No";
                }
            }
        });


    }

    private void clickEvent() {
        txtDeliveryDate.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void init() {
        mCalendar=Calendar.getInstance();
        booking_toolbar=(Toolbar)findViewById(R.id.bookingForm_toolbar);
        edtBookingId=(EditText) findViewById(R.id.formBooking_edtId);
        edtAmount=(EditText) findViewById(R.id.formBooking_edtAmount);
        txtDeliveryDate=(TextView) findViewById(R.id.formBooking_txtDeliveryDate);
        rbCash=(RadioButton) findViewById(R.id.formBooking_rbPaymentCash);
        rbCHQ=(RadioButton) findViewById(R.id.formBooking_rbPaymentCHQ);
        rbOther=(RadioButton) findViewById(R.id.formBooking_rbPaymentOther);
        rbExShowroomYes=(RadioButton) findViewById(R.id.formBooking_rbIncExShowroomYes);
        rbExShowroomNo=(RadioButton) findViewById(R.id.formBooking_rbIncExShowroomNo);
        rbInsuranceYes=(RadioButton) findViewById(R.id.formBooking_rbIncInsuranceYes);
        rbInsuranceNo=(RadioButton) findViewById(R.id.formBooking_rbIncInsuranceNo);
        rbRtoYes=(RadioButton) findViewById(R.id.formBooking_rbIncRtoYes);
        rbRtoNo=(RadioButton) findViewById(R.id.formBooking_rbIncRtoNo);
        rbAmcYes=(RadioButton) findViewById(R.id.formBooking_rbIncAmcYes);
        rbAmcNo=(RadioButton) findViewById(R.id.formBooking_rbIncAmcNo);
        rbExtraWarrantyYes=(RadioButton) findViewById(R.id.formBooking_rbIncEWYes);
        rbExtraWarrantyNo=(RadioButton) findViewById(R.id.formBooking_rbIncEWNo);
        rbMppYes=(RadioButton) findViewById(R.id.formBooking_rbIncMppYes);
        rbMppNo=(RadioButton) findViewById(R.id.formBooking_rbIncMppNo);
        rbAccYes=(RadioButton) findViewById(R.id.formBooking_rbIncAccYes);
        rbAccNo=(RadioButton) findViewById(R.id.formBooking_rbIncAccNo);
        txtCustomerName=(TextView) findViewById(R.id.formBooking_txtCustomerName);
        txtCustomerMobile=(TextView) findViewById(R.id.formBooking_txtCustomerMobile);
        txtModelName=(TextView) findViewById(R.id.formBooking_txtModelName);

        btnSave=(Button) findViewById(R.id.formBooking_btnSave);
        txtDeliveryDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(mCalendar.getTime()));
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR,year);
            mCalendar.set(Calendar.MONTH,monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            txtDeliveryDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(mCalendar.getTime()));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==txtDeliveryDate){
            datePickerDialog= new DatePickerDialog(ViewAddBookingForm.this, myDateListener, mCalendar
                    .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if (view==btnSave){
            callAddBookingAPI();
        }
    }

    private void callAddBookingAPI() {
        if (edtBookingId.getText().toString().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddBookingForm.this,"Please enter Booking Id");
        }else if (edtAmount.getText().toString().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddBookingForm.this,"Please enter amount");
        }else{
            if(CM.isInternetAvailable(ViewAddBookingForm.this)){
                if (isFromEditMode){
                    webcallForEditBooking(selectedBookingData.booking_primary_id,selectedBookingData.inquery_id,edtBookingId.getText().toString(),edtAmount.getText().toString(),txtDeliveryDate.getText().toString(),strPaymentType,isExShowroomInclude,isInsuranceInclude,isRtoInclude,isAmcInclude,isEWInclude,isMppInclude,isAccInclude);
                }else{
                    webcallForAddBooking(modelInquiryData.inquery_id,edtBookingId.getText().toString(),edtAmount.getText().toString(),txtDeliveryDate.getText().toString(),strPaymentType,isExShowroomInclude,isInsuranceInclude,isRtoInclude,isAmcInclude,isEWInclude,isMppInclude,isAccInclude);
                }
            }else{
                CM.showDialogueNoInternet(ViewAddBookingForm.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
            }
        }
    }

    private void webcallForAddBooking(String inquiryId, String bookingId, String amount, String deliveryDate, String paymentType, String isExShowroomInclude, String isInsuranceInclude, String isRtoInclude, String isAmcInclude, String isEWInclude, String isMppInclude, String isAccInclude) {
        String userId=CM.getSp(ViewAddBookingForm.this, CV.USER_ID,"").toString();

        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddBookingForm.this, true, true);

            WebService.addBooking(v,userId,inquiryId,bookingId,amount,deliveryDate,paymentType,isExShowroomInclude,isInsuranceInclude,isRtoInclude,isAmcInclude,isEWInclude,isMppInclude,isAccInclude, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddBooking(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddBookingForm.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void webcallForEditBooking(String bookingPrimaryId, String inquiryId, String bookingId, String amount, String deliveryDate, String paymentType, String isExShowroomInclude, String isInsuranceInclude, String isRtoInclude, String isAmcInclude, String isEWInclude, String isMppInclude, String isAccInclude) {
        String userId=CM.getSp(ViewAddBookingForm.this, CV.USER_ID,"").toString();

        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddBookingForm.this, true, true);

            WebService.editBooking(v,bookingPrimaryId,userId,inquiryId,bookingId,amount,deliveryDate,paymentType,isExShowroomInclude,isInsuranceInclude,isRtoInclude,isAmcInclude,isEWInclude,isMppInclude,isAccInclude, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddBooking(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddBookingForm.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForAddBooking(String response) {
        Log.i("REsponse",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);
        if (responsecode.equalsIgnoreCase("1")){
            String bookingId= CM.getValueFromJson(WebServiceTag.TAG_STR_BOOKING_PRIMARY_ID, response);
//            CM.ShowDialogue(ViewAddInquiry.this,msg);
            setResult(RESULT_OK);
            ViewAddBookingForm.this.finish();
        }else{
            CM.ShowDialogue(ViewAddBookingForm.this,msg);
        }
    }
}
