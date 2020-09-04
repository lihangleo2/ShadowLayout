package com.leo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ShadowLayout_wiki).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WikiActivity.class));
        });

        findViewById(R.id.ShadowLayout_shape).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShapeActivity.class));
        });

    }
}
