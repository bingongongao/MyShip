package com.example.huuph.myship.uis.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huuph.myship.R;

public class WebViewFabook extends AppCompatActivity {
    WebView webView;
    String idfeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_fabook);
        idfeed = getIntent().getStringExtra("idfeed");

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.facebook.com/" + idfeed + "");
        webView.setWebViewClient(new WebViewClient());


    }

    public void back(View view) {
        finish();
    }
}
