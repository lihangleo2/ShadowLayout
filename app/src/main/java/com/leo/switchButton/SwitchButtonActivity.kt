package com.leo.switchButton

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leo.R
import com.leo.databinding.ActivityScrollviewIndicatorBinding
import com.leo.databinding.ActivitySwitchbuttonBinding

/**
 * @Author leo
 * github：https://github.com/lihangleo2
 * @Date 2025/12/4
 */
class SwitchButtonActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySwitchbuttonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_switchbutton)
        supportActionBar?.title = "Switch的使用"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.run {
            switchButton.setOnCheckedChangeListener { view, isChecked ->
                Toast.makeText(this@SwitchButtonActivity, "${isChecked}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 检查点击的项是否是“返回键”（HomeAsUp按钮的ID是android.R.id.home）
        when (item.itemId) {
            android.R.id.home -> {
                // 在这里编写你的自定义逻辑，例如：
                // saveData() // 先保存数据
                // 然后模拟系统的“向上”导航行为
                onBackPressed() // 或者使用 finish()
                // 如果你使用了 AndroidX 的 Navigation 组件，也可以这样：
                // findNavController().navigateUp()
                return true // 表示事件已处理
            }
        }
        return super.onOptionsItemSelected(item)
    }
}