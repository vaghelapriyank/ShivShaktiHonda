package com.shivshaktihonda.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.pojo.ModelUser;
import com.shivshaktihonda.app.pojo.ModelUserData;
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
 * Created by abc on 12/8/2017.
 */

public class ViewLogin extends AppCompatActivity implements View.OnClickListener {
    Button btnSignin;
    EditText edtUserName,edtPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        init();
        clickEvent();
    }

    private void clickEvent() {
        btnSignin.setOnClickListener(this);
    }

    private void init() {
        btnSignin= (Button)findViewById(R.id.login_btnSignin);
        edtUserName= (EditText) findViewById(R.id.login_edtUserName);
        edtPassword= (EditText)findViewById(R.id.login_edtPassword);

    }

    @Override
    public void onClick(View view) {
        if (view==btnSignin){

            if(CM.isInternetAvailable(ViewLogin.this)){
                if (edtUserName.getText().toString().trim().length()==0){
                    CM.ShowDialogue(ViewLogin.this, getResources().getString(R.string.err_msg_usernameEmpty));
                    edtUserName.requestFocus();
                }else if (edtPassword.getText().toString().trim().length()==0){
                    CM.ShowDialogue(ViewLogin.this, getResources().getString(R.string.err_msg_pwdEmpty));
                    edtPassword.requestFocus();
                }else{
                    webcallLogin(edtUserName.getText().toString(), edtPassword.getText().toString());
                }
            }else{
                CM.showDialogueNoInternet(ViewLogin.this,getResources().getString(R.string.msg_internet_unavailable_msg),false);
            }
        }
    }

    public void webcallLogin(String userName, String password) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewLogin.this, true, true);

            WebService.Login(v, userName, password, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForlogin(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(ViewLogin.this, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForlogin(String response) {
        Log.i("REsponse",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);
        ArrayList<ModelUserData> userData=new ArrayList<>();
        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelUser mainModel=CM.JsonParse(new ModelUser(),response);
                userData=mainModel.userDataArray;
                CM.setSp(ViewLogin.this,CV.USER_ID,userData.get(0).user_id);
                CM.setSp(ViewLogin.this,CV.USER_TYPE,userData.get(0).user_type);
                CM.setSp(ViewLogin.this,CV.USER_NAME,userData.get(0).name);
                CM.setSp(ViewLogin.this,CV.USER_MOBILE_NO,userData.get(0).mobile_number);
                CM.setSp(ViewLogin.this,CV.USER_LOGIN_DATE,userData.get(0).login_datetime);
                CM.setSp(ViewLogin.this, CV.ISLOGIN,true);
                if (userData.get(0).user_type.equalsIgnoreCase("0")){
                    CM.setSp(ViewLogin.this, CV.IS_ADMIN,true);
                }else{
                    CM.setSp(ViewLogin.this, CV.IS_ADMIN,false);
                }

                Intent intent=new Intent(ViewLogin.this,ViewDashboard.class);
                CM.startActivity(intent,ViewLogin.this);
                ViewLogin.this.finish();
            }else{
                CM.ShowDialogue(ViewLogin.this,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

}
