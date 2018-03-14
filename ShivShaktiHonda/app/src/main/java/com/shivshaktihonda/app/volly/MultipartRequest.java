package com.shivshaktihonda.app.volly;

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
public class MultipartRequest extends Request<String> {
    private org.apache.http.entity.mime.MultipartEntity entity = new org.apache.http.entity.mime.MultipartEntity();

    private static String FILE_NAME ="";
    private static final String ADD_IUC_CHASSIS_FILE= "iuc_image_name";
    private static final String ADD_POWER_CABLE_FILE= "spd_image_name";
    private static final String ADD_IUC_UP_LINK_FILE= "onu_image_name";
    private static final String ADD_FTF_FOC_FILE= "foc_image_name";
    private static final String ADD_FDF_FOC_FILE= "fdf_image_name";
    private static final String ADD_FDF_BOX_FILE= "fdf2_image_name";
    private static final String ADD_EPE_NPE_FILE= "epe_image_name";

    private final Response.Listener<String> mListener;
    private final String file_iuc_chassis;
    private final String file_power_cable;
    private final String file_iuc_up_link;
    private final String file_ftf_foc;
    private final String file_fdf_foc;
    private final String file_fdf_box;
    private final String file_epe_npe;
    private final Map<String, String> params;
    File f_iuc_chassis,f_power_cable,f_iuc_up_link,f_ftf_foc,f_fdf_foc,f_fdf_box,f_epe_npe;
    public MultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String iuc_chassis, String power_cable, String iuc_up_link, String ftf_foc, String fdf_foc, String fdf_box, String epe_npe, Map<String, String> params) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        this.file_iuc_chassis = iuc_chassis;
        this.file_power_cable= power_cable;
        this.file_iuc_up_link= iuc_up_link;
        this.file_ftf_foc= ftf_foc;
        this.file_fdf_foc= fdf_foc;
        this.file_fdf_box= fdf_box;
        this.file_epe_npe= epe_npe;
//        this.FILE_NAME = imagekey;
//        this.FILE_NAME = imagekey;
        this.params = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {

        if (file_iuc_chassis != null && !file_iuc_chassis.equalsIgnoreCase("")){
            f_iuc_chassis= new File(file_iuc_chassis);
        }else{
            f_iuc_chassis= null;
        }

        if (file_power_cable!= null && !file_power_cable.equalsIgnoreCase("")){
            f_power_cable= new File(file_power_cable);
        }else{
            f_power_cable= null;
        }
        if (file_iuc_up_link!= null && !file_iuc_up_link.equalsIgnoreCase("")){
            f_iuc_up_link= new File(file_iuc_up_link);
        }else{
            f_iuc_up_link= null;
        }

        if (file_ftf_foc!= null && !file_ftf_foc.equalsIgnoreCase("")){
            f_ftf_foc= new File(file_ftf_foc);
        }else{
            f_ftf_foc= null;
        }

        if (file_fdf_foc!= null && !file_fdf_foc.equalsIgnoreCase("")){
            f_fdf_foc= new File(file_fdf_foc);
        }else{
            f_fdf_foc= null;
        }
        if (file_fdf_box!= null && !file_fdf_box.equalsIgnoreCase("")){
            f_fdf_box= new File(file_fdf_box);
        }else{
            f_fdf_box= null;
        }

        if (file_epe_npe!= null && !file_epe_npe.equalsIgnoreCase("") ){
            f_epe_npe= new File(file_epe_npe);
        }else{
            f_epe_npe= null;
        }

        try {


            if (f_iuc_chassis==null){
                //  entity.addPart(FILE_PART_NAME, new StringBody(""));
            }
            else if (f_iuc_chassis != null) {
                entity.addPart(ADD_IUC_CHASSIS_FILE, new FileBody(f_iuc_chassis));
            }

            if (f_power_cable==null){
                //  entity.addPart(FILE_PART_NAME, new StringBody(""));
            }
            else{
                entity.addPart(ADD_POWER_CABLE_FILE, new FileBody(f_power_cable));
            }

            if (f_iuc_up_link==null){
                //  entity.addPart(FILE_PART_NAME, new StringBody(""));
            }
            else{
                entity.addPart(ADD_IUC_UP_LINK_FILE, new FileBody(f_iuc_up_link));
            }

            if (f_ftf_foc==null){
            }
            else{
                entity.addPart(ADD_FTF_FOC_FILE, new FileBody(f_ftf_foc));
            }

            if (f_fdf_foc==null){
            }
            else{
                entity.addPart(ADD_FDF_FOC_FILE, new FileBody(f_fdf_foc));
            }

            if (f_fdf_box==null){
            }
            else{
                entity.addPart(ADD_FDF_BOX_FILE, new FileBody(f_fdf_box));
            }

            if (f_epe_npe==null){
            }
            else{
                entity.addPart(ADD_EPE_NPE_FILE, new FileBody(f_epe_npe));
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
