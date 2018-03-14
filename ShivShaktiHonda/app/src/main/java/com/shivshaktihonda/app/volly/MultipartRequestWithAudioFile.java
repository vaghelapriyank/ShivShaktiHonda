package com.shivshaktihonda.app.volly;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Ravi on 30-07-2016.
 */
public class MultipartRequestWithAudioFile extends Request<String> {
    private org.apache.http.entity.mime.MultipartEntity entity = new org.apache.http.entity.mime.MultipartEntity();

    private static String FILE_NAME ="";
    private static final String ADD_SUB_CAT_ICON = "audioLink";

    private final Response.Listener<String> mListener;
    private final String file;
    private final Map<String, String> params;
    File file_1;
    public MultipartRequestWithAudioFile(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String file, Map<String, String> params) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        this.file = file;
//        this.FILE_NAME = imagekey;
        this.params = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {

        if (file != null){
            file_1 = new File(file);
        }else{
            file_1 = null;
        }


        try {


            if (file==null){
                //  entity.addPart(FILE_PART_NAME, new StringBody(""));
            }
            else if (file != null) {
                Log.e("keyname", "" + ADD_SUB_CAT_ICON);
                entity.addPart(ADD_SUB_CAT_ICON, new FileBody(file_1));
            }


/*
            if (file != null && file1 != null && file2 != null) {
                entity.addPart(FILE_PART_NAME, new FileBody(file_1));
                entity.addPart(FILE_PART_NAME1, new FileBody(file_2));
                entity.addPart(FILE_PART_NAME2, new FileBody(file_3));
            }
            else if(file != null && file1 != null){
                entity.addPart(FILE_PART_NAME, new FileBody(file_1));
                entity.addPart(FILE_PART_NAME1, new FileBody(file_2));
                entity.addPart(FILE_PART_NAME2, new StringBody(""));
            }*/
            for (String key : params.keySet()) {
                entity.addPart(key, new StringBody(params.get(key)));
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String parsed;
        try {
            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(networkResponse.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }


}
