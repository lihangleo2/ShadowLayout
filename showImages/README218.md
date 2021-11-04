[![](https://jitpack.io/v/lihangleo2/ShadowLayout.svg)](https://jitpack.io/#lihangleo2/ShadowLayout)

## 万能阴影布局，定制化你要的阴影。 ShadowLayout 2.0震撼上线（需要阴影地方，被它嵌套即可享受阴影，阴影可定制化，效果赶超CardView）
* 支持定制化阴影
* 支持随意更改阴影颜色值
* 支持x,y轴阴影偏移 
* 可随意更改阴影扩散区域 
* 支持阴影圆角属性
* 支持单边或多边不显示阴影
* 支持ShadowLayout背景填充颜色，背景圆角随阴影圆角改变
* 控件支持动态设置shape和selector（项目里再也不用画shape了）

#### 2.1.8更新功能及ShadowLayout成长历程
* 新增更改四个圆角属性
 [Shadow成长历程](https://github.com/lihangleo2/ShadowLayout/wiki)  
 
<br>

### [最近有人反应内存情况，请看分析](https://juejin.im/post/5d4c1392f265da03bc126584#heading-12)

## 效果展示（截图分辨率模糊，真机运行效果赶超CardView）
|基础功能展示|各属性展示|随意更改颜色|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/main.jpg)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/first_show.gif)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/other_show.gif)

### 后续功能更新
|2.1.6新增shape,selector功能|2.1.7isSym属性对比|2.1.8单独更改某圆角大小|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/shape_gif.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/corners.gif)
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
	        implementation 'com.github.lihangleo2:ShadowLayout:2.1.8'
	}
   ```
   
<br>

## 使用(这里只放了几个基本属性，全部属性请看下方介绍)
```xml
      <com.lihang.ShadowLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:hl_cornerRadius="18dp"
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
 #### 1、圆角属性 app:hl_cornerRadius="18dp"
 - 阴影圆角属性（同时如果设置了背景填充色也是背景圆角）
 <br>
  
 #### 2、阴影扩散程度 app:hl_shadowLimit="5dp"
 - 阴影的扩散区域
  <br>
  
 #### 3、阴影布局背景颜色值 app:hl_shadowBackColor="#fff"
 - 阴影布局背景填充色，同时注意圆角属性即是阴影圆角
  <br>
 
 #### 4、阴影的颜色 app:hl_shadowColor="#2a000000"
 - 阴影的颜色可以随便改变,透明度的改变可以改变阴影的清晰程度，如果未设置透明度，则默认透明度为16% 
  <br>

 #### 5、x轴的偏移量 app:hl_dx="0dp"
 - 也可以理解为左右偏移量
  <br>
 
 #### 6、y轴的偏移量 app:hl_dy="0dp"
 - 也可以理解为上下的偏移量
  <br>

 #### 7、阴影的4边可见不可见 app:hl_leftShow="false"
 - 左边的阴影不可见，其他3边保持不变
  <br>

 #### 8、动态设置shape,selector。pressed和selected背景颜色值 app:hl_shadowBackColorClicked="#ff0000"
 - 不点击状态下，显示hl_shadowBackColor颜色值。点击时切换成hl_shadowBackColorClicked。松开手指后恢复正常；如果是setSelect（true）则显示hl_shadowBackColorClicked，false显示hl_shadowBackColor
 <br>
 
 #### 9、设置selector的样式 app:hl_selectorMode="selected"
 - selected表示只支持selected样式；pressed表示只支持点击状态；selected|pressed则表示2者都支持
 <br>
 
 #### 10、是否使用阴影 app:hl_isShowShadow="false"
 - 这个属性是因为有些同学只想使用shape和selector功能，那么就不使用阴影就好了
 <br>
 
 #### 11、 控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走:app:hl_isSym="false"
 - 如下图：右边是对称，不管你怎么偏移，控件所占的区域都是均等的，这也造成了有些同学ui上不好控制。那么你可以加上app:hl_isSym="false"属性。控件区域随着阴影改变，如下图左边样子。
<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="350">

<br>

#### 11、更改控件4个圆角的大小：app:hl_cornerRadius_leftTop="0dp"；app:hl_cornerRadius_leftBottom="0dp"；app:hl_cornerRadius_rigthTop="0dp"；app:hl_cornerRadius_rightBottom="0dp"
- 这个属性是单独控制某一个圆角的圆角大小

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
