package com.leo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leo.databinding.ActivityMainBinding;

/**
 * 首页
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

        binding.ShadowLayoutWiki.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this, WikiActivity.class));
        });






    }
}
