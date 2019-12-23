[![](https://jitpack.io/v/lihangleo2/ShadowLayout.svg)](https://jitpack.io/#lihangleo2/ShadowLayout)

## 万能阴影布局，定制化你要的阴影。 ShadowLayout 2.0震撼上线（需要阴影地方，被它嵌套即可享受阴影，阴影可定制化，效果赶超CardView）
* 支持定制化阴影
* 支持随意更改阴影颜色值
* 支持x,y轴阴影偏移 
* 可随意更改阴影扩散区域 
* 支持阴影圆角属性
* 支持单边或多边不显示阴影
* 支持ShadowLayout背景填充颜色，背景圆角随阴影圆角改变

#### 2.1.4 更新功能（转载请注明出处）
* 优化阴影bitmap，且采用Bitmap.Config.ARGB_4444，减小内存
* 新增添加颜色值不带透明度时，默认透明度为16%
<br>

### [最近有人反应内存情况，请看分析](https://juejin.im/post/5d4c1392f265da03bc126584#heading-12)

## 效果展示（截图分辨率模糊，真机运行效果赶超CardView）
|基础功能展示|各属性展示|随意更改颜色|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/main.jpg)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/first_show.gif)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/other_show.gif)

<br>

## 扫描二维体验效果(下载密码是：123456)
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
	        implementation 'com.github.lihangleo2:ShadowLayout:2.1.4'
	}
   ```
   
<br>

## 使用(你也可以什么属性都不加，使用默认值)
```xml
      <com.lihang.ShadowLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:hl_cornerRadius="18dp"
        app:hl_dx="0dp"
        app:hl_dy="0dp"
        app:hl_leftShow="false"
        app:hl_shadowColor="#2aff0000"
	app:hl_shadowBackColor="#fff"
        app:hl_shadowLimit="5dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="36dp"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
	    android:gravity="center"
            android:text="定制化你的阴影"
            android:textColor="#000" />

    </com.lihang.ShadowLayout>
```
<br>

 # 自定义属性
 ####  圆角属性
 - app:hl_cornerRadius="18dp"  阴影圆角属性（同时如果设置了背景填充色也是背景圆角）
  
 #### 阴影扩散程度
 - app:hl_shadowLimit="5dp"  阴影的扩散区域
  
 #### 阴影布局背景颜色值
 - app:hl_shadowBackColor="#fff" 阴影布局背景填充色，圆角属性即是阴影圆角
 
 ####  阴影的颜色
 - app:hl_shadowColor="#2a000000"  阴影的颜色可以随便改变,透明度的改变可以改变阴影的清晰程度  
 特别注意：系统方法，颜色值必须带透明度。如果你不想加透明度，则默认透明度为16%
```java
	//这里是setShadowLayer源码的描述，去掉了部分代码便于理解
	/*
     * The alpha of the shadow will be the paint's alpha if the shadow color is
     * opaque, or the alpha from the shadow color if not.
     */
    public void setShadowLayer(float radius, float dx, float dy, int shadowColor) {
      mShadowLayerRadius = radius;
      mShadowLayerDx = dx;
      mShadowLayerDy = dy;
      mShadowLayerColor = shadowColor;
      nSetShadowLayer(mNativePaint, radius, dx, dy, shadowColor);
    }
```

 #### x轴的偏移量
 - app:hl_dx="0dp"    也可以理解为左右偏移量
 
 #### y轴的偏移量
 - app:hl_dy="0dp"    也可以理解为上下的偏移量

 #### 阴影的4边可见不可见（与偏移量无关）
 - app:hl_leftShow="false"    左边的阴影不可见，其他3边保持不变


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
