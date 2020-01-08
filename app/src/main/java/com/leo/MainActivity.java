package com.leo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lihang.ShadowLayout;

public class MainActivity extends AppCompatActivity {
    ShadowLayout ShadowLayoutSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShadowLayoutSelect = findViewById(R.id.ShadowLayoutSelect);
        ShadowLayoutSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShadowLayoutSelect.setSelected(!ShadowLayoutSelect.isSelected());
            }
        });
        findViewById(R.id.ShadowLayoutIntent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StarShowActivity.class));
            }
        });

    }
}
