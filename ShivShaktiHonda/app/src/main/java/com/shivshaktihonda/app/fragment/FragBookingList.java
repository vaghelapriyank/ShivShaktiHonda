package com.shivshaktihonda.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.activity.ViewBookingDetail;
import com.shivshaktihonda.app.activity.ViewDashboard;
import com.shivshaktihonda.app.adaptor.AdptSpBookingList;
import com.shivshaktihonda.app.adaptor.AdptSpInuiryList;
import com.shivshaktihonda.app.listener.RecyclerClickListener;
import com.shivshaktihonda.app.pojo.ModelBookingData;
import com.shivshaktihonda.app.pojo.ModelBookings;
import com.shivshaktihonda.app.pojo.ModelInquiry;
import com.shivshaktihonda.app.pojo.ModelInquiryData;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.OnVolleyHandler;
import com.shivshaktihonda.app.volly.VolleyIntialization;
import com.shivshaktihonda.app.volly.WebService;
import com.shivshaktihonda.app.volly.WebServiceTag;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abc on 12/8/2017.
 */

public class FragBookingList extends Fragment implements RecyclerClickListener, View.OnClickListener {
    Activity thisActivity;
    RecyclerView rvBookingList;
    AdptSpBookingList adptSpBookingList;
    ArrayList<ModelBookingData> bookingData;

    RelativeLayout rlMonthSelction;
    ImageView imgPreMonth,imgNextMonth;
    TextView txtSelectedMonth;
    Calendar calendar;
    String month, year;
    DecimalFormat formatter ;
    private File mainFolder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_booking_list,container,false);
        thisActivity=getActivity();
        init(view);
        clickEvent();
        setHasOptionsMenu(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (thisActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || thisActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE},
                        1001);
            }
        }

        if (!mainFolder.exists() && !mainFolder.isDirectory()) {
            mainFolder.mkdir();
        }


        if(CM.isInternetAvailable(thisActivity)){

            String userId=CM.getSp(thisActivity, CV.USER_ID,"").toString();
            if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
                webcallForGetBookings("",month,year);
            }else{
                webcallForGetBookings(CM.getSp(thisActivity, CV.USER_ID,"").toString(),"","");
            }

