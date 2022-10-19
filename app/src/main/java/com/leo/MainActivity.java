package com.leo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.leo.databinding.ActivityMainBinding;

/**
 * 首页展示
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        binding.ShadowLayoutShadow.setOnClickListener(v -> {
//            startActivity(new Intent(MainActivity.this, ShadowActivity.class));
//        });
//
//        binding.ShadowLayoutShape.setOnClickListener(v -> {
//            startActivity(new Intent(MainActivity.this, ShapeActivity.class));
//        });
//
//        binding.ShadowLayoutWiki.setOnClickListener(v -> {
//            startActivity(new Intent(MainActivity.this, WikiActivity.class));
//        });
        binding.buttonPanel.setOnClickListener(v->{
            binding.ShadowLayoutClickable.setGradientColor(Color.parseColor("#ff0000"),Color.parseColor("#000000"));
        });

        binding.buttonPanel2.setOnClickListener(v->{
            binding.ShadowLayoutClickable.setLayoutBackground(Color.parseColor("#0000ff"));
        });
    }
}
