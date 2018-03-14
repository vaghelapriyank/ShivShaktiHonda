package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.adaptor.AdptSpinnerModelColor;
import com.shivshaktihonda.app.adaptor.AdptSpinnerModelName;
import com.shivshaktihonda.app.adaptor.AdptSpinnerModelVariant;
import com.shivshaktihonda.app.pojo.ModelInquiryData;
import com.shivshaktihonda.app.pojo.ModelUser;
import com.shivshaktihonda.app.pojo.ModelUserData;
import com.shivshaktihonda.app.pojo.ModelVehicleColorData;
import com.shivshaktihonda.app.pojo.ModelVehicles;
import com.shivshaktihonda.app.pojo.ModelVehiclesData;
import com.shivshaktihonda.app.pojo.ModelVeriantData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by abc on 12/9/2017.
 */

public class ViewAddInquiry extends AppCompatActivity {
    Toolbar inquiryForm_toolbar;
    EditText edtFirstName,edtMiddleName,edtLastName,edtMobileNumber,edtAddress,edtTahsil,edtDistict;
    RadioGroup rgVehicleType,rgInquiryType,rgCustomerType;
    RadioButton rbSCVehicleType,rbMCVehicleType,rbDemoVan,rbNews,rbReference,rbWalkIn,
            rbCustHot,rbCustCold,rbPurchCash,rbPurchFinance,rbPurchExchange,rbPurchOther,rbCustWarm,
            rbTestDriveYes,rbTestDriveNo;
    EditText edtModelName,edtVariantName,edtVehicleColor;
    EditText edtFeedback;
    Button btnSave;
    String strVehicleType="SC",strInquiryType="demovan",strCustomerType="hot",strPurchaseType="cash",strTestDrive="Yes";
    Spinner spModels,spVeriant,spColors;
    ArrayList<ModelVehiclesData> modelListData;
    ArrayList<ModelVeriantData> modelVariantData;
    ArrayList<ModelVehicleColorData> modelColorData;
    AdptSpinnerModelName vehiclesDataArrayAdapter;
    AdptSpinnerModelVariant veriantDataArrayAdapter;
    AdptSpinnerModelColor colorDataArrayAdapter;
    String modelNameId="",modelVeriantId="",modelColorId="";
    Intent intent;
    ModelInquiryData selectedInquiryDataForEdit;
    boolean isFromEditMode=false;
    int veriantPos=0,colorpos=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inquiry_form);
        init();
        changeListener();
        clickEvent();
        inquiryForm_toolbar.setPadding(0, CM.getStatusBarHeight(ViewAddInquiry.this),0,0);
        setSupportActionBar(inquiryForm_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Inquiry_form_screen_title));

        intent=getIntent();
        if (intent!=null){
            selectedInquiryDataForEdit=(ModelInquiryData)intent.getSerializableExtra(CV.SELECTED_INQUIRY_DATA);
            isFromEditMode=intent.getBooleanExtra(CV.IS_FROM_EDIT_MODE,false);
        }

        if(CM.isInternetAvailable(ViewAddInquiry.this)){
            webcallForGetModels();
        }else{
            CM.showDialogueNoInternet(ViewAddInquiry.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }

        if (isFromEditMode){
            setInquiryDataForEdit(selectedInquiryDataForEdit);
        }

    }

    private void setInquiryDataForEdit(ModelInquiryData selectedInquiryDataForEdit) {
        edtFirstName.setText(selectedInquiryDataForEdit.first_name);
        edtMiddleName.setText(selectedInquiryDataForEdit.middle_name);
        edtLastName.setText(selectedInquiryDataForEdit.last_name);
        edtMobileNumber.setText(selectedInquiryDataForEdit.mobile_number);
        edtAddress.setText(selectedInquiryDataForEdit.address);
        edtTahsil.setText(selectedInquiryDataForEdit.tahsil);
        edtDistict.setText(selectedInquiryDataForEdit.distict);

        if (selectedInquiryDataForEdit.vehicle_type.equalsIgnoreCase("SC")){
            rbSCVehicleType.setChecked(true);
            rbMCVehicleType.setChecked(false);
        }else{
            rbMCVehicleType.setChecked(true);
            rbSCVehicleType.setChecked(false);
        }

        if (selectedInquiryDataForEdit.inquiry_type.equalsIgnoreCase("demovan")){
            rbDemoVan.setChecked(true);
            rbNews.setChecked(false);
            rbReference.setChecked(false);
            rbWalkIn.setChecked(false);
        }else if (selectedInquiryDataForEdit.inquiry_type.equalsIgnoreCase("new")){
            rbNews.setChecked(true);
            rbDemoVan.setChecked(false);
            rbReference.setChecked(false);
            rbWalkIn.setChecked(false);
        }else if (selectedInquiryDataForEdit.inquiry_type.equalsIgnoreCase("reference")){
            rbNews.setChecked(false);
            rbDemoVan.setChecked(false);
            rbReference.setChecked(true);
            rbWalkIn.setChecked(false);
        }else if (selectedInquiryDataForEdit.inquiry_type.equalsIgnoreCase("walk-in")){
            rbNews.setChecked(false);
            rbDemoVan.setChecked(false);
            rbReference.setChecked(false);
            rbWalkIn.setChecked(true);
        }

        if (selectedInquiryDataForEdit.customer_type.equalsIgnoreCase("hot")){
            rbCustHot.setChecked(true);
            rbCustCold.setChecked(false);
            rbCustWarm.setChecked(false);
        }else if (selectedInquiryDataForEdit.customer_type.equalsIgnoreCase("cold")){
            rbCustHot.setChecked(false);
            rbCustCold.setChecked(true);
            rbCustWarm.setChecked(false);
        }else if (selectedInquiryDataForEdit.customer_type.equalsIgnoreCase("warm")){
            rbCustHot.setChecked(false);
            rbCustCold.setChecked(false);
            rbCustWarm.setChecked(true);
        }

        if (selectedInquiryDataForEdit.purchase_type.equalsIgnoreCase("cash")){
            rbPurchCash.setChecked(true);
            rbPurchExchange.setChecked(false);
            rbPurchFinance.setChecked(false);
            rbPurchOther.setChecked(false);
        }else if (selectedInquiryDataForEdit.purchase_type.equalsIgnoreCase("finance")){
            rbPurchCash.setChecked(false);
            rbPurchExchange.setChecked(false);
            rbPurchFinance.setChecked(true);
            rbPurchOther.setChecked(false);
        }else if (selectedInquiryDataForEdit.purchase_type.equalsIgnoreCase("exchange")){
            rbPurchCash.setChecked(false);
            rbPurchExchange.setChecked(true);
            rbPurchFinance.setChecked(false);
            rbPurchOther.setChecked(false);
        }else if (selectedInquiryDataForEdit.purchase_type.equalsIgnoreCase("other")){
            rbPurchCash.setChecked(false);
            rbPurchExchange.setChecked(false);
            rbPurchFinance.setChecked(false);
            rbPurchOther.setChecked(true);
        }

/*        edtModelName.setText(selectedInquiryDataForEdit.model_name);
        modelNameId=selectedInquiryDataForEdit.model_id;

        edtVariantName.setText(selectedInquiryDataForEdit.model_type_name);
        modelVeriantId=selectedInquiryDataForEdit.model_type_id;

        edtVehicleColor.setText(selectedInquiryDataForEdit.color);
        modelColorId=selectedInquiryDataForEdit.color;*/

/*        if (selectedInquiryDataForEdit..equalsIgnoreCase("cash")){

        }*/

    }

    private void clickEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFromEditMode){
                    sendInquiryFormForEdit();
                }else{
                    sendInquiryForm();
                }
            }
        });
    }

    private void sendInquiryFormForEdit() {
        if (edtFirstName.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_first_name_empty));
        }else if (edtLastName.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_last_name_empty));
        }else if (edtMobileNumber.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_mobile_number_empty));
        }else if (edtAddress.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_address_empty));
        }else if (edtTahsil.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_tahsil_empty));
        }else if (edtDistict.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_distict_empty));
        }else if (strVehicleType.trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_vehicle_type_empty));
        }else if (modelNameId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_model_name_empty));
        }else if (modelVeriantId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_variant_model_empty));
        }else if (modelColorId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_vehicle_color_empty));
        }else{
            webcallForAddInquiryFormForEdit(selectedInquiryDataForEdit.inquery_id,edtFirstName.getText().toString(),edtMiddleName.getText().toString(),edtLastName.getText().toString(),
                    edtMobileNumber.getText().toString(),edtAddress.getText().toString(),edtTahsil.getText().toString(),edtDistict.getText().toString(),
                    strVehicleType,modelNameId,modelVeriantId,modelColorId,
                    strInquiryType,strCustomerType,strPurchaseType,strTestDrive,edtFeedback.getText().toString());
        }
    }


    private void sendInquiryForm() {
        if (edtFirstName.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_first_name_empty));
        }else if (edtLastName.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_last_name_empty));
        }else if (edtMobileNumber.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_mobile_number_empty));
        }else if (edtAddress.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_address_empty));
        }else if (edtTahsil.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_tahsil_empty));
        }else if (edtDistict.getText().toString().trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_distict_empty));
        }else if (strVehicleType.trim().equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_vehicle_type_empty));
        }else if (modelNameId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_model_name_empty));
        }else if (modelVeriantId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_variant_model_empty));
        }else if (modelColorId.equalsIgnoreCase("")){
            CM.ShowDialogue(ViewAddInquiry.this,getResources().getString(R.string.err_msg_vehicle_color_empty));
        }else{
            webcallForAddInquiryForm(edtFirstName.getText().toString(),edtMiddleName.getText().toString(),edtLastName.getText().toString(),
                    edtMobileNumber.getText().toString(),edtAddress.getText().toString(),edtTahsil.getText().toString(),edtDistict.getText().toString(),
                    strVehicleType,modelNameId,modelVeriantId,modelColorId,
                    strInquiryType,strCustomerType,strPurchaseType,strTestDrive,edtFeedback.getText().toString());
        }
    }

    private void webcallForAddInquiryFormForEdit(String inquiryId, String firstName, String middleName, String lastName, String mobileNumber, String address, String tahsil, String distict, String strVehicleType, String modelName, String modelVariant, String vehicleColor, String strInquiryType, String strCustomerType, String strPurchaseType, String strTestDrive, String feedback) {
        String userId=CM.getSp(ViewAddInquiry.this,CV.USER_ID,"").toString();
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddInquiry.this, true, true);

            WebService.editInquiry(v,inquiryId,userId, firstName, middleName,lastName,mobileNumber,address,tahsil,distict,strVehicleType,modelName,modelVariant,vehicleColor,strInquiryType,strCustomerType,strPurchaseType,strTestDrive,feedback, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddInquiry(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddInquiry.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void webcallForAddInquiryForm(String firstName, String middleName, String lastName, String mobileNumber, String address, String tahsil, String distict, String strVehicleType, String modelName, String modelVariant, String vehicleColor, String strInquiryType, String strCustomerType, String strPurchaseType, String strTestDrive, String feedback) {
        String userId=CM.getSp(ViewAddInquiry.this,CV.USER_ID,"").toString();
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddInquiry.this, true, true);

            WebService.addInquiry(v,userId, firstName, middleName,lastName,mobileNumber,address,tahsil,distict,strVehicleType,modelName,modelVariant,vehicleColor,strInquiryType,strCustomerType,strPurchaseType,strTestDrive,feedback, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAddInquiry(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddInquiry.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForAddInquiry(String response) {
        Log.i("REsponse",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);
        if (responsecode.equalsIgnoreCase("1")){
            String inquiryId = CM.getValueFromJson(WebServiceTag.TAG_STR_INQUIRY_ID, response);
//            CM.ShowDialogue(ViewAddInquiry.this,msg);
            setResult(RESULT_OK);
            ViewAddInquiry.this.finish();
        }else{
            CM.ShowDialogue(ViewAddInquiry.this,msg);
        }
    }

    private void changeListener() {
        rbSCVehicleType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strVehicleType = "SC";
                }
            }
        });

        rbMCVehicleType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strVehicleType = "MC";
                }
            }
        });

        rbDemoVan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbNews.setChecked(false);
                    rbReference.setChecked(false);
                    rbWalkIn.setChecked(false);
                    strInquiryType= "demovan";
                }
            }
        });

        rbNews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbDemoVan.setChecked(false);
                    rbReference.setChecked(false);
                    rbWalkIn.setChecked(false);
                    strInquiryType= "new";
                }
            }
        });

        rbReference.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbNews.setChecked(false);
                    rbDemoVan.setChecked(false);
                    rbWalkIn.setChecked(false);
                    strInquiryType= "reference";
                }
            }
        });

        rbWalkIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbNews.setChecked(false);
                    rbReference.setChecked(false);
                    rbDemoVan.setChecked(false);
                    strInquiryType= "walk-in";
                }
            }
        });

        rbCustHot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strCustomerType= "hot";
                }
            }
        });

        rbCustCold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strCustomerType= "cold";
                }
            }
        });

        rbCustWarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strCustomerType= "warm";
                }
            }
        });

        rbPurchCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbPurchFinance.setChecked(false);
                    rbPurchExchange.setChecked(false);
                    rbPurchOther.setChecked(false);
                    strPurchaseType= "cash";
                }
            }
        });

        rbPurchFinance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbPurchCash.setChecked(false);
                    rbPurchExchange.setChecked(false);
                    rbPurchOther.setChecked(false);
                    strPurchaseType= "finance";
                }
            }
        });

        rbPurchExchange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbPurchCash.setChecked(false);
                    rbPurchExchange.setChecked(false);
                    rbPurchOther.setChecked(false);
                    strPurchaseType= "exchange";
                }
            }
        });

        rbPurchOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbPurchFinance.setChecked(false);
                    rbPurchExchange.setChecked(false);
                    rbPurchCash.setChecked(false);
                    strPurchaseType= "other";
                }
            }
        });

        rbTestDriveYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strTestDrive= "Yes";
                }
            }
        });

        rbTestDriveNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strTestDrive= "No";
                }
            }
        });

    }

    private void webcallForGetModels() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAddInquiry.this, true, true);

            WebService.getAllModels(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAllModels(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewAddInquiry.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForAllModels(String response) {
        Log.i("REsponse",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);
        final ArrayList<ModelVehiclesData> vehicleData=new ArrayList<>();

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelVehicles mainModel=CM.JsonParse(new ModelVehicles(),response);
                modelListData=mainModel.vehicleDataArray;
                ModelVehiclesData firstData=new ModelVehiclesData();
                firstData.model_id="0";
                firstData.model_name="Select Model";
                vehicleData.add(firstData);
                vehicleData.addAll(modelListData);
                vehiclesDataArrayAdapter=new AdptSpinnerModelName(ViewAddInquiry.this,R.layout.spinner_textview,vehicleData);
                spModels.setAdapter(vehiclesDataArrayAdapter);

                spModels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i!=0 ){
                            modelNameId=vehicleData.get(i).model_id;
                            final ArrayList<ModelVeriantData> tempVerintData=new ArrayList<>();
                            ModelVeriantData sampleVariantData=new ModelVeriantData();
                            sampleVariantData.model_type_id="0";
                            sampleVariantData.model_type_name="Select Variant";
                            modelVariantData = vehicleData.get(i).verientData;
                            tempVerintData.add(sampleVariantData);
                            tempVerintData.addAll(modelVariantData);
                            veriantDataArrayAdapter=new AdptSpinnerModelVariant(ViewAddInquiry.this,R.layout.spinner_textview,tempVerintData);
                            spVeriant.setAdapter(veriantDataArrayAdapter);
                            spVeriant.setSelection(veriantPos);
                            spVeriant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i!=0){
                                        veriantPos=i;
                                        modelVeriantId=tempVerintData.get(i).model_type_id;
                                    }else{
                                        modelVeriantId="";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    modelVeriantId="";
                                }
                            });

                            //Set Color Data
                            final ArrayList<ModelVehicleColorData> tempVehicleColor=new ArrayList<>();
                            ModelVehicleColorData sampleVehicleColor=new ModelVehicleColorData();
                            sampleVehicleColor.model_color_id="0";
                            sampleVehicleColor.model_color_name="Select Color";
                            modelColorData = vehicleData.get(i).colorData;
                            tempVehicleColor.add(sampleVehicleColor);
                            tempVehicleColor.addAll(modelColorData);
                            colorDataArrayAdapter=new AdptSpinnerModelColor(ViewAddInquiry.this,R.layout.spinner_textview,tempVehicleColor);
                            spColors.setAdapter(colorDataArrayAdapter);
                            spColors.setSelection(colorpos);
                            spColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i!=0){
                                        colorpos=i;
                                        modelColorId=tempVehicleColor.get(i).model_color_id;
                                    }else{
                                        modelColorId="";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    modelColorId="";
                                }
                            });
                        }else{
                            modelNameId="";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        modelNameId="";
                    }
                });
                if (isFromEditMode){
                    if (modelListData!=null){
                        for (int i=0;i<modelListData.size();i++){
                            if (modelListData.get(i).model_name.equalsIgnoreCase(selectedInquiryDataForEdit.model_name)){
                                spModels.setSelection(i+1);
                                modelNameId=modelListData.get(i).model_id;
                                ArrayList<ModelVeriantData> tempVeriantDatas=new ArrayList<>();
                                ArrayList<ModelVehicleColorData> tempColorData=new ArrayList<>();
                                tempVeriantDatas=modelListData.get(i).verientData;
                                tempColorData=modelListData.get(i).colorData;

                                if (tempVeriantDatas!=null){
                                    for (int j=0;j<tempVeriantDatas.size();j++){
                                        if (tempVeriantDatas.get(j).model_type_name.equalsIgnoreCase(selectedInquiryDataForEdit.model_type_name)){
                                            veriantPos=j+1;
                                            spVeriant.setSelection(j+1);
                                            modelVeriantId=tempVeriantDatas.get(j).model_type_id;
                                        }
                                    }
                                }

                                if (tempColorData!=null){
                                    for (int j=0;j<tempColorData.size();j++){
                                        if (tempColorData.get(j).model_color_name.equalsIgnoreCase(selectedInquiryDataForEdit.color)){
                                            colorpos=j+1;
                                            spColors.setSelection(j+1);
                                            modelColorId=tempColorData.get(j).model_color_id;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }else{
                CM.ShowDialogue(ViewAddInquiry.this,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


    }

    private void init() {
        modelListData=new ArrayList<>();
        modelVariantData=new ArrayList<>();
        modelColorData=new ArrayList<>();
        inquiryForm_toolbar=(Toolbar)findViewById(R.id.inquiryForm_toolbar);
        edtFirstName=(EditText)findViewById(R.id.formInquiry_edtFirstName);
        edtMiddleName=(EditText)findViewById(R.id.formInquiry_edtMiddleName);
        edtLastName=(EditText)findViewById(R.id.formInquiry_edtLastName);
        edtMobileNumber=(EditText)findViewById(R.id.formInquiry_edtMobileNo);
        edtAddress=(EditText)findViewById(R.id.formInquiry_edtAddress);
        edtTahsil=(EditText)findViewById(R.id.formInquiry_edtTahsil);
        edtDistict=(EditText)findViewById(R.id.formInquiry_edtDistict);
//        edtModelName=(EditText)findViewById(R.id.formInquiry_edtModel);
//        edtVariantName=(EditText)findViewById(R.id.formInquiry_edtVariant);
//        edtVehicleColor=(EditText)findViewById(R.id.formInquiry_edtColor);
        edtFeedback=(EditText)findViewById(R.id.formInquiry_edtFeedback);
        rbSCVehicleType=(RadioButton)findViewById(R.id.formInquiry_rbVehicleSC);
        rbMCVehicleType=(RadioButton)findViewById(R.id.formInquiry_rbVehicleMC);
        rbDemoVan=(RadioButton)findViewById(R.id.formInquiry_rbInqTypeDemoVan);
        rbNews=(RadioButton)findViewById(R.id.formInquiry_rbInqTypeNews);
        rbReference=(RadioButton)findViewById(R.id.formInquiry_rbInqTypeReference);
        rbWalkIn=(RadioButton)findViewById(R.id.formInquiry_rbInqTypeWalkIn);
        rbCustHot=(RadioButton)findViewById(R.id.formInquiry_rbCustHot);
        rbCustCold=(RadioButton)findViewById(R.id.formInquiry_rbCustCold);
        rbCustWarm=(RadioButton)findViewById(R.id.formInquiry_rbCustWarm);
        rbPurchCash=(RadioButton)findViewById(R.id.formInquiry_rbPurchaseCash);
        rbPurchFinance=(RadioButton)findViewById(R.id.formInquiry_rbPurchaseFinance);
        rbPurchExchange=(RadioButton)findViewById(R.id.formInquiry_rbPurchaseExchange);
        rbPurchOther=(RadioButton)findViewById(R.id.formInquiry_rbPurchaseOther);
        rbTestDriveYes=(RadioButton)findViewById(R.id.formInquiry_rbTestDriveYes);
        rbTestDriveNo=(RadioButton)findViewById(R.id.formInquiry_rbTestDriveNo);
        btnSave=(Button) findViewById(R.id.formInquiry_btnSave);
        spModels=(Spinner) findViewById(R.id.formInquiry_spModels);
        spVeriant=(Spinner) findViewById(R.id.formInquiry_spVeriant);
        spColors=(Spinner) findViewById(R.id.formInquiry_spVehicleColor);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
