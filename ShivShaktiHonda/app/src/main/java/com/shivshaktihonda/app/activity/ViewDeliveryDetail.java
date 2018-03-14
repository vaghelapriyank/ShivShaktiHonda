package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelDeliveryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 12/13/2017.
 */

public class ViewDeliveryDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar deliveryDetail_toolbar;

    TextView txtEngineNumber,txtChesisNumber,txtBattryNumber,txtKeyNumber,txtDCNumber,txtPriceTotal,
            txtPriceDiscount,txtPriceDifference,txtPaymentType;

    TextView txtCustomerName,txtCustomerNumber,txtModelName;
    TextView txtSalespersonName,txtBookingAmount;
    LinearLayout llSalesPersonName;

    Button btnOk;

    Intent intent;
    ModelDeliveryData selectedDeliveryData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delivery_detail);
        init();
        clickEvent();
        deliveryDetail_toolbar.setPadding(0, CM.getStatusBarHeight(ViewDeliveryDetail.this),0,0);
        setSupportActionBar(deliveryDetail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.delivery_detail_title));

        intent=getIntent();
        if (intent!=null){
            selectedDeliveryData=(ModelDeliveryData)intent.getSerializableExtra(CV.SELECTED_DELIVERY_DATA);
            if (selectedDeliveryData!=null){
                setDeliveryValues(selectedDeliveryData);
            }
        }

    }

    private void clickEvent() {
        btnOk.setOnClickListener(this);
    }

    private void setDeliveryValues(ModelDeliveryData selectedDeliveryData) {
        txtSalespersonName.setText(selectedDeliveryData.salesperson_name);
        txtEngineNumber.setText(selectedDeliveryData.engin_number);
        txtChesisNumber.setText(selectedDeliveryData.chesis_number);
        txtBattryNumber.setText(selectedDeliveryData.battery_number);
        txtKeyNumber.setText(selectedDeliveryData.key_number);
        txtDCNumber.setText(selectedDeliveryData.dc_number);
        txtPriceTotal.setText(selectedDeliveryData.total);
        txtPriceDifference.setText(selectedDeliveryData.diff);
        txtPriceDiscount.setText(selectedDeliveryData.disc);
        txtPaymentType.setText(selectedDeliveryData.payment_type);
        txtBookingAmount.setText(selectedDeliveryData.booking_amount);

        txtCustomerName.setText(selectedDeliveryData.first_name+" "+selectedDeliveryData.last_name);
        txtCustomerNumber.setText(selectedDeliveryData.mobile_number);
        txtModelName.setText(selectedDeliveryData.model_name);


    }

    private void init() {
        deliveryDetail_toolbar=(Toolbar)findViewById(R.id.deliveryDetail_toolbar);
        txtEngineNumber=(TextView) findViewById(R.id.deliveryDetail_txtEngineNumber);
        txtChesisNumber=(TextView) findViewById(R.id.deliveryDetail_txtChesisNumber);
        txtBattryNumber=(TextView) findViewById(R.id.deliveryDetail_txtBatteryNumber);
        txtKeyNumber=(TextView) findViewById(R.id.deliveryDetail_txtKeyNumber);
        txtDCNumber=(TextView) findViewById(R.id.deliveryDetail_txtDCNumber);
        txtPriceTotal=(TextView) findViewById(R.id.deliveryDetail_txtTotalPrice);
        txtPriceDiscount=(TextView) findViewById(R.id.deliveryDetail_txtDiscountPrice);
        txtPriceDifference=(TextView) findViewById(R.id.deliveryDetail_txtDifferencePrice);
        txtPaymentType=(TextView) findViewById(R.id.deliveryDetail_txtPaymentType);
        txtCustomerName=(TextView) findViewById(R.id.deliveryDetail_txtCustomerName);
        txtCustomerNumber=(TextView) findViewById(R.id.deliveryDetail_txtCustomerMobile);
        txtModelName=(TextView) findViewById(R.id.deliveryDetail_txtModelName);
        txtSalespersonName=(TextView) findViewById(R.id.deliveryDetail_txtSalesPerson);
        txtBookingAmount=(TextView) findViewById(R.id.deliveryDetail_txtBookingAmount);
        llSalesPersonName=(LinearLayout) findViewById(R.id.deliveryDetail_llSalesPerson);
        btnOk=(Button) findViewById(R.id.deliveryDetail_btnOk);
        if ((boolean)CM.getSp(ViewDeliveryDetail.this,CV.IS_ADMIN,false)){
            llSalesPersonName.setVisibility(View.VISIBLE);
        }else{
            llSalesPersonName.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view==btnOk){
            ViewDeliveryDetail.this.finish();
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
