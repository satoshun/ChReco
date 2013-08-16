package jp.satoshun.chreco.libs;


import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class User {
    private static String userId = null;

    public static String getId(Context context) {
        if (userId != null) return userId;

        String userId = getIMEI(context);
        if (userId != null) {
            return userId;
        }

        return getAndroidId(context);
    }

    private static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
        }
        return null;
    }

    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
