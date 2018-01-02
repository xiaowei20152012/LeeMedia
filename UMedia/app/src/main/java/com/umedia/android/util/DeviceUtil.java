package com.umedia.android.util;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public final class DeviceUtil {

    public void getSimOperator(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String operator = telManager.getSimOperator();
        if (operator != null) {

            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {

                //中国移动

            } else if (operator.equals("46001")) {

                //中国联通

            } else if (operator.equals("46003")) {

                //中国电信

            }
        }

    }

    public static final String getNetworkOperatorName(Context context) {

        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperatorName = "";
        try {
            networkOperatorName = telManager.getNetworkOperatorName();
        } catch (Exception E) {

        }
        Log.e("tag", "" + networkOperatorName);
        return networkOperatorName;
    }

}
