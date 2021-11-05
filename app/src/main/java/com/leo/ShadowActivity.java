package com.leo;

import android.content.Intent;
import android.os.Bundle;

import com.leo.databinding.ActivityShadowBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Created by leo
 * on 2020/10/27.
 * shadow阴影的各项使用
 */
public class ShadowActivity extends AppCompatActivity {
    ActivityShadowBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shadow);
        binding.shadowLayoutBarLeft.setOnClickListener(v ->{
            finish();
        });

        binding.ShadowLayoutIntent.setOnClickListener(v ->{
            startActivity(new Intent(ShadowActivity.this, StarShowActivity.class));
        });

    }
}
