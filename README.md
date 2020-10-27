[![](https://jitpack.io/v/lihangleo2/ShadowLayout.svg)](https://jitpack.io/#lihangleo2/ShadowLayout)

## 万能阴影布局，定制化你要的阴影。 ShadowLayout 3.0来袭（需要阴影地方，被它嵌套即可享受阴影，阴影可定制化，效果赶超CardView）
* 支持定制化阴影
* 支持随意更改阴影颜色值
* 支持x,y轴阴影偏移 
* 可随意更改阴影扩散区域 
* 支持阴影圆角属性
* 支持单边或多边不显示阴影
* 支持ShadowLayout背景填充颜色，背景圆角随阴影圆角改变
* 控件支持动态设置shape和selector（项目里再也不用画shape了）

#### 3.1.0更新功能及ShadowLayout成长历程
* shapeMode新增了ripple（点击水波纹样式），具体会在README中说明
* ShadowLayout，新增了渐变色，这也是issues大量出现的需求，感谢大家的支持

 [Shadow成长历程](https://github.com/lihangleo2/ShadowLayout/wiki)  
 
 #### 注意
 因为3.0修改了大量api及规范命名，如不方便转移还在使用2.0的。可查看2.0文档[ShadowLayout 2.1.8](https://github.com/lihangleo2/ShadowLayout/blob/master/README218.md)  
<br>

### [最近有人反应内存情况，请看分析](https://juejin.im/post/5d4c1392f265da03bc126584#heading-12)

## 效果展示（截图分辨率模糊，真机运行效果赶超CardView）
|基础功能展示|各属性展示|随意更改颜色|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/main.jpg)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/first_show.gif)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/other_show.gif)
<br>

### 2.0功能更新
|2.1.6新增shape,selector功能|2.1.7isSym属性对比|2.1.8单独更改某圆角大小|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/shape_gif.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/corners.gif)
<br>

### 3.0.1版本来袭
|stroke边框及点击|shape及图片selector|组合使用|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/stroke2.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/shapeSelector2.gif" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/groupUse2.gif)
<br>

### 3.1.0新增ripple及渐变色
|stroke边框及点击|
|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/ripple.gif)|
<br>

## gif为了流畅清晰度模糊，扫描二维体验效果(下载密码是：123456)
![](https://github.com/lihangleo2/ShadowLayout/blob/master/ShadowLayout_.png)

<br>

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
	        implementation 'com.github.lihangleo2:ShadowLayout:3.1.0'
	}
   ```
   
<br>

## 基本使用
### 一、阴影的简单使用
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
<br>

### 二、stroke边框的简单使用
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
<br>

### 三、shape selector的简单使用
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
<br>

### 三、图片 selector的简单使用
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

<br>

### 四、渐变色的简单使用
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

<br>

### 五、水波纹ripple的使用
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

<br>





# 属性表格
## 一、关于阴影

|name|format|description|
|:---:|:---:|:---:|
|hl_shadowHidden|boolean|是否隐藏阴影（默认false）|
|hl_shadowColor|color|阴影颜色值,如不带透明，默认透明16%|
|hl_shadowLimit|dimension|阴影扩散程度（dp）|
|hl_shadowOffsetX|dimension|x轴的偏移量（dp）|
|hl_shadowOffsetY|dimension|y轴的偏移量（dp）|
|hl_shadowHiddenLeft|boolean|左边的阴影不可见，其他3边同理|
|hl_shadowSymmetry|boolean|控件区域是否对称（默认true）根据此图理解<br><img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="350">|

<br>

## 二、关于圆角
|name|format|description|
|:---:|:---:|:---:|
|hl_cornerRadius|dimension|包括阴影圆角、shape圆角（dp）|
|hl_cornerRadius_leftTop|dimension|左上圆角，其他角还是hl_cornerRadius值；同理其他3角（dp）|
 
<br>

## 三、关于shape
### 3.1、关于shape样式及背景色
|name|format|description|
|:---:|:---:|:---:|
|hl_shapeMode|enum|有3种模式：pressed和selected。和系统shape一样，以及ripple点击水波纹|
|hl_layoutBackground|reference/color|背景色，为false时展示：可以是颜色值，图片以及系统shape样式|
|hl_layoutBackground_true|reference/color|背景色，为true时展示：可以是颜色值，图片以及系统shape样式|
 
<br>

### 3.2、关于stroke边框
|name|format|description|
|:---:|:---:|:---:|
|hl_strokeWith|dimension|stroke边框线宽度|
|hl_strokeColor|color|边框颜色值，为false展示|
|hl_strokeColor_true|color|边框颜色值，为true展示|

<br>

### 3.3、关于渐变色
|name|format|description|
|:---:|:---:|:---:|
|hl_startColor|color|渐变起始颜色（设置渐变色后，hl_layoutBackground属性将无效）|
|hl_centerColor|color|渐变中间颜色（可不填）|
|hl_endColor|color|渐变的终止颜色|
|hl_angle|integer|渐变角度（默认0）|

<br>

## 四、关于clickable
|name|format|description|
|:---:|:---:|:---:|
|clickable|boolean|设置ShadowLayout是否可以被点击；代码设置：mShadowLayout.setClickable(false);（默认true）|
|hl_layoutBackground_clickFalse|reference/color|Clickable为false时，要展示的图片或颜色。（此属性应当在app:hl_shapeMode="pressed"时生效）|
 
<br>


## 关于作者。
Android工作多年了，一直向往大厂。在前进的道路上是孤独的。如果你在学习的路上也感觉孤独，请和我一起。让我们在学习道路上少些孤独
* [关于我的经历](https://mp.weixin.qq.com/s?__biz=MzAwMDA3MzU2Mg==&mid=2247483667&idx=1&sn=1a575ea2c636980e5f4c579d3a73d8ab&chksm=9aefcb26ad98423041c61ad7cbad77f0534495d11fc0a302b9fdd3a3e6b84605cad61d192959&mpshare=1&scene=23&srcid=&sharer_sharetime=1572505105563&sharer_shareid=97effcbe7f9d69e6067a40da3e48344a#rd)
 * QQ群： 209010674 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=5e29576e7d2ebf08fa37d8953a0fea3b5eafdff2c488c1f5c152223c228f1d11"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="android交流群" title="android交流群"></a>（点击图标，可以直接加入）

<br>

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
