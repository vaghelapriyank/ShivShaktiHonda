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
import com.shivshaktihonda.app.pojo.ModelPriceListData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

/**
 * Created by abc on 12/14/2017.
 */

public class ViewSpPriceDetail extends AppCompatActivity implements View.OnClickListener {
    Toolbar priceDetail_toolbar;
    Button btnOk;
    MenuItem editMenu;
    Intent intent;
    ModelPriceListData selectedModelPriceList;
    TextView txtVariantName,txtExShowRoom,txtInsurance,txtRTO,
            txtExtraWar,txtAMC,txtMPP,txtACC,txtTotal;

    TextView txtIncentiveExShowRoom,txtIncentiveInsurance,txtIncentiveRTO,
            txtIncentiveExtraWar,txtIncentiveAMC,txtIncentiveMPP,txtIncentiveACC
            ,txtIncentiveBaseAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_price_detail);
        init();
        clickEvent();
        intent=getIntent();
        if (intent!=null){
            selectedModelPriceList=(ModelPriceListData)intent.getSerializableExtra(CV.SELECT_MODEL_PRICELIST);
        }
        priceDetail_toolbar.setPadding(0, CM.getStatusBarHeight(ViewSpPriceDetail.this),0,0);
        setSupportActionBar(priceDetail_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (selectedModelPriceList!=null){
            setPriceLIstValues(selectedModelPriceList);


        }


    }

    private void setPriceLIstValues(ModelPriceListData selectedModelPriceList) {
        getSupportActionBar().setTitle(selectedModelPriceList.model_name);

        txtVariantName.setText(selectedModelPriceList.veriant);
        txtExShowRoom.setText(selectedModelPriceList.ex_showroom);
        txtInsurance.setText(selectedModelPriceList.insurance);
        txtRTO.setText(selectedModelPriceList.rto);
        txtExtraWar.setText(selectedModelPriceList.extra_warranty);
        txtAMC.setText(selectedModelPriceList.amc);
        txtMPP.setText(selectedModelPriceList.mpp);
        txtACC.setText(selectedModelPriceList.acc);
        txtTotal.setText(selectedModelPriceList.total);

        txtIncentiveBaseAmount.setText(selectedModelPriceList.incentive);
        txtIncentiveExShowRoom.setText(selectedModelPriceList.ex_showroom_per);
        txtIncentiveInsurance.setText(selectedModelPriceList.insurance_per);
        txtIncentiveRTO.setText(selectedModelPriceList.rto_per);
        txtIncentiveAMC.setText(selectedModelPriceList.amc_per);
        txtIncentiveExtraWar.setText(selectedModelPriceList.ew_per);
        txtIncentiveMPP.setText(selectedModelPriceList.mpp_per);
        txtIncentiveACC.setText(selectedModelPriceList.acc_per);

    }

    private void clickEvent() {
        btnOk.setOnClickListener(this);
    }

    private void init() {
        priceDetail_toolbar=(Toolbar)findViewById(R.id.priceDetail_toolbar);
        btnOk=(Button) findViewById(R.id.priceDetail_btnOk);
        txtVariantName=(TextView) findViewById(R.id.priceDetail_txtVariant);
        txtExShowRoom=(TextView) findViewById(R.id.priceDetail_txtExShowroom);
        txtInsurance=(TextView) findViewById(R.id.priceDetail_txtInsurance);
        txtRTO=(TextView) findViewById(R.id.priceDetail_txtRTO);
        txtExtraWar=(TextView) findViewById(R.id.priceDetail_txtExtraWar);
        txtAMC=(TextView) findViewById(R.id.priceDetail_txtAmc);
        txtMPP=(TextView) findViewById(R.id.priceDetail_txtMPP);
        txtACC=(TextView) findViewById(R.id.priceDetail_txtAcc);
        txtTotal=(TextView) findViewById(R.id.priceDetail_txtTotal);

        txtIncentiveBaseAmount=(TextView) findViewById(R.id.priceDetail_txtInventiveAmount);
        txtIncentiveExShowRoom=(TextView) findViewById(R.id.priceDetail_txtIncentiveExshowroom);
        txtIncentiveInsurance=(TextView) findViewById(R.id.priceDetail_txtIncentiveInsurance);
        txtIncentiveRTO=(TextView) findViewById(R.id.priceDetail_txtIncentiveRTO);
        txtIncentiveAMC=(TextView) findViewById(R.id.priceDetail_txtIncentiveAMC);
        txtIncentiveExtraWar=(TextView) findViewById(R.id.priceDetail_txtIncentiveEW);
        txtIncentiveMPP=(TextView) findViewById(R.id.priceDetail_txtIncentiveMPP);
        txtIncentiveACC=(TextView) findViewById(R.id.priceDetail_txtIncentiveACC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_price,menu);
        editMenu=menu.findItem(R.id.action_edit);

        if ((boolean)CM.getSp(ViewSpPriceDetail.this, CV.IS_ADMIN,false)){
            editMenu.setVisible(true);
        }else{
            editMenu.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }else if (item.getItemId()==R.id.action_edit){
            Intent intent=new Intent(ViewSpPriceDetail.this,ViewEditPriceDetail.class);
            intent.putExtra(CV.SELECTED_MODEL_PRICELIST,selectedModelPriceList);
            CM.startActivityForResult(intent,500,ViewSpPriceDetail.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==btnOk){
            ViewSpPriceDetail.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==500 && resultCode==RESULT_OK){
            setResult(RESULT_OK);
            ViewSpPriceDetail.this.finish();
        }
    }
}
