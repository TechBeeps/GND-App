package quran.gnd.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QuranView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_view);
        WebView webview = (WebView) findViewById(R.id.quran_web);
        String webUrl = "https://edenbigh.com/q/";
        webview.setWebViewClient(new QuranView.MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(webUrl);
        webview.requestFocus();
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}