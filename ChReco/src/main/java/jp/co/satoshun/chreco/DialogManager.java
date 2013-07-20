package jp.co.satoshun.chreco;


import android.app.Dialog;
import android.content.Context;

class DialogManager {
    private static WaitingDialog instance = null;

    private DialogManager() {}

    public static void create(Context context) {
        if (instance == null) {
            instance = new WaitingDialog(context);
        }
    }

    public static WaitingDialog getInstance(Context context) {
        create(context);
        return instance; 
    }

    public void show() {
        if (instance != null) {
            instance.show();
        }
    }

    public void dismiss() {
        if (instance != null) {
            instance.dismiss();
        }
    }

    private static class WaitingDialog extends Dialog {
        public WaitingDialog(Context context) {
            super(context, R.style.WaitingDialog);
            setContentView(R.layout.waiting_dialog);
            setCancelable(false);
        }
    }
}
