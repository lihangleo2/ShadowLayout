package com.leo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leo.databinding.ActivityShadowBinding;
import com.leo.databinding.ActivityShapeBinding;

/**
 * Created by leo
 * on 2020/10/27.
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
