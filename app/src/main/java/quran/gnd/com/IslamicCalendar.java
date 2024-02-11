package quran.gnd.com;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class IslamicCalendar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic_calendar);
        WebView webview = (WebView) findViewById(R.id.cal_web);
        String webUrl = "https://edenbigh.com/cl/cal.php";
        webview.setWebViewClient(new IslamicCalendar.MyWebViewClient());
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