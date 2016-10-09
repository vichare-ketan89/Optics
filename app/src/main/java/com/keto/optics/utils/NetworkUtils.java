package com.keto.optics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ketanvichare on 10/9/16.
 */
public class NetworkUtils {

    private static ConnectivityManager mConnectivityManager;
    private static String testString;

    public static void initNetworkUtils(Context context){
        mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        testString = "Ketan Anushree";

    }

    public static boolean isNetworkAvailable(){
        testString = testString;
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

}
