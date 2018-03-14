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
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelGetAllSalespersonData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 12/25/2017.
 */

public class ViewSalesPersonDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView txtSPName,txtSPEmail,txtSPMobileNumber,txtSPUserName,txtSPPassword;
    Button btnOk;
    Intent intent;
    ModelGetAllSalespersonData selectedSalesPerson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_salesperson_detail);
        init();
        clickEvent();
        toolbar.setPadding(0, CM.getStatusBarHeight(ViewSalesPersonDetail.this),0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.salespersondetail_screen_title));

        intent=getIntent();
        if (intent!=null){
            selectedSalesPerson=(ModelGetAllSalespersonData)intent.getSerializableExtra(CV.SELECTED_SALESPERSONDATA);
        }

        if (selectedSalesPerson!=null){
            setSalespersonResult(selectedSalesPerson);
        }

    }

    private void clickEvent() {
        btnOk.setOnClickListener(this);
    }

    private void setSalespersonResult(ModelGetAllSalespersonData selectedSalesPerson) {
         txtSPName.setText(selectedSalesPerson.name);
         txtSPEmail.setText(selectedSalesPerson.email);
         txtSPMobileNumber.setText(selectedSalesPerson.mobile_number);
         txtSPUserName.setText(selectedSalesPerson.user_name);
         txtSPPassword.setText(selectedSalesPerson.password);
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.aspd_toolbar);
        txtSPName=(TextView) findViewById(R.id.aspd_txtSalespersonName);
        txtSPEmail=(TextView) findViewById(R.id.aspd_txtSalespersonEmail);
        txtSPMobileNumber=(TextView) findViewById(R.id.aspd_txtSalespersonMobileNumber);
        txtSPUserName=(TextView) findViewById(R.id.aspd_txtUserName);
        txtSPPassword=(TextView) findViewById(R.id.aspd_txtPassword);
        btnOk=(Button) findViewById(R.id.aspd_btnOk);
    }

    @Override
    public void onClick(View view) {
        if (view==btnOk){
            ViewSalesPersonDetail.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_price,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }else if (item.getItemId()==R.id.action_edit){
            Intent intent=new Intent(ViewSalesPersonDetail.this,ViewAddSalesPerson.class);
            intent.putExtra(CV.SELECTED_SALESPERSONDATA,selectedSalesPerson);
            intent.putExtra(CV.IS_FROM_EDIT_MODE,true);
            CM.startActivityForResult(intent,500,ViewSalesPersonDetail.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==500 && resultCode==RESULT_OK){
            setResult(RESULT_OK);
            ViewSalesPersonDetail.this.finish();
        }
    }
}
