## ShadowLayout阴影布局（需要加阴影的控件只要被他嵌套，即刻享受）

## 效果展示（图片较模糊，下载demo自行观看。觉得不错就star下吧）
##### 后期有时间后，我会将功能完善的更好、敬请关注
![alt text](https://github.com/lihangleo2/ShadowLayout/blob/master/show.jpg)

## 添加依赖

 - 项目build.gradle添加如下
   ```java
   allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
   ```
 - app build.gradle添加如下
    ```java
   dependencies {
	        implementation 'com.github.lihangleo2:ShadowLayout:1.0'
	}
   ```
   
## 使用
```xml
      <com.lihang.ShadowLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:hl_cornerRadius="18dp"
        app:hl_dx="0dp"
        app:hl_dy="0dp"
        app:hl_leftShow="false"
        app:hl_shadowColor="#2a000000"
        app:hl_shadowLimit="5dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="36dp"
            android:background="@drawable/shape_show_"
            android:gravity="center"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="完全圆形圆角"
            android:textColor="#000" />

    </com.lihang.ShadowLayout>
```

 # Api说明
 ## ① 圆角属性
  - app:hl_cornerRadius="18dp"  阴影圆角属性
  
 ## ② 阴影扩散程度
  - app:hl_shadowLimit="5dp"  阴影的扩散区域
  
 ## ③ 阴影的颜色
 - app:hl_shadowColor="#2a000000"  阴影的颜色可以随便改变,透明度的改变可以改变阴影的清晰程度

 ## ④ x轴的偏移量
 - app:hl_dx="0dp"    也可以理解为左右偏移量
 
 ## ⑤ y轴的偏移量
 - app:hl_dy="0dp"    也可以理解为上下的偏移量

 ## ⑥ 阴影的4边可见不可见（与偏移量无关）
 - app:hl_leftShow="false"    左边的阴影不可见，其他3边保持不变

