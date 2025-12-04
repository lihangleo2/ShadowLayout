package com.leo.loadingview


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leo.R
import com.leo.databinding.ActivityLoadingMainBinding


class MainLoadingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoadingMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_loading_main)
        supportActionBar?.title = "SmartLoadingView的使用"
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
        mBinding.run {
            /**
             * 1.smart_full_screen:全屏
             * 不支持关注模式
             * */

            //finishLoadingWithFullScreen 的使用
            smartFullscreenAuto.setOnClickListener {
                //1.1、开始加载
                smartFullscreenAuto.startLoading()

                //1.2、模拟2s后，加载成功并跳转（finishLoadingWithFullScreen会自动跳转页面并关闭当前页面）
                it.postDelayed({
                    smartFullscreenAuto.finishLoadingWithFullScreen(this@MainLoadingActivity, SecondActivity::class.java)
                    //也可以自己处理动画回调
                    //smartFullscreenAuto.finishLoading(true) {}
                }, 2000)
            }

            //模拟失败
            smartFullscreenFail.setOnClickListener {
                smartFullscreenFail.startLoading()
                it.postDelayed({
                    smartFullscreenFail.finishLoading(false) {
                        Toast.makeText(this@MainLoadingActivity,"加载失败",Toast.LENGTH_SHORT).show()
                    }
                }, 2000)
            }


            //模拟失败，并展示失败文案
            smartFullscreenFailtxt.setOnClickListener {
                smartFullscreenFailtxt.startLoading()
                it.postDelayed({
                    //smartFullscreenFailtxt.setAnimaledText("我是自定义错误文案")
                    smartFullscreenFailtxt.finishLoading(false)
                }, 2000)
            }


            /**
             * 2.smart_button
             * 支持关注模式
             * */
            //正常情况下的："点击关注" 和 "取消关注"
            smartButtonSuccess.setOnClickListener {
                if (!smartButtonSuccess.isFinished) {
                    smartButtonSuccess.startLoading()
                    it.postDelayed({
                        //2.1、加载成功--带loading动画的加载成功
                        smartButtonSuccess.finishLoading(true) {
                            Toast.makeText(this@MainLoadingActivity,"关注成功",Toast.LENGTH_SHORT).show()
                        }
                    }, 2000)
                } else {
                    //2.2、再次点击，取消关注。通过此方法设置不带动画
                    smartButtonSuccess.isFinished = false
                }
            }

            //模拟关注失败
            smartButtonFail.setOnClickListener {
                smartButtonFail.startLoading()
                it.postDelayed({
                    smartButtonFail.finishLoading(false) {
                        Toast.makeText(this@MainLoadingActivity,"关注失败，请稍后再试~",Toast.LENGTH_SHORT).show()
                    }
                }, 2000)
            }

            //不带动画的 ”关注“ 和 ”取消关注“
            smartButtonNoanimal.setOnClickListener {
                smartButtonNoanimal.isFinished = !smartButtonNoanimal.isFinished
            }


            /**
             * 3.smart_tick
             * 支持关注模式
             * */
            //这里的用法和 smart_button 类似
            smartTickDemo.setOnClickListener {
                if (!smartTickDemo.isFinished) {
                    smartTickDemo.startLoading()
                    it.postDelayed({
                        //2.1、加载成功--带loading动画的加载成功
                        smartTickDemo.finishLoading(true) {
                            Toast.makeText(this@MainLoadingActivity,"关注成功",Toast.LENGTH_SHORT).show()
                        }
                    }, 2000)
                } else {
                    //2.2、再次点击，取消关注。通过此方法设置不带动画
                    smartTickDemo.isFinished = false
                }
            }


            /**
             * 4.smart_tick_hide
             * 支持关注模式
             * */
            //这里的用法和 smart_button 类似
            smartTickHideDemo.setOnClickListener {
                if (!smartTickHideDemo.isFinished) {
                    smartTickHideDemo.startLoading()
                    it.postDelayed({
                        //2.1、加载成功--带loading动画的加载成功
                        smartTickHideDemo.finishLoading(true) {
                            Toast.makeText(this@MainLoadingActivity,"关注成功",Toast.LENGTH_SHORT).show()
                        }
                    }, 2000)
                } else {
                    //2.2、再次点击，取消关注。通过此方法设置不带动画
                    smartTickHideDemo.isFinished = false
                }
            }


            /**
             * 5.smart_tick_center_hide
             * 支持关注模式
             * */
            //这里的用法和 smart_button 类似
            smartTickCenterHideDemo.setOnClickListener {
                if (!smartTickCenterHideDemo.isFinished) {
                    smartTickCenterHideDemo.startLoading()
                    it.postDelayed({
                        //2.1、加载成功--带loading动画的加载成功
                        smartTickCenterHideDemo.finishLoading(true) {
                            Toast.makeText(this@MainLoadingActivity,"关注成功",Toast.LENGTH_SHORT).show()
                        }
                    }, 2000)
                } else {
                    //2.2、再次点击，取消关注。通过此方法设置不带动画
                    smartTickCenterHideDemo.isFinished = false
                }
            }

        }
    }
}