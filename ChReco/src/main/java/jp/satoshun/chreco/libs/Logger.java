package jp.satoshun.chreco.libs;

import android.util.Log;
import jp.satoshun.chreco.BuildConfig;


public class Logger {
    private static final boolean debug = BuildConfig.DEBUG;
    private static final String TAG = "Logger";

    public static void e() {
        if (debug) {
            Log.e(TAG, getMetaInfo());
        }
    }

    private static String getMetaInfo(){
         // スタックトレースから情報を取得 // 0: VM, 1: Thread, 2: LogUtil#getMetaInfo, 3: LogUtil#d など, 4: 呼び出し元
         final StackTraceElement element = Thread.currentThread().getStackTrace()[4];
         return Logger.getMetaInfo(element);
    }

    public static String getMetaInfo(StackTraceElement element){
        // クラス名、メソッド名、行数を取得
        final String fullClassName = element.getClassName();
        final String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        final String methodName = element.getMethodName();
        final int lineNumber = element.getLineNumber();
        // メタ情報
        final String metaInfo = "[" + simpleClassName + "#" + methodName + ":" + lineNumber + "]";
        return metaInfo;
    }
}
