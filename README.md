# 万能阴影布局-ShadowLayout
[![](https://jitpack.io/v/lihangleo2/ShadowLayout.svg)](https://jitpack.io/#lihangleo2/ShadowLayout)

## 特点功能
任何view被包裹后即可享受阴影，以及系统shape,selector功能（清空项目drawable文件夹）。具体介绍如下：
```
支持定制化阴影：
    1. 随意修改阴影颜色值
    2. 阴影圆角：可统一设置圆角，也可对某几个角单独设置
    3. 阴影x,y偏移量
    4. 随意修改阴影扩散程度,即阴影大小
    5. 隐藏阴影：隐藏某边或多边阴影，或完全隐藏
    6. 可随意代码动态修改阴影
    
不止于阴影；系统shape功能：项目中shape、selector、ripple统统拥有。解放你的双手，清空项目drawable文件夹
    1. shape样式：pressed（按钮点击）、selected（按钮选择）、ripple（点击水波纹）
    2. 背景色设置
    3. stroke边框设置
    4. 渐变色背景色设置
    5. 按钮是否可被点击及不可点击背景
    6. 可绑定textView后，可伴随文案变化，可伴随文案颜色变化
    7. 支持设置图片背景，支持图片selector
    
```


## ShadowLayout动态
* [ShadowLayout3.2.4更新内容，及以往成长](https://github.com/lihangleo2/ShadowLayout/wiki)  
* 注意：3.1.8修复issues79，阴影过渡不平滑。老项目升级为3.1.8时，需将limit*2。（如果你没有改变阴影透明度则不存在这个问题，放心使用3.1.7）
* 注意：3.0后修改大量api及规范命名，如果还在用2.0，不方便转移的可查看[ShadowLayout2.0文档](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/README218.md)  


## Demo
为录制流畅，截图分辨率比较模糊。可在下方扫描二维码下载apk，查看真机效果。下载密码是：123456

![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/ShadowLayout_.png)
<br/>

## 效果展示
为录制流畅，截图分辨率模糊。可下载apk查看真机效果
* ### 1.0功能展示
|基础功能展示|各属性展示|随意更改颜色|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/main.jpg)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/first_show.gif)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/other_show.gif)
<br/>

* ### 2.0功能更新
|2.1.6新增shape,selector功能|2.1.7isSym属性对比|2.1.8单独更改某圆角大小|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/shape_gif.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/isSym_half.jpg" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/corners.gif)
<br/>

* ### 3.0.1版本来袭
|stroke边框及点击|shape及图片selector|组合使用|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/stroke2.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/shapeSelector2.gif" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/groupUse2.gif)
<br/>

* ### 3.1.0新增ripple及渐变色及3.1.1绑定textView
|3.1.0渐变色及ripple|3.1.1绑定textView|
|:---:|:---:|
|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/ripple.gif" alt="Sample">|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/bindTextView.gif" alt="Sample">
<br/>



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
	        implementation 'com.github.lihangleo2:ShadowLayout:3.2.4'
	}
   ```
   
 - 在AndroidX里如何使用？根目录下找到gradle的配置文件gradle.properties里加上以下2句代码：
    ```java
   android.useAndroidX=true
   android.enableJetifier=true
   ```
   
[glide版本冲突终极解决方案](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/GLIDE.md)     


## 基本使用
* #### 一、阴影的简单使用
```xml
            <com.lihang.ShadowLayout
                android:id="@+id/mShadowLayout"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                app:hl_cornerRadius="10dp"
                app:hl_shadowColor="#2a000000"
                app:hl_shadowLimit="5dp"
                >

                <TextView
                    android:id="@+id/txt_test"
                    android:layout_width="wrap_content"
                    android:layout_height="36dp"
                    android:gravity="center"
                    android:paddingLeft="10dp"
                    android:paddingRight="10dp"
                    android:text="圆角"
                    android:textColor="#000" />
            </com.lihang.ShadowLayout>
```
<br/>

* #### 二、stroke边框的简单使用
```xml
            <com.lihang.ShadowLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                app:hl_cornerRadius="10dp"
                app:hl_strokeColor="#000">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="36dp"
                    android:gravity="center"
                    android:paddingLeft="10dp"
                    android:paddingRight="10dp"
                    android:text="圆角边框"
                    android:textColor="#000" />
            </com.lihang.ShadowLayout>
```
<br/>

* #### 三、shape selector的简单使用
```xml
            <com.lihang.ShadowLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginTop="10dp"
                app:hl_cornerRadius="30dp"
                app:hl_cornerRadius_leftTop="0dp"
                app:hl_layoutBackground="#F76C6C"
                app:hl_layoutBackground_true="#89F76C6C"
                app:hl_shapeMode="pressed">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="36dp"
                    android:gravity="center"
                    android:paddingLeft="10dp"
                    android:paddingRight="10dp"
                    android:text="selector的pressed用法，请点击"
                    android:textColor="#fff" />
            </com.lihang.ShadowLayout>
