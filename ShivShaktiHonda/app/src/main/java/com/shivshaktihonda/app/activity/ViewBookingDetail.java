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
import android.widget.LinearLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by abc on 12/13/2017.
 */

public class ViewBookingDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar bookingDetail_toolbar;
    Button btnDelivery;

    TextView txtBookingId,txtAmount,txtDeliveryDate,txtPaymentType;
    TextView txtIncExShowroom,txtIncInsurance,txtIncRTO,txtIncAMC,txtIncExtarwarranty,txtIncMPP,txtIncAcc,
            txtCustomerName,txtCustomerMobile,txtModelName;

    LinearLayout llSalespersonName;
    TextView txtSalesPersonName;

    Intent intent;
    ModelBookingData selectedBookingData;
    SimpleDateFormat toSdf,fromSdf;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_booking_detail);
        init();
        clickEvent();
        bookingDetail_toolbar.setPadding(0, CM.getStatusBarHeight(ViewBookingDetail.this),0,0);
        setSupportActionBar(bookingDetail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.booking_detail_title));

        intent=getIntent();
        if (intent!=null){
            selectedBookingData=(ModelBookingData)intent.getSerializableExtra(CV.SELECTED_BOOKING_DATA);
            if (selectedBookingData!=null){
                setBookingData(selectedBookingData);
            }
        }

    }

    private void setBookingData(ModelBookingData selectedBookingData) {
        txtBookingId.setText(selectedBookingData.booking_id);
        txtAmount.setText(selectedBookingData.amount);
        try {
            txtDeliveryDate.setText(toSdf.format(fromSdf.parse(selectedBookingData.delivery_datettime)) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtSalesPersonName.setText(selectedBookingData.salesperson_name);
        txtPaymentType.setText(selectedBookingData.payment_type);
        txtIncExShowroom.setText(selectedBookingData.incentive_ex_showroom);
        txtIncInsurance.setText(selectedBookingData.incentive_insurance);
        txtIncRTO.setText(selectedBookingData.incentive_rto);
        txtIncAMC.setText(selectedBookingData.incentive_amc);
        txtIncExtarwarranty.setText(selectedBookingData.incentive_ew);
        txtIncMPP.setText(selectedBookingData.incentive_mpp);
        txtIncAcc.setText(selectedBookingData.incentive_acc);
        txtCustomerMobile.setText(selectedBookingData.mobile_number);
        txtCustomerName.setText(selectedBookingData.first_name + " " +  selectedBookingData.last_name);
        txtModelName.setText(selectedBookingData.model_name);
    }

    private void clickEvent() {
        btnDelivery.setOnClickListener(this);
    }

    private void init() {
        toSdf=new SimpleDateFormat("dd MMM yyyy");
        fromSdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        bookingDetail_toolbar=(Toolbar)findViewById(R.id.bookingDetail_toolbar);
        btnDelivery=(Button) findViewById(R.id.bookingDetail_btnDelivery);
        txtBookingId=(TextView) findViewById(R.id.bookingDetail_txtBookingId);
        txtAmount=(TextView) findViewById(R.id.bookingDetail_txtBookingAmount);
        txtDeliveryDate=(TextView) findViewById(R.id.bookingDetail_txtDeliveryDate);
        txtPaymentType=(TextView) findViewById(R.id.bookingDetail_txtPaymentBy);
        txtIncExShowroom=(TextView) findViewById(R.id.bookingDetail_txtExShowroomStatus);
        txtIncInsurance=(TextView) findViewById(R.id.bookingDetail_txtInsuranceStatus);
        txtIncRTO=(TextView) findViewById(R.id.bookingDetail_txtRtoStatus);
        txtIncAMC=(TextView) findViewById(R.id.bookingDetail_txtAmcStatus);
        txtIncExtarwarranty=(TextView) findViewById(R.id.bookingDetail_txtEWStatus);
        txtIncMPP=(TextView) findViewById(R.id.bookingDetail_txtMppStatus);
        txtIncAcc=(TextView) findViewById(R.id.bookingDetail_txtAccStatus);
        txtCustomerName=(TextView) findViewById(R.id.bookingDetail_txtCustomerName);
        txtCustomerMobile=(TextView) findViewById(R.id.bookingDetail_txtCustomerMobile);
        txtModelName=(TextView) findViewById(R.id.bookingDetail_txtModelName);
        txtSalesPersonName=(TextView) findViewById(R.id.bookingDetail_txtSalesPersonName);
        llSalespersonName=(LinearLayout) findViewById(R.id.bookingDetail_llSalesPersonName);

        if ((boolean)CM.getSp(ViewBookingDetail.this,CV.IS_ADMIN,false)){
            btnDelivery.setVisibility(View.GONE);
            llSalespersonName.setVisibility(View.VISIBLE);
        }else{
            llSalespersonName.setVisibility(View.GONE);
            btnDelivery.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view==btnDelivery){
            Intent intent=new Intent(ViewBookingDetail.this,ViewAddDeliveryForm.class);
            intent.putExtra(CV.SELECTED_BOOKING,selectedBookingData);
            CM.startActivityForResult(intent,2500,ViewBookingDetail.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_booking,menu);
        MenuItem editMenu=menu.findItem(R.id.action_edit);
        MenuItem deleteMenu=menu.findItem(R.id.action_delete);
        if ((boolean)CM.getSp(ViewBookingDetail.this,CV.IS_ADMIN,false)){
            editMenu.setVisible(false);
            deleteMenu.setVisible(false);
        }else{
            editMenu.setVisible(true);
            deleteMenu.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }else if (item.getItemId()==R.id.action_edit){
            Intent intent=new Intent(ViewBookingDetail.this,ViewAddBookingForm.class);
            intent.putExtra(CV.SELECTED_BOOKING_DATA,selectedBookingData);
            intent.putExtra(CV.IS_FROM_EDIT_MODE,true);
            CM.startActivityForResult(intent,500,ViewBookingDetail.this);
        }else if (item.getItemId()==R.id.action_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookingDetail.this);
            builder.setMessage("Are you sure, you want to delete this booking?")
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.common_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            deleteBookingRecordAPI(selectedBookingData.booking_primary_id);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.common_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBookingRecordAPI(String booking_primary_id) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewBookingDetail.this, true, true);

            WebService.deleteBooking(v,booking_primary_id, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForDeleteBooking(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewBookingDetail.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForDeleteBooking(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        if (responsecode.equalsIgnoreCase("1")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookingDetail.this);
            builder.setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setResult(500);
                            ViewBookingDetail.this.finish();
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else if (responsecode.equalsIgnoreCase("0")){
            CM.ShowDialogue(ViewBookingDetail.this,msg);
        }else if (responsecode.equalsIgnoreCase("-11")){
            CM.ShowDialogue(ViewBookingDetail.this,msg);
        }else{
            CM.ShowDialogue(ViewBookingDetail.this,msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2500 && resultCode==RESULT_OK){
            setResult(RESULT_OK);
            ViewBookingDetail.this.finish();
        }else if (requestCode==500 && resultCode==RESULT_OK){
            setResult(500);
            ViewBookingDetail.this.finish();
        }
    }
}
