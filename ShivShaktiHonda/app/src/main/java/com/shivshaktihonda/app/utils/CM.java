package com.shivshaktihonda.app.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.shivshaktihonda.app.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Jaimin on 15/15/2015.
 */
public class CM {


    /**
     * Getting Typeface Passing attributes(Font name)
     *
     * @param ctx
     * @param attrs
     * @return
     */
/*    public static Typeface getTypeFace(Context ctx, AttributeSet attrs) {
        Typeface myTypeface = null;
        if (attrs != null) {
            TypedArray a = ctx.obtainStyledAttributes(attrs,
                    R.styleable.mtplviews);
            String fontName = a.getString(R.styleable.mtplviews_TypeFace);


            if (fontName != null) {
//Checking fontname if match with MainApplication set type as fontname otherwise RobotoMedium font set
                if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_medium))) {
                    myTypeface = ((VanueGuide) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_regular))) {
                    myTypeface = ((VanueGuide) ctx.getApplicationContext()).mTypeFaceRobotoRegular;
                }  else {
                    myTypeface = ((VanueGuide) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
                }
            } else {
                myTypeface = ((VanueGuide) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
            }

            a.recycle();
        }
        return myTypeface;
    }*/

    /**
     * Checking Internet is available or not
     *
     * @param context
     * @return
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void setSp(Context activity, String key, Object value) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }

        editor.commit();
        editor = null;
        prefs = null;

    }

    public static void setNisFormSp(Context activity, String key, Object value) {
        SharedPreferences prefs = activity.getSharedPreferences(
                CV.NIS_FIBER_FORM_PREFERENCE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }

        editor.commit();
        editor = null;
        prefs = null;

    }

    public static String getTimezone() {

        String[] ids = TimeZone.getAvailableIDs();
        Calendar cal = Calendar.getInstance();
        String timezone_value = "";
        long milliDiff = cal.get(Calendar.ZONE_OFFSET);
        for (String id : ids) {
            TimeZone tz = TimeZone.getTimeZone(id);
            if (tz.getRawOffset() == milliDiff) {
                // Found a match.
                timezone_value = id;
                break;
            }
        }
        System.out.println("timezone_value" + timezone_value);
        return timezone_value;
    }

    public static void setSSp(Context activity, String key, Object value) {
        SharedPreferences prefs = activity.getSharedPreferences(
                "SERVICE_DOWNLOADINFO", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }

        editor.commit();
        editor = null;
        prefs = null;

    }

    public static Object getNisFromSP(Context activity, String key, Object defaultValue) {
        SharedPreferences prefs = activity.getSharedPreferences(
                CV.NIS_FIBER_FORM_PREFERENCE, Activity.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return prefs.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return prefs.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return prefs.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return prefs.getLong(key, (Long) defaultValue);
        } else {
            return prefs.getFloat(key, (Float) defaultValue);
        }

    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        final List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

        if (taskInfo!=null){
            for (final ActivityManager.RunningTaskInfo taskInfo1 : taskInfo) {
                if (taskInfo1.topActivity.getPackageName().equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void clearSessionSp(Context activity) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CV.USER_ID);
        editor.remove(CV.USER_TYPE);
        editor.remove(CV.USER_NAME);
        editor.remove(CV.USER_MOBILE_NO);
        editor.remove(CV.USER_LOGIN_DATE);
        editor.remove(CV.ISLOGIN);
//        editor.clear();
        editor.commit();
    }


    public static void clearSp(Context activity) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CV.ACCESS_TOKEN);
//        editor.clear();
        editor.commit();
   }

    public static void clearSpecificSp(Context activity, String value) {
        try {
            SharedPreferences prefs = activity.getSharedPreferences(
                    activity.getPackageName(), Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(value);
//        editor.clear();
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * returns object of specific type from SharedPrefs
     *
     * @param activity
     * @param key          name of data in SP
     * @param defaultValue used if no value available for this key
     * @return
     */
    public static Object getSp(Context activity, String key, Object defaultValue) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return prefs.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return prefs.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return prefs.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return prefs.getLong(key, (Long) defaultValue);
        } else {
            return prefs.getFloat(key, (Float) defaultValue);
        }

    }

    public static String getFormattedCurrentDate(
            long timestampInMilliSeconds) {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
//        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmss")
        String formattedDate = new SimpleDateFormat("ddMMyyyyHHmmss")
                .format(date);
        return formattedDate;
    }



    /**
     * Check Whether App is in Background or Not
     * @param context
     */

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }






    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


  /*  public static void createShortCut(Activity activity)
    {
        boolean mboolean = false;
        SharedPreferences settings = activity.getSharedPreferences("VenueGuide_shortcut", 0);
        mboolean = settings.getBoolean("isCreated", false);
        if (!mboolean)
        {
            // do the thing for the first time
            settings = activity.getSharedPreferences("VenueGuide_shortcut", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isCreated", true);
            editor.commit();
            Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcutintent.putExtra("duplicate", false);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getResources().getString(R.string.app_name));
            Parcelable icon = Intent.ShortcutIconResource.fromContext(activity, R.mipmap.ic_launcher);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            *//*Class<?> activityClass;
            try {
                SharedPreferences prefs = activity.getSharedPreferences("X", activity.MODE_PRIVATE);
                activityClass = Class.forName(
                        prefs.getString("lastActivity", Activity.class.getName()));
            } catch(ClassNotFoundException ex) {
                activityClass = Activity.class;
            }*//*

            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(activity,HomeActivity.class));


            activity.sendBroadcast(shortcutintent);
        }
    }*/
    public static int getActionBarHeight(Activity activity) {
        final TypedArray ta = activity.getTheme().obtainStyledAttributes(
                new int[] {android.R.attr.actionBarSize});
        int actionBarHeight = (int) ta.getDimension(0, 0);
        return actionBarHeight;
    }

    // call this method when you don't have any data via intent
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_from_left,
                R.anim.push_out_to_right);

    }

    public static void fragStartActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_from_bottom,
                R.anim.stay);

    }

    // call this method when you have to pass data in intent
    public static void startActivity(Intent intent, Activity activity) {

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_from_left,
                R.anim.push_out_to_right);

    }
    // call this method when you have to pass data in intent
    public static void startActivityForResult(Intent intent, int resultcode, Activity activity) {

        activity.startActivityForResult(intent, resultcode);
        activity.overridePendingTransition(R.anim.push_in_from_bottom,
                R.anim.push_out_to_top);

    }



    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.push_out_to_left);
    }

    public static void setStatusBarTranslucent(boolean makeTranslucent,Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (makeTranslucent)
            {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            else
            {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }






    /**
     * Image Loader Default image remaining
     *
     * @param activity
     * @return
     */
   /*

    public static String converDateFormate(String oldpattern,
                                           String newPattern, String dateString) {
        // SimpleDateFormat sdf = new SimpleDateFormat(oldpattern);
        SimpleDateFormat sdf = new SimpleDateFormat(oldpattern,
                com.mtpl.sample.utils.CV.LOCALE_USE_DATEFORMAT);
        Date testDate = null;
        try {
            testDate = sdf.parse(dateString);
            SimpleDateFormat formatter = new SimpleDateFormat(newPattern,
                    com.mtpl.sample.utils.CV.LOCALE_USE_DATEFORMAT);
            String newFormat = formatter.format(testDate);
            System.out.println("" + newFormat);
            return newFormat;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }*/

    public static int getDeviceWidth(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    /**
     * @param activity
     * @return
     */
  /*  public static Dialog showPopupCommonValidation(final Activity activity,
                                                   final String title, final boolean isActivityFinish) {
        if (activity.isFinishing()) {
            return null;
        }
        final Dialog dialog = new Dialog(activity
        );
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_commonmsg);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                RelativeLayout rltvMain = (RelativeLayout) dialog.findViewById(R.id.RelativeLayout1);
                final TextView txtHeader = (TextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtHeader);
                txtHeader.setText(title);
                final TextView txtOk = (TextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtOkBtn);
                final TextView txtCancel = (TextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtCancelBtn);

                // CM.setFontRobotoRegular(activity, txtHeader);
                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (isActivityFinish) {
                            finishActivity(activity);
                            //finishActivity(activity);
                        }
                    }
                });
                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return dialog;
    }*/
    /**
     * Loading Image Progress
     */
    public static void loadImageProgess(ImageView imgProgress, Activity activity) {
        imgProgress.setVisibility(View.VISIBLE);
        Animation a = AnimationUtils.loadAnimation(activity, R.anim.scale);
        a.setDuration(1000);
        imgProgress.startAnimation(a);

        a.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
    }

    public static String Validation(String[] edittextName, TextView... edt) {
        String message = null;
        for (int i = 0; i < edt.length; i++) {
            if (edt[i].getText().length() > 0) {
                message = CV.Valid;
            } else {
                message = edittextName[i] + " is required.";
                break;
            }
        }
        return message;
    }

    public static String getUDID(final Context activity) {
        String android_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static void showDialogueNoInternet(final Activity activity,
                                              final String title, final boolean isActivityFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(title)
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isActivityFinish) {
                            finishActivity(activity);
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void ShowDialogue(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void ShowDialogueAndCallBackActivity(final Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static void ShowDialogueAndRedirectToLogin(final Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       /* Intent login_intent= new Intent(activity,ViewSignin.class);
                        activity.startActivity(login_intent);
                        activity.finish();
                        clearSp(activity);*/
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static <T> T JsonParse(T t, String response)
            throws JsonSyntaxException, IOException, XmlPullParserException {
        InputStream in = new ByteArrayInputStream(response.getBytes());
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapter(boolean.class, new BooleanSerializer());
        Gson gson = b.create();
        t = (T) gson.fromJson(reader, t.getClass());
        reader.close();
        return t;
    }


    /**
     * Json Response using key value
     *
     * @param Key
     * @param response
     * @return
     */
    public static String getValueFromJson(String Key, String response) {
        // if you use below code then it will throw null pointer exception when
        // key is not found in jsonResponse
        Gson gson = new Gson();
        ByteArrayInputStream io = new ByteArrayInputStream(
                response.getBytes());
        Reader reader = new InputStreamReader(io);
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        return jsonObject.get(Key).getAsString();
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    // database Operation copy file asset to application database folder
    public static void copyDataBase(Context mActivity) throws IOException {

        InputStream myInput = new FileInputStream(new File("/data/data/"
                + mActivity.getPackageName() + "/databases/"
                + "BodhiSteps.sqlite"));
        File files = new File(Environment.getExternalStorageDirectory()
                + "/files/");
        files.mkdirs();
        String outFileName = Environment.getExternalStorageDirectory()
                + "/files/BodhiSteps.sqlite";
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int bufferLength;
        while ((bufferLength = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, bufferLength);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("tag", "Copy Database Done");
    }

    /**
     * @param intMonthAgo
     * @param dateFormat
     * @return
     */
    public static String getMonthAgoDate(int intMonthAgo, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat,
                CV.LOCALE_USE_DATEFORMAT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, intMonthAgo);
        return formatter.format(calendar.getTime());
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        // DateFormat formatter = new SimpleDateFormat(dateFormat);
        DateFormat formatter = new SimpleDateFormat(dateFormat,
                CV.LOCALE_USE_DATEFORMAT);

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getCurrentDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        return getDate(calendar.getTimeInMillis(), dateFormat);
    }

    /*public static boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = VanueGuide.sqLiteDatabase;
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }*/

    public static Date convertStringtodate(String strDate) {
        // SimpleDateFormat format = new SimpleDateFormat(
        // CommonVariable.DATABASE_DATE_FORMAT);

        SimpleDateFormat format = new SimpleDateFormat(
                CV.DATABASE_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Http url parameter value divide in map value
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> splitUrlVariable(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static String getDoubleTwoDecimal(double value)
            throws NumberFormatException {

        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
                "#0.00");

        return decimalFormat.format(value);
    }

    public static String getFormatedData(String unformatedData) {
        if(unformatedData != null) {
            try {
                //unformatedData.replaceAll(",", "");
                Double result = Double.valueOf(unformatedData);
                DecimalFormat myFormatter = new DecimalFormat("#,##,##0.00");
                //DecimalFormat myFormatter = new DecimalFormat("#,###,###");
                //If you don't want to show .00 format
                return myFormatter.format(result);
            } catch (NumberFormatException e) {
                return unformatedData;
            }

        } else {
            return "0.00";
        }
    }
}

