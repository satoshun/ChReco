package jp.satoshun.chreco.libs;


import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public void init() {
        WebSettings settings = getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        setVerticalScrollbarOverlay(true);
        requestFocus(View.FOCUS_DOWN);

        setWebViewClient(new WebViewClient() {
        });
    }
}
