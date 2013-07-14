package jp.co.satoshun.chreco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import jp.co.satoshun.chreco.libs.MyWebView;

public class WebViewActivity extends Activity {
    LinearLayout layout;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        layout = (LinearLayout) findViewById(R.id.main_webview_layout);
        webView = new MyWebView(getApplicationContext());
        layout.addView(webView);
        Uri uri = getIntent().getData();
        webView.loadUrl(String.valueOf(uri));
    }
}