```
<br/>

* #### 四、图片 selector的简单使用
```xml
    <com.lihang.ShadowLayout
        android:id="@+id/ShadowLayout_shape"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"
        app:hl_cornerRadius="18dp"
        app:hl_cornerRadius_rightTop="0dp"
        app:hl_layoutBackground="@mipmap/test_background_false"
        app:hl_layoutBackground_true="@mipmap/test_background_true">


        <TextView
            android:layout_width="wrap_content"
            android:layout_height="36dp"
            android:gravity="center"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="图片selector"
            android:textColor="#fff" />

    </com.lihang.ShadowLayout>
```
如果你觉得麻烦，你还可以这样
```xml
            <com.lihang.ShadowLayout
                android:id="@+id/ShadowLayout_image"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_gravity="center_horizontal"
                android:layout_marginTop="10dp"
                app:hl_layoutBackground="@mipmap/game_6_right"
                app:hl_layoutBackground_true="@mipmap/game_6_wrong"
                app:hl_shapeMode="pressed" />
```
<br/>


* #### 五、渐变色的简单使用
```xml
            <com.lihang.ShadowLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:hl_cornerRadius="18dp"
                app:hl_startColor="#ff0000"
                app:hl_endColor="#0000ff"
                >

                <TextView
                    android:layout_width="160dp"
                    android:layout_height="40dp"
                    android:gravity="center"
                    android:text="渐变色"
                    android:textColor="#fff" />

            </com.lihang.ShadowLayout>
```
<br/>


* #### 六、水波纹ripple的使用
```xml
            <com.lihang.ShadowLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:hl_cornerRadius="18dp"
                app:hl_shadowColor="#2a000000"
                app:hl_shadowLimit="7dp"
                app:hl_layoutBackground="#fff"
                app:hl_layoutBackground_true="#ff0000"
                app:hl_shapeMode="ripple"
                >

                <TextView
                    android:layout_width="160dp"
                    android:layout_height="40dp"
                    android:gravity="center"
                    android:text="水波纹"
                    />

            </com.lihang.ShadowLayout>
```
<br/>


* #### 七、绑定textView，伴随文案及颜色变化
```xml
		<com.lihang.ShadowLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginTop="20dp"
                    app:hl_bindTextView="@+id/txt_press"
                    app:hl_cornerRadius="18dp"
                    app:hl_layoutBackground="#FF9800"
                    app:hl_layoutBackground_true="#ff0000"
                    app:hl_shapeMode="pressed"
                    app:hl_textColor_true="#fff"
                    app:hl_text="点我，press样式"
                    app:hl_text_true="我改变了文案了"
                    >

                    <TextView
                        android:id="@+id/txt_press"
                        android:layout_width="wrap_content"
                        android:layout_height="36dp"
                        android:gravity="center"
                        android:paddingLeft="10dp"
                        android:paddingRight="10dp"
                        android:text="点我，press样式"
                        android:textColor="#000" />

                </com.lihang.ShadowLayout>
