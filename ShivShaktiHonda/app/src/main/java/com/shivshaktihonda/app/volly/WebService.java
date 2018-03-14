package com.shivshaktihonda.app.volly;


import com.android.volley.Request;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.utils.URLS;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jaimin on 15-12-2015.
 */
public class WebService {

    /**
     * @param vollyInit
     * @param vollyHanlder
     * @throws JSONException
     */
    public static void Login(VolleyIntialization vollyInit, String strEmail, String strPwd, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.USER_LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USERNAME, strEmail);
        params.put(WebServiceTag.TAG_STR_PASSWORD, strPwd);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getAllModels(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_MODELS;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getAllInquiries(VolleyIntialization vollyInit,String userId,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_INQUIRIES_FOR_SALESPERSON;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getAllBookings(VolleyIntialization vollyInit,String userId,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_BOOKINGS_FOR_SALESPERSON;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void changePassword(VolleyIntialization vollyInit,String userId,String oldPwd,String newPwd,String confirmPwd, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CHANGE_PASSWORD;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_OLD_PWD, oldPwd);
        params.put(WebServiceTag.TAG_STR_NEW_PWD, newPwd);
        params.put(WebServiceTag.TAG_STR_CONFIRM_PWD, confirmPwd);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void deleteBooking(VolleyIntialization vollyInit,String booking_primary_id, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.DELETE_BOOKING;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_BOOKING_PRIMARY_ID, booking_primary_id);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getAllDeliveries(VolleyIntialization vollyInit,String userId,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_DELIVERY;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getPersonalData(VolleyIntialization vollyInit,String userId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_USER_DASHBOARD_DATA;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void editPriceList(VolleyIntialization vollyInit,String modelTypeId,String variantName,String exShowroom,String insurance,String rto,String extraWar,String amc,String mpp,String acc,String total, String incentiveAmount, String incentiveExShowroom, String insuranceIncentive, String RTOIncentive, String AMCIncentive, String extraWarIncentive, String MPPIncentive, String ACCIncentive, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.EDIT_PRICE_LIST;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_VARIANT_NAME, variantName);
        params.put(WebServiceTag.TAG_STR_MODEL_TYPE_ID, modelTypeId);
        params.put(WebServiceTag.TAG_STR_EX_SHOWROOM,exShowroom);
        params.put(WebServiceTag.TAG_STR_INSURANCE,insurance);
        params.put(WebServiceTag.TAG_STR_RTO,rto);
        params.put(WebServiceTag.TAG_STR_EXTRA_WAR,extraWar);
        params.put(WebServiceTag.TAG_STR_AMC,amc);
        params.put(WebServiceTag.TAG_STR_MPP,mpp);
        params.put(WebServiceTag.TAG_STR_ACC,acc);
        params.put(WebServiceTag.TAG_STR_TOTAL,total);
        params.put(WebServiceTag.TAG_STR_INS_AMOUNT,incentiveAmount);
        params.put(WebServiceTag.TAG_STR_INS_EX_SHOWROOM,incentiveExShowroom);
        params.put(WebServiceTag.TAG_STR_INS_INSURANCE,insuranceIncentive);
        params.put(WebServiceTag.TAG_STR_INS_RTO,RTOIncentive);
        params.put(WebServiceTag.TAG_STR_INS_AMC,AMCIncentive);
        params.put(WebServiceTag.TAG_STR_INS_EXTRA_WAR,extraWarIncentive);
        params.put(WebServiceTag.TAG_STR_INS_MPP,MPPIncentive);
        params.put(WebServiceTag.TAG_STR_INS_ACC,ACCIncentive);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getAllPriceList(VolleyIntialization vollyInit,String userId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_PRICE_LIST;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getAllModelSummaries(VolleyIntialization vollyInit,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_MODEL_SUMMARY;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void addBooking(VolleyIntialization vollyInit,String userId,String inquiryId, String bookingId, String amount, String deliveryDate, String paymentType,String isExShowroomInclude, String isInsuranceInclude, String isRtoInclude, String isAmcInclude, String isEWInclude, String isMppInclude, String isAccInclude, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADD_BOOKING_FORM;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_INQUIRY_ID, inquiryId);
        params.put(WebServiceTag.TAG_BOOKING_ID, bookingId);
        params.put(WebServiceTag.TAG_STR_AMOUNT, amount);
        params.put(WebServiceTag.TAG_STR_PAYMENT_TYPE, paymentType);
        params.put(WebServiceTag.TAG_STR_DELIEVERYDATETIME, deliveryDate);
        params.put(WebServiceTag.TAG_STR_INC_EX_SHOWROOM, isExShowroomInclude);
        params.put(WebServiceTag.TAG_STR_INC_INSURANCE, isInsuranceInclude);
        params.put(WebServiceTag.TAG_STR_INC_RTO, isRtoInclude);
        params.put(WebServiceTag.TAG_STR_INC_AMC, isAmcInclude);
        params.put(WebServiceTag.TAG_STR_INC_EW, isEWInclude);
        params.put(WebServiceTag.TAG_STR_INC_MPP, isMppInclude);
        params.put(WebServiceTag.TAG_STR_INC_ACC, isAccInclude);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void editBooking(VolleyIntialization vollyInit,String bookingPrimaryId,String userId,String inquiryId, String bookingId, String amount, String deliveryDate, String paymentType,String isExShowroomInclude, String isInsuranceInclude, String isRtoInclude, String isAmcInclude, String isEWInclude, String isMppInclude, String isAccInclude, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.EDIT_BOOKING_FORM;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_BOOKING_PRIMARY_ID,bookingPrimaryId);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_INQUIRY_ID, inquiryId);
        params.put(WebServiceTag.TAG_BOOKING_ID, bookingId);
        params.put(WebServiceTag.TAG_STR_AMOUNT, amount);
        params.put(WebServiceTag.TAG_STR_PAYMENT_TYPE, paymentType);
        params.put(WebServiceTag.TAG_STR_DELIEVERYDATETIME, deliveryDate);
        params.put(WebServiceTag.TAG_STR_INC_EX_SHOWROOM, isExShowroomInclude);
        params.put(WebServiceTag.TAG_STR_INC_INSURANCE, isInsuranceInclude);
        params.put(WebServiceTag.TAG_STR_INC_RTO, isRtoInclude);
        params.put(WebServiceTag.TAG_STR_INC_AMC, isAmcInclude);
        params.put(WebServiceTag.TAG_STR_INC_EW, isEWInclude);
        params.put(WebServiceTag.TAG_STR_INC_MPP, isMppInclude);
        params.put(WebServiceTag.TAG_STR_INC_ACC, isAccInclude);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void addDeliveryForm(VolleyIntialization vollyInit,String userId, String bookingId, String engieNumber, String chesisNumber, String batteryNumber, String keyNumber, String dcNumber, String priceTotal, String edtDiscount, String priceDifference, String strPaymentType, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADD_DELIVERY_FORM;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_BOOKING_PRIMARY_ID, bookingId);
        params.put(WebServiceTag.TAG_STR_ENGINE_NUMBER, engieNumber);
        params.put(WebServiceTag.TAG_STR_CHESIS_NUMBER, chesisNumber);
        params.put(WebServiceTag.TAG_STR_BATTERY_NUMBER, batteryNumber);
        params.put(WebServiceTag.TAG_STR_KEY_NUMBER, keyNumber);
        params.put(WebServiceTag.TAG_STR_DC_NUMBER, dcNumber);
        params.put(WebServiceTag.TAG_STR_TOTAL, priceTotal);
        params.put(WebServiceTag.TAG_STR_DIFERENCE, priceDifference);
        params.put(WebServiceTag.TAG_STR_DISCOUNT, edtDiscount);
        params.put(WebServiceTag.TAG_STR_AMOUNT, priceTotal);
        params.put(WebServiceTag.TAG_STR_PAYMENT_TYPE, strPaymentType);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void addInquiry(VolleyIntialization vollyInit,String userId,String firstName, String middleName, String lastName, String mobileNumber, String address, String tahsil, String distict, String strVehicleType, String modelName, String modelVariant, String vehicleColor, String strInquiryType, String strCustomerType, String strPurchaseType, String strTestDrive, String feedback, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ADD_INQUIRY;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_FIRST_NAME, firstName);
        params.put(WebServiceTag.TAG_STR_MIDDLE_NAME, middleName);
        params.put(WebServiceTag.TAG_STR_LAST_NAME, lastName);
        params.put(WebServiceTag.TAG_STR_MOBILE_NUMBER, mobileNumber);
        params.put(WebServiceTag.TAG_STR_ADDRESS, address);
        params.put(WebServiceTag.TAG_STR_TAHSIL, tahsil);
        params.put(WebServiceTag.TAG_STR_DISTICT, distict);
        params.put(WebServiceTag.TAG_STR_VEHICLE_TYPE, strVehicleType);
        params.put(WebServiceTag.TAG_STR_MODEL_ID, modelName);
        params.put(WebServiceTag.TAG_STR_MODEL_TYPE_ID, modelVariant);
        params.put(WebServiceTag.TAG_STR_VEHICLE_COLOR, vehicleColor);
        params.put(WebServiceTag.TAG_STR_INQUIRY_TYPE, strInquiryType);
        params.put(WebServiceTag.TAG_STR_CUSTOMER_TYPE, strCustomerType);
        params.put(WebServiceTag.TAG_STR_PURCHASE_TYPE, strPurchaseType);
        params.put(WebServiceTag.TAG_STR_TEST_DRIVE, strTestDrive);
        params.put(WebServiceTag.TAG_STR_FEEDBACK, feedback);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void editInquiry(VolleyIntialization vollyInit,String inquiryId,String userId,String firstName, String middleName, String lastName, String mobileNumber, String address, String tahsil, String distict, String strVehicleType, String modelName, String modelVariant, String vehicleColor, String strInquiryType, String strCustomerType, String strPurchaseType, String strTestDrive, String feedback, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_EDIT_INQUIRY;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_INQUIRY_ID, inquiryId);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        params.put(WebServiceTag.TAG_STR_FIRST_NAME, firstName);
        params.put(WebServiceTag.TAG_STR_MIDDLE_NAME, middleName);
        params.put(WebServiceTag.TAG_STR_LAST_NAME, lastName);
        params.put(WebServiceTag.TAG_STR_MOBILE_NUMBER, mobileNumber);
        params.put(WebServiceTag.TAG_STR_ADDRESS, address);
        params.put(WebServiceTag.TAG_STR_TAHSIL, tahsil);
        params.put(WebServiceTag.TAG_STR_DISTICT, distict);
        params.put(WebServiceTag.TAG_STR_VEHICLE_TYPE, strVehicleType);
        params.put(WebServiceTag.TAG_STR_MODEL_ID, modelName);
        params.put(WebServiceTag.TAG_STR_MODEL_TYPE_ID, modelVariant);
        params.put(WebServiceTag.TAG_STR_VEHICLE_COLOR, vehicleColor);
        params.put(WebServiceTag.TAG_STR_INQUIRY_TYPE, strInquiryType);
        params.put(WebServiceTag.TAG_STR_CUSTOMER_TYPE, strCustomerType);
        params.put(WebServiceTag.TAG_STR_PURCHASE_TYPE, strPurchaseType);
        params.put(WebServiceTag.TAG_STR_TEST_DRIVE, strTestDrive);
        params.put(WebServiceTag.TAG_STR_FEEDBACK, feedback);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    //ADMIN APIS
    //GetAllSalesPerson API

    public static void getAllSalesPerson(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ALL_SALESPERSON;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void addSalesPerson(VolleyIntialization vollyInit, String name, String email, String mobile, String userName, String pwd,OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_ADD_SALESPERSON;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_NAME, name);
        params.put(WebServiceTag.TAG_STR_EMAIL, email);
        params.put(WebServiceTag.TAG_STR_MOBILENUMBER, mobile);
        params.put(WebServiceTag.TAG_STR_USERNAME, userName);
        params.put(WebServiceTag.TAG_STR_PASSWORD, pwd);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void editSalesPerson(VolleyIntialization vollyInit, String userId,  String name, String email, String mobile, String userName, String pwd,OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GET_EDIT_SALESPERSON;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_NAME, name);
        params.put(WebServiceTag.TAG_STR_EMAIL, email);
        params.put(WebServiceTag.TAG_STR_MOBILENUMBER, mobile);
        params.put(WebServiceTag.TAG_STR_USERNAME, userName);
        params.put(WebServiceTag.TAG_STR_PASSWORD, pwd);
        params.put(WebServiceTag.TAG_STR_USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }



/*    public static void changePassword(VolleyIntialization vollyInit, String userID, String curPwd, String newPwd, String confirmPwd, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.USER_CHANGE_PASSWORD;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_USERID, userID);
        params.put(WebServiceTag.TAG_STR_CUR_PASSWORD,curPwd);
        params.put(WebServiceTag.TAG_STR_NEW_PASSWORD,newPwd);
        params.put(WebServiceTag.TAG_STR_CONFIRM_PASSWORD,confirmPwd);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }*/

    public static void downlaodInquiryListFile(VolleyIntialization vollyInit,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.DOWNLOAD_INQUIRYLIST;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void downlaodBookingListFile(VolleyIntialization vollyInit,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.DOWNLOAD_BOOKINGLIST;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void downlaodDeliveryListFile(VolleyIntialization vollyInit,String month,String year, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.DOWNLOAD_DELIVERYLIST;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_MONTH, month);
        params.put(WebServiceTag.TAG_STR_YEAR, year);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }



}

