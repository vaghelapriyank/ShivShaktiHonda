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
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ravi on 30-07-2016.
 */
public class MultipartRequestWithMultipleImages extends Request<String> {
    private org.apache.http.entity.mime.MultipartEntity entity = new org.apache.http.entity.mime.MultipartEntity();

    private static String FILE_NAME ="";
    private static final String ADD_SUB_CAT_ICON = "imageName";
    private static final String ADD_AUDIORECORDING = "audioLink";
    private final Response.Listener<String> mListener;
    String audio;
    ArrayList<String> files=new ArrayList<>();
    private final Map<String, String> params;
    File file_1;
    ArrayList<File> fileArray=new ArrayList<>();
    File audioFile;
    public MultipartRequestWithMultipleImages(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, ArrayList<String> files, String audio, Map<String, String> params) {
        super(Method.POST, url, errorListener);

        mListener = listener;
//        this.file = file;
        this.files=files;
        this.audio=audio;
//        this.FILE_NAME = imagekey;
        this.params = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {

        if (fileArray != null){
            for (int i=0;i<files.size();i++){
                File file=new File(files.get(i));
                fileArray.add(file);
            }
//            file_1 = new File(file);
        }else{
//            file_1 = null;
            fileArray=null;
        }

        if (!audio.equals("")) {
            audioFile = new File(audio);
        }


        try {


            if (fileArray==null){
                //  entity.addPart(FILE_PART_NAME, new StringBody(""));
            }
            else if (fileArray != null) {
                Log.e("keyname", "" + ADD_SUB_CAT_ICON);
                for (int i=0;i<fileArray.size();i++){
                    Log.e("keyname", "" + ADD_SUB_CAT_ICON + (i + 1));
                    entity.addPart(ADD_SUB_CAT_ICON + (i + 1), new FileBody(fileArray.get(i)));

                }

//                entity.addPart(ADD_SUB_CAT_ICON, new FileBody(file_1));
            }
            if (audioFile!=null){
                entity.addPart(ADD_AUDIORECORDING,new FileBody(audioFile));
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
