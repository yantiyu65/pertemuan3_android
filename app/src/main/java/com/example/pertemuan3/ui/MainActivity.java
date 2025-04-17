package com.example.pertemuan3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pertemuan3.R;
import com.example.pertemuan3.adapter.NewsAdapter;
import com.example.pertemuan3.model.DataNews;
import com.example.pertemuan3.util.Constans;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView recData;
    NewsAdapter postAdapter;
    ArrayList<DataNews> newsList;
    ImageView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCover = findViewById(R.id.imgCover);
        recData = findViewById(R.id.recData);
        recData.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recData.setLayoutManager(mLayoutManager);

        newsList = new ArrayList<>();
        getDataNews();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("news.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void getDataNews() {
        String json = loadJSONFromAsset();

        // Cek apakah JSON berhasil dimuat
        if (json == null) {
            Toast.makeText(this, "Gagal membaca file JSON!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);

            // Pastikan JSON memiliki key "DATA_NEWS"
            if (!jsonObject.has("DATA_NEWS")) {
                Toast.makeText(this, "Format JSON salah, tidak ada 'DATA_NEWS'", Toast.LENGTH_LONG).show();
                return;
            }

            JSONArray jsonArray = jsonObject.getJSONArray("DATA_NEWS");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                DataNews developers = new DataNews();
                developers.title = jo.getString("title_news");
                developers.link_image = jo.getString("image_link");
                developers.description = jo.getString("link_news");
                newsList.add(developers);
            }

            // Cek apakah newsList kosong
            if (newsList.isEmpty()) {
                Toast.makeText(this, "Tidak ada berita yang tersedia!", Toast.LENGTH_LONG).show();
                return;
            }

            Random r = new Random();
            int numberCover = r.nextInt(newsList.size());
            Picasso.get().load(newsList.get(numberCover).getLink_image()).into(imgCover);

            imgCover.setOnClickListener(view -> {
                Constans.TITLE = newsList.get(numberCover).getTitle();
                Constans.IMAGE = newsList.get(numberCover).getLink_image();
                Constans.DESCRIPTION = newsList.get(numberCover).getDescription();
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            });

            postAdapter = new NewsAdapter(newsList, MainActivity.this);
            recData.setAdapter(postAdapter);

        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, "Error parsing JSON: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
