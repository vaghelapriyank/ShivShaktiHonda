package com.shivshaktihonda.app;

import android.app.Application;
import android.content.Context;

import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;
import com.shivshaktihonda.app.volly.VolleySingleton;

/**
 * Created by abc on 12/8/2017.
 */

public class ShivShaktiHonda extends Application {
    public Context mContext;
    public VolleySingleton volley;
    private static ShivShaktiHonda vInstance;

    public  static synchronized ShivShaktiHonda getInstance(){
        return vInstance;
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        volley = new VolleySingleton(mContext);
        CV.UDID = CM.getUDID(mContext);
    }
}
