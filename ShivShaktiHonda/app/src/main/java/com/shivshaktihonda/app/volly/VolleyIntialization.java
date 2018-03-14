package com.shivshaktihonda.app.volly;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.ShivShaktiHonda;
import com.shivshaktihonda.app.utils.CM;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by jaimin on 15-12-2015.
 */
public class VolleyIntialization {
    private Activity mActivity;

    private boolean mIsShowPopup;
    private boolean mIsDismissPopup;
    private MtplProgressDialog mtplDialog;

    public VolleyIntialization(Activity activity, boolean showpopup, boolean dismisspopup) {

        mActivity = activity;
        mIsShowPopup = showpopup;
        mIsDismissPopup = dismisspopup;

    }

    public Activity getActivity() {
        return mActivity;
    }

    public boolean getShowPopup() {
        return mIsShowPopup;
    }

    public boolean getDismissPopup() {
        return mIsDismissPopup;
    }

//Volly Webservice Related Methods //

    /**
     * Webservice call with Map Key pair value and after response not
     * any ws call use this method(single boolean) for dialog dismiss
     *  @param url
     * @param requestMethod
//     * @param json
     * @param vollyHandler
     */
    public void vollyStringRequestCall(String url, final int requestMethod, final Map<String, String> params, final OnVolleyHandler vollyHandler) throws JSONException {
        //AS we have to pass Security key in ever webservice we have
//        if (json != null) {
//            json.put("strSecurityKey", CV.SECURITY_KEY);
//        }
        if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.getRequestQueue().getCache().invalidate(url, true);
        HttpsTrustManager.allowAllSSL();
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        Log.i("WebCalls", url);
        StringRequest stringRequest = new StringRequest(requestMethod,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
                        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
                            dismissMtplDialog(mActivity);
                        } else {
                            if (mIsDismissPopup) {
                                dismissMtplDialog(mActivity);
                            }
                        }
                        vollyHandler.onVollySuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Webcalls", "error=" + error.getMessage());
                dismissMtplDialog(mActivity);
                String errorSet = getActivity().getResources().getString(R.string.msg_networkerror);
                vollyHandler.onVollyError(errorSet);
            }
        }) {
            /*@Override
            public String getBodyContentType() {
                // TODO Auto-generated method stub
                return "application/json";
            }*/

           /* @Override
            public byte[] getBody() {

                Log.i("Webcalls", "Json=" + json.toString());
                try {
                    return json.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }*/

            @Override
            protected Map<String, String> getParams()
            {
                Log.i("Webcalls", "params=" + params.toString());
                return params;
            }
        };

      ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.addToRequestQueue(stringRequest);

    }


    //Volly Webservice Related Methods End //
    public void showMtplDialog(Activity mActivity) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (mtplDialog == null)
            mtplDialog = new MtplProgressDialog(mActivity, "", false);
        if (!mtplDialog.isShowing())
            mtplDialog.show();
    }

    private void dismissMtplDialog(Activity activity) {

        if (mtplDialog != null && mtplDialog.isShowing())
            mtplDialog.dismiss();
    }

    public void vollyStringRequestCallwithFile(String url, final String iuc_chassis, final String power_cable, final String iuc_up_link, final String ftb_foc, final String fdf_foc, final String fdf_box, final String epe_npe, final int requestMethod, final Map<String, String> params, final OnVolleyHandler vollyHandler) throws JSONException {
        if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        MultipartRequest mr = new MultipartRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
                if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
                    dismissMtplDialog(mActivity);
                } else {
                    if (mIsDismissPopup) {
                        dismissMtplDialog(mActivity);
                    }
                }
                try {
                    vollyHandler.onVollySuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Webcalls", "error=" + error.getMessage());
                dismissMtplDialog(mActivity);
                String errorSet = error.getMessage();
                vollyHandler.onVollyError(errorSet);
            }
        }, iuc_chassis,power_cable,iuc_up_link,ftb_foc,fdf_foc,fdf_box,epe_npe, params);
        Log.e("Webcalls", "params=" + params.toString());
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.addToRequestQueue(mr);
    }


    public void vollyStringRequestCallwithMultipleFiles(String url, ArrayList<String> f, final int requestMethod, final Map<String, String> params, final OnVolleyHandler vollyHandler) throws JSONException {
        if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        MultipartRequestWithMultipleImages mr = new MultipartRequestWithMultipleImages(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
                if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
                    dismissMtplDialog(mActivity);
                } else {
                    if (mIsDismissPopup) {
                        dismissMtplDialog(mActivity);
                    }
                }
                try {
                    vollyHandler.onVollySuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Webcalls", "error=" + error.getMessage());
                dismissMtplDialog(mActivity);
                String errorSet = error.getMessage();
                vollyHandler.onVollyError(errorSet);
            }
        }, f,"", params);
        Log.e("Webcalls", "params=" + params.toString());
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.addToRequestQueue(mr);
    }

    public void vollyStringRequestCallwithMultipleFilesWithAudio(String url, ArrayList<String> f, String audioPath, final int requestMethod, final Map<String, String> params, final OnVolleyHandler vollyHandler) throws JSONException {
        if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        MultipartRequestWithMultipleImages mr = new MultipartRequestWithMultipleImages(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
                if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
                    dismissMtplDialog(mActivity);
                } else {
                    if (mIsDismissPopup) {
                        dismissMtplDialog(mActivity);
                    }
                }
                try {
                    vollyHandler.onVollySuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Webcalls", "error=" + error.getMessage());
                dismissMtplDialog(mActivity);
                String errorSet = error.getMessage();
                vollyHandler.onVollyError(errorSet);
            }
        }, f , audioPath, params);
        Log.e("Webcalls", "params=" + params.toString());
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.addToRequestQueue(mr);
    }


    public void vollyJsonObjectRequestCall(String url, final int requestMethod, JSONObject jsonData, final OnVolleyHandler vollyHandler) throws JSONException {

        if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.getRequestQueue().getCache().invalidate(url, true);
        HttpsTrustManager.allowAllSSL();
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        Log.i("WebCalls", url);

        JsonObjectRequest request_json = new JsonObjectRequest(url, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
/*                            String strResponseStatus = CM.getValueFromJson(com.venueguide.app.volly.WebServiceTag.WEB_STATUS, response);
                            if (strResponseStatus.equalsIgnoreCase(com.venueguide.app.volly.WebServiceTag.WEB_STATUSFAIL)) {
                                dismissMtplDialog(mActivity);
                            } else {
                                if (mIsDismissPopup) {
                                    dismissMtplDialog(mActivity);
                                }
                            }*/
                            vollyHandler.onVollySuccess(response.toString());
                            dismissMtplDialog(mActivity);

                            //Process os success response
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        ((ShivShaktiHonda) mActivity.getApplicationContext()).volley.addToRequestQueue(request_json);
    }
}
