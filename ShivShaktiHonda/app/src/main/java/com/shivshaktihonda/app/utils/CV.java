package com.shivshaktihonda.app.utils;

import android.os.Environment;

import java.io.File;
import java.util.Locale;

/**
 * Created by Jaimin on 15/12/2015.
 */
public class CV {

    public static final String APP_NAME = "SAMPLEAPP";
    public static final String APP_FOLDER_NAME = "VenueGuide";
    public static final String STR_APP_SDCARD_PATH = Environment.getExternalStorageDirectory() + File.separator + APP_FOLDER_NAME;
    public static final String ISLOGIN = "isLogin";
    public static final Locale LOCALE_USE_DATEFORMAT = Locale.US;
    public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATABASE_DATE_FORMAT_WITH_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ISFROMEDIT ="isFromEdit" ;
    public static final String SELECT_MODEL_PRICELIST = "selectedModelPriceList";
    public static final String SELECT_MODEL_SUMMARY= "selectedModelSummary";
    public static final String SELECTED_INQUIRY_DATA = "selectedInquiryData";
    public static final String SELECTED_DELIVERY_DATA = "selectedDeliveryData";
    public static final String SELECTED_BOOKING_DATA = "selectedBookingData";
    public static final String SELECTED_BOOKING_ID = "selectedBookingId";
    public static final String SELECTED_BOOKING= "selectedBooking";
    public static final String SELECTED_SLAESPERSON = "selectedSalesPerson";
    public static final String SELECTED_MODEL_PRICELIST = "selectedModelPriceList";
    public static final String IS_FROM_EDIT_MODE = "isFromEditMode";

    public static final String INQ_TYPE_NEWS= "isFromEditMode";
    public static final String INQ_TYPE_DEMOVAN= "isFromEditMode";
    public static final String INQ_TYPE_REFERENCE= "isFromEditMode";
    public static final String INQ_TYPE_WALKIN= "isFromEditMode";
    public static final String SELECTED_SALESPERSONDATA = "selectedSalespersonData";


    public static String UDID = "";
    public static final String DEVICE_TYPE = "Android";
    public static final String DEVICE_TOKEN = "deviceToken";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String LAST_SCREEN = "lastScreen";
    public static final String Valid = "valid";
    public static final String ImageLink = "imagelink";
    public static final String ImageFile = "imagefile";
    //Local Security Key
    public static final String API_KEY = "4p3Yg7Z5PGtmYrsWWeWz";
    public static final String ERROR_CODE= "errorcode";
    public static final String MSG = "msg";
    /*Login Screen*/
    public static final String USER_ID= "user_id";
    public static final String USER_TYPE= "user_type";
    public static final String USER_NAME = "name";
    public static final String USER_MOBILE_NO= "mobile_number";
    public static final String USER_LOGIN_DATE = "login_datetime";


    /*Nis Fiber Form*/
    public static final String NIS_FIBER_FORM_PREFERENCE= "nis_fiber_form_pref";

    public static final String IS_ADMIN = "isAdmin";


    public static String SELECtED_INQUIRY_ID="selectedInquiryId";
    public static String SELECtED_INQUIRY="selectedInquiry";

}
