package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelInquiryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 12/10/2017.
 */

public class ViewInquiryDetail extends AppCompatActivity implements View.OnClickListener {
    Button btnBook;
    Toolbar toolbar;

    TextView txtSalespersonName,txtName,txtMobileNumber,txtAddress,txtModelName,txtVehicleType,txtVariantName,txtVehicleColor,
            txtInquiryTitle,txtCustomerType,txtPurchaseType,txtFeedback;

    LinearLayout llSalespersonDetail;

    Intent intent;
    ModelInquiryData selectedInquiryDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inquiry_detail);
        init();
        clickEvent();

        toolbar.setPadding(0, CM.getStatusBarHeight(ViewInquiryDetail.this),0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Inquiry_detail_screen_title));

        intent=getIntent();
        if (intent!=null){
            selectedInquiryDate=(ModelInquiryData)intent.getSerializableExtra(CV.SELECTED_INQUIRY_DATA);
            if (selectedInquiryDate!=null){
                setInquiryValues(selectedInquiryDate);
            }
        }

    }

    private void setInquiryValues(ModelInquiryData selectedInquiryDate) {
        txtName.setText(selectedInquiryDate.first_name+" "+selectedInquiryDate.middle_name+" "+selectedInquiryDate.last_name);
        txtMobileNumber.setText(selectedInquiryDate.mobile_number);
        txtAddress.setText(selectedInquiryDate.address);
        txtModelName.setText(selectedInquiryDate.model_name);
        txtVehicleType.setText(selectedInquiryDate.vehicle_type);
        txtVariantName.setText(selectedInquiryDate.model_type_name);
        txtVehicleColor.setText(selectedInquiryDate.color);
        txtInquiryTitle.setText(selectedInquiryDate.inquiry_type);
        txtCustomerType.setText(selectedInquiryDate.customer_type);
        txtPurchaseType.setText(selectedInquiryDate.purchase_type);
        txtFeedback.setText(selectedInquiryDate.feedback);
        txtSalespersonName.setText(selectedInquiryDate.salesperson_name);
    }

    private void clickEvent() {
        btnBook.setOnClickListener(this);
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.inquiryDetail_toolbar);
        btnBook=(Button)findViewById(R.id.inquiryDetail_btnBooking);
        txtName=(TextView) findViewById(R.id.inquiryDetail_txtName);
        txtMobileNumber=(TextView) findViewById(R.id.inquiryDetail_txtMobileNumber);
        txtAddress=(TextView) findViewById(R.id.inquiryDetail_txtAddress);
        txtModelName=(TextView) findViewById(R.id.inquiryDetail_txtVehicleModel);
        txtVehicleType=(TextView) findViewById(R.id.inquiryDetail_txtVehicleType);
        txtVariantName=(TextView) findViewById(R.id.inquiryDetail_txtVehicleVariant);
        txtVehicleColor=(TextView) findViewById(R.id.inquiryDetail_txtVehicleColor);
        txtInquiryTitle=(TextView) findViewById(R.id.inquiryDetail_txtInquiryTitle);
        txtCustomerType=(TextView) findViewById(R.id.inquiryDetail_txtCustomerType);
        txtPurchaseType=(TextView) findViewById(R.id.inquiryDetail_txtPurchaseType);
        txtFeedback=(TextView) findViewById(R.id.inquiryDetail_txtFeedback);
        llSalespersonDetail=(LinearLayout) findViewById(R.id.inquiryDetail_llSalespersonDetail);
        txtSalespersonName=(TextView) findViewById(R.id.inquiryDetail_txtSalespersonName);

        if ((boolean)CM.getSp(ViewInquiryDetail.this,CV.IS_ADMIN,false)){
            btnBook.setVisibility(View.GONE);
            llSalespersonDetail.setVisibility(View.VISIBLE);
        }else{
            btnBook.setVisibility(View.VISIBLE);
            llSalespersonDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_price,menu);
        MenuItem editMenu=menu.findItem(R.id.action_edit);

        if ((boolean)CM.getSp(ViewInquiryDetail.this,CV.IS_ADMIN,false)){
            editMenu.setVisible(false);
        }else{
            editMenu.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        if (view==btnBook){
            Intent intent=new Intent(ViewInquiryDetail.this,ViewAddBookingForm.class);
//            intent.putExtra(CV.SELECtED_INQUIRY_ID,selectedInquiryDate.inquery_id);
            intent.putExtra(CV.SELECtED_INQUIRY,selectedInquiryDate);
            CM.startActivityForResult(intent,1500,ViewInquiryDetail.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }else if (item.getItemId()==R.id.action_edit){
            Intent editIntent=new Intent(ViewInquiryDetail.this,ViewAddInquiry.class);
            editIntent.putExtra(CV.SELECTED_INQUIRY_DATA,selectedInquiryDate);
            editIntent.putExtra(CV.IS_FROM_EDIT_MODE,true);
            CM.startActivityForResult(editIntent,200,ViewInquiryDetail.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1500&& resultCode==RESULT_OK){
            setResult(RESULT_OK);
            ViewInquiryDetail.this.finish();
        }else if (requestCode==200 && resultCode==RESULT_OK){
            setResult(111);
            ViewInquiryDetail.this.finish();
        }
    }
}