```
<br/>



## 属性表格（Attributes）
### 一、关于阴影

|name|format|description|
|:---:|:---:|:---:|
|hl_shadowHidden|boolean|是否隐藏阴影（默认false）|
|hl_shadowColor|color|阴影颜色值,如不带透明，默认透明16%|
|hl_shadowLimit|dimension|阴影扩散程度（dp）|
|hl_shadowOffsetX|dimension|x轴的偏移量（dp）|
|hl_shadowOffsetY|dimension|y轴的偏移量（dp）|
|hl_shadowHiddenLeft|boolean|左边的阴影不可见，其他3边同理|
|hl_shadowSymmetry|boolean|控件区域是否对称（默认true）根据此图理解<br><img src="https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/isSym_half.jpg" alt="Sample"  width="350">|
<br/>


### 二、关于圆角
|name|format|description|
|:---:|:---:|:---:|
|hl_cornerRadius|dimension|包括阴影圆角、shape圆角（dp）|
|hl_cornerRadius_leftTop|dimension|左上圆角，其他角还是hl_cornerRadius值；同理其他3角（dp）|
 <br/>


### 三、关于shape
* #### 3.1、关于shape样式及背景色
|name|format|description|
|:---:|:---:|:---:|
|hl_shapeMode|enum|有3种模式：pressed和selected。和系统shape一样，以及ripple点击水波纹|
|hl_layoutBackground|reference/color|背景色，为false时展示：可以是颜色值，图片以及系统shape样式|
|hl_layoutBackground_true|reference/color|背景色，为true时展示：可以是颜色值，图片以及系统shape样式|
 <br/>


* #### 3.2、关于stroke边框
|name|format|description|
|:---:|:---:|:---:|
|hl_strokeWith|dimension|stroke边框线宽度|
|hl_strokeColor|color|边框颜色值，为false展示|
|hl_strokeColor_true|color|边框颜色值，为true展示|
<br/>


* #### 3.3、关于渐变色
|name|format|description|
|:---:|:---:|:---:|
|hl_startColor|color|渐变起始颜色（设置渐变色后，hl_layoutBackground属性将无效）|
|hl_centerColor|color|渐变中间颜色（可不填）|
|hl_endColor|color|渐变的终止颜色|
|hl_angle|integer|渐变角度（默认0）|
<br/>


* #### 3.4、关于绑定textView
|name|format|description|
|:---:|:---:|:---:|
|hl_bindTextView|reference|当前要绑定的textView的id|
|hl_textColor|color|shape为false是展示的文案颜色|
|hl_textColor_true|color|shape为true是展示的文案颜色|
|hl_text|string|shape为false时展示的文案|
|hl_text_true|string|shape为true时展示的文案|
<br/>



### 四、关于clickable
|name|format|description|
|:---:|:---:|:---:|
|clickable|boolean|设置ShadowLayout是否可以被点击；代码设置：mShadowLayout.setClickable(false);（默认true）|
|hl_layoutBackground_clickFalse|reference/color|Clickable为false时，要展示的图片或颜色。（此属性应当在app:hl_shapeMode="pressed"时生效）|
 <br/>


## 方法表格（Method）
|name|format|description|
|:---:|:---:|:---:|
|setShadowHidden()|boolean|是否隐藏阴影|
|setShadowColor()|color|设置阴影颜色值|
|setShadowLimit()|dimension|设置阴影扩散区域|
|setOffsetX()|dimension|设置阴影的X轴偏移量|
|setOffsetY()|dimension|设置阴影的Y轴偏移量|
|setShadowHiddenTop()|boolean|隐藏上边阴影（同理其他三遍）|
|setCornerRadius()|dimension|设置圆角|
|setLayoutBackground()|color|设置false时的背景颜色值|
|setLayoutBackgroundTrue()|color|设置true时的背景颜色值|
|setStrokeColor()|color|设置false时的边框颜色|
|setStrokeColorTrue()|color|设置true时的边框颜色|
|setStrokeWidth()|dimension|设置边框的粗细|
|setClickable()|boolean|设置ShadowLayout是否可以点击|
|setSpecialCorner()|integer|设置ShadowLayout四个角的大小|
<br/>

## 赞赏

如果你喜欢 ShadowLayout 的功能，感觉 ShadowLayout 帮助到了你，可以点右上角 "Star" 支持一下 谢谢！ ^_^
你也还可以扫描下面的二维码~ 请作者喝一杯咖啡。或者遇到工作中比较难实现的需求请作者帮忙。

![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/pay_ali.png) ![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/pay_wx.png)


如果在捐赠留言中备注名称，将会被记录到列表中~ 如果你也是github开源作者，捐赠时可以留下github项目地址或者个人主页地址，链接将会被添加到列表中
[捐赠列表](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/friend.md)
<br/>


## 其他作品
[RichEditTextCopyToutiao](https://github.com/lihangleo2/RichEditTextCopyToutiao)  
[mPro](https://github.com/lihangleo2/mPro)  
[SmartLoadingView](https://github.com/lihangleo2/SmartLoadingView)
<br/>


## 关于作者。
Android工作多年了，一直向往大厂。在前进的道路上是孤独的。如果你在学习的路上也感觉孤独，请和我一起。让我们在学习道路上少些孤独
<!-- * [关于我的经历](https://mp.weixin.qq.com/s?__biz=MzAwMDA3MzU2Mg==&mid=2247483667&idx=1&sn=1a575ea2c636980e5f4c579d3a73d8ab&chksm=9aefcb26ad98423041c61ad7cbad77f0534495d11fc0a302b9fdd3a3e6b84605cad61d192959&mpshare=1&scene=23&srcid=&sharer_sharetime=1572505105563&sharer_shareid=97effcbe7f9d69e6067a40da3e48344a#rd) -->
 * QQ群： 209010674 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=5e29576e7d2ebf08fa37d8953a0fea3b5eafdff2c488c1f5c152223c228f1d11"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="android交流群" title="android交流群"></a>（点击图标，可以直接加入）
<br/>


## Licenses

```
MIT License

Copyright (c) 2019 leo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```
