package com.leo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lihang.ShadowLayout;

/**
 * Created by leo
 * on 2020/8/5.
 */
public class WikiActivity extends AppCompatActivity {
    ShadowLayout ShadowLayoutIntent;
    ShadowLayout ShadowLayoutSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);
        ShadowLayoutIntent = findViewById(R.id.ShadowLayoutIntent);
        ShadowLayoutSelect = findViewById(R.id.ShadowLayoutSelect);
        ShadowLayoutIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WikiActivity.this, StarShowActivity.class));
            }
        });

        ShadowLayoutSelect.setOnClickListener(v -> {
            ShadowLayoutSelect.setSelected(!ShadowLayoutSelect.isSelected());
        });
    }
}
