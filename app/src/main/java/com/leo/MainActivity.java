package com.leo;

import android.content.Intent;
import android.os.Bundle;

import com.leo.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * 首页展示
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.ShadowLayoutShadow.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShadowActivity.class));
        });

        binding.ShadowLayoutShape.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShapeActivity.class));
        });

        binding.ShadowLayoutWiki.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WikiActivity.class));
        });




    }
}