//            webcallForGetBookings();
        }else{
            CM.showDialogueNoInternet(thisActivity,getResources().getString(R.string.msg_internet_unavailable_msg),false);
        }
        return view;
    }

    private void clickEvent() {
        imgNextMonth.setOnClickListener(this);
        imgPreMonth.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share,menu);
        MenuItem emailItem=menu.findItem(R.id.action_email);

        if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
            emailItem.setVisible(true);
        }else{
            emailItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_email){
            callApiAndSendMailOfList(month,year);
        }
        return super.onOptionsItemSelected(item);
    }

    private void callApiAndSendMailOfList(String month, String year) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.downlaodBookingListFile(v,month,year, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForDownloadBookingListURL(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(thisActivity, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForDownloadBookingListURL(String response) {
        Log.e("RESPONSE",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                String fileUrl = CM.getValueFromJson(WebServiceTag.TAG_STR_DOWNLOADPATH, response);
                String[] urlArray=fileUrl.split("/");
                String down_url=mainFolder.getAbsolutePath()+"/csv_"+urlArray[urlArray.length-1];
                File tempFile=new File(down_url);
                if(tempFile.exists()){
                    tempFile.delete();
                }
                new DownloadFileFromURL().execute(fileUrl,down_url);
            }else{
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void webcallForGetBookings(String userId,String month,String year) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);

            WebService.getAllBookings(v,userId,month,year, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    getResponseForAllBookings(response);
                }

                @Override
                public void onVollyError(String error) {
                    CM.showDialogueNoInternet(thisActivity, error, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForAllBookings(String response) {
        Log.e("RESPONSE",response);
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        String responsecode = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORCODE, response);
        String msg = CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response);

        try {
            if (responsecode.equalsIgnoreCase("1")){
                ModelBookings mainModel=CM.JsonParse(new ModelBookings(),response);
                bookingData = mainModel.bookingDataArray;

                setBookingsList(bookingData);
            }else{
                rvBookingList.setVisibility(View.GONE);
                CM.ShowDialogue(thisActivity,msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void setBookingsList(ArrayList<ModelBookingData> bookingData) {
        rvBookingList.setVisibility(View.VISIBLE);
        adptSpBookingList=new AdptSpBookingList(thisActivity,bookingData);
        adptSpBookingList.clickListener(this);
        rvBookingList.setAdapter(adptSpBookingList);
    }

    private void init(View view) {
        mainFolder=new File(Environment.getExternalStorageDirectory()+"/"+getResources().getString(R.string.app_name));
        bookingData=new ArrayList<>();
        formatter = new DecimalFormat("00");
        rvBookingList=(RecyclerView)view.findViewById(R.id.booking_rvList);
        rvBookingList.setLayoutManager(new LinearLayoutManager(thisActivity));
        rlMonthSelction =(RelativeLayout) view.findViewById(R.id.booking_rlMonthSelection);
        imgPreMonth =(ImageView) view.findViewById(R.id.booking_ivPreMonth);
        imgNextMonth =(ImageView) view.findViewById(R.id.booking_ivNextMonth);
        txtSelectedMonth =(TextView) view.findViewById(R.id.booking_txtMonthYear);

        if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
            rlMonthSelction.setVisibility(View.VISIBLE);
        }else{
            rlMonthSelction.setVisibility(View.GONE);
        }

        calendar=Calendar.getInstance();
        txtSelectedMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
        month=formatter.format((calendar.getTime().getMonth()+1));
        year=String.valueOf(calendar.get(Calendar.YEAR));
        Log.e("YEAR",year);



    }

    @Override
    public void onResume() {
        super.onResume();
        ((ViewDashboard)thisActivity).setToolbarTitle(thisActivity.getResources().getString(R.string.booking_list_screen_title));
    }

    @Override
    public void onClickEvent(View v, int position) {
        CardView cvParent=(CardView)v.findViewById(R.id.spBooking_cvParent);

        if (v==cvParent){
            Intent intent=new Intent(thisActivity, ViewBookingDetail.class);
            intent.putExtra(CV.SELECTED_BOOKING_DATA,bookingData.get(position));
            CM.startActivityForResult(intent,2500,thisActivity);
        }


    }

    @Override
    public void onClick(View view) {
        if (view==imgNextMonth){
            setNextMonth();
        }else if (view==imgPreMonth){
            setPreviousMonth();
        }
    }

    private void setNextMonth() {
        if (calendar!=null){
            calendar.add(Calendar.MONTH,1);
            txtSelectedMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
            month = formatter.format((calendar.getTime().getMonth()+1));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("YEAR",year);
            if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
                webcallForGetBookings("",month,year);
            }else{
                webcallForGetBookings(CM.getSp(thisActivity, CV.USER_ID,"").toString(),"","");
            }
        }
    }

    private void setPreviousMonth() {
        if (calendar!=null){
            calendar.add(Calendar.MONTH,-1);
            txtSelectedMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
            month = formatter.format((calendar.getTime().getMonth()+1));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("YEAR",year);
            if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
                webcallForGetBookings("",month,year);
            }else{
                webcallForGetBookings(CM.getSp(thisActivity, CV.USER_ID,"").toString(),"","");
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2500 && resultCode==500){
            calendar=Calendar.getInstance();
            txtSelectedMonth.setText(new SimpleDateFormat("MMM-yyyy").format(calendar.getTime()));
            month = formatter.format((calendar.getTime().getMonth()+1));
            year=String.valueOf(calendar.get(Calendar.YEAR));
            Log.e("YEAR",year);
            if ((boolean) CM.getSp(thisActivity, CV.IS_ADMIN,false)){
                webcallForGetBookings("",month,year);
            }else{
                webcallForGetBookings(CM.getSp(thisActivity, CV.USER_ID,"").toString(),"","");
            }
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String fileUrl;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(thisActivity);
            pDialog.setIndeterminate(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setProgress(1000);
            pDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;

            try {
                fileUrl = f_url[1];
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(f_url[1]);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

//            if (isShareAction){
            shareSummaryFile(fileUrl);
           /* }else{
                CM.ShowDialogue(ViewSummaryDetail.this,getResources().getString(R.string.success_download_msg));
            }*/

        }
    }

    private void shareSummaryFile(String fileUrl) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Booking List");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I have attached Booking List file below of this mail. PFA");
//        String[] urlArray=summaryDetail.download_link.split("/");
        File root = new File(fileUrl);
        if (!root.exists() || !root.canRead()) {
            return;
        }

        Uri uri = null;
        uri = Uri.fromFile(root);
        /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
        }else{
            uri = FileProvider.getUriForFile(ViewSummaryDetail.this, BuildConfig.APPLICATION_ID+".provider", root);
        }*/
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }



}
