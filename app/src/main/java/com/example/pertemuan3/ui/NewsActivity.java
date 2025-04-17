package com.example.pertemuan3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pertemuan3.R;
import com.example.pertemuan3.util.Constans;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    private WebView mWebView;
    private ImageView imgDetail;
    private TextView txtTitleDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(),
                    insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(),
                    insets.getSystemWindowInsetBottom());
            return insets;
        });
        imgDetail = findViewById(R.id.imgDetail);
        txtTitleDetail = findViewById(R.id.txtTitleDetail);
        mWebView = findViewById(R.id.webData);

        // Load title
        txtTitleDetail.setText(Constans.TITLE);

        // Load image
        Picasso.get().load(Constans.IMAGE).into(imgDetail);

        // Load description
        mWebView.loadDataWithBaseURL(null,
                "<head><style>img{max-width: 90%; width:auto; height:auto;}</style></head>"
                        + Constans.DESCRIPTION, "text/html", "UTF-8", null);
    }
}