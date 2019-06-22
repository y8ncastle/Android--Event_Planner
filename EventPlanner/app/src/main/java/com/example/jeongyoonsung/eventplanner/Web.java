package com.example.jeongyoonsung.eventplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Web extends AppCompatActivity {
    public static String web_url = "";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        webView = (WebView) findViewById(R.id.web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(web_url);

        Toast.makeText(getApplicationContext(), "10초 이상 웹이 연결되지 않는 경우 잘못된 링크이거나\n연결 상태가 좋지 않은 상태일 수 있습니다.", Toast.LENGTH_LONG).show();
    }
}
