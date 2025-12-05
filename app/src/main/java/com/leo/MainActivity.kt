package com.leo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leo.databinding.ActivityMainBinding
import com.leo.loadingview.MainLoadingActivity
import com.leo.scrollViewIndicator.ScrollViewIndicatorActivity
import com.leo.switchButton.SwitchButtonActivity

/**
 * 首页展示
 */
class MainActivity : AppCompatActivity() {
    var mBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding?.run {
            //ShadowLayout的使用
            ShadowLayoutShadow.setOnClickListener {
                startActivity(Intent(this@MainActivity, ShadowActivity::class.java))
            }

            ShadowLayoutShape.setOnClickListener {
                startActivity(Intent(this@MainActivity, ShapeActivity::class.java))
            }

            ShadowLayoutWiki.setOnClickListener {
                startActivity(Intent(this@MainActivity, WikiActivity::class.java))
            }

            //3.4.1 SmartLoadingView的使用
            smartFullscreenAuto.setOnClickListener {
                smartFullscreenAuto.startLoading()
                smartFullscreenAuto.finishLoadingWithFullScreen(this@MainActivity, MainLoadingActivity::class.java)
            }

            ShadowLayoutScrollView.setOnClickListener {
                startActivity(Intent(this@MainActivity, ScrollViewIndicatorActivity::class.java))
            }

            ShadowLayoutSwitchButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SwitchButtonActivity::class.java))
            }
        }
    }
}