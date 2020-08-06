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

#### 3.0.1更新功能及ShadowLayout成长历程
* 完善shape功能，解放你的drawable文件。包括有：图片selector、shape selector、stroke selector
* ShadowLayout提供了背景圆角方案
* 修改了部分单词拼写错误，及大量属性命名。更规范
* 其中也加上了设置Clickable ="false"的样式。
* 修改了目前已知bug，及修改和完善了不规则圆角的阴影方案
 [Shadow成长历程](https://github.com/lihangleo2/ShadowLayout/wiki)  
 
 #### 注意
 因为3.0修改了大量api及规范命名，如不方便转移还在使用2.0的。可查看2.0文档[ShadowLayout 2.1.8](https://github.com/lihangleo2/ShadowLayout/blob/master/README218.md)  
<br>

### [最近有人反应内存情况，请看分析](https://juejin.im/post/5d4c1392f265da03bc126584#heading-12)

## 效果展示（截图分辨率模糊，真机运行效果赶超CardView）
|基础功能展示|各属性展示|随意更改颜色|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/main.jpg)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/first_show.gif)|![](https://github.com/lihangleo2/ShadowLayout/blob/master/other_show.gif)

### 2.0功能更新
|2.1.6新增shape,selector功能|2.1.7isSym属性对比|2.1.8单独更改某圆角大小|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/shape_gif.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/corners.gif)

### 3.0.1版本来袭
|stroke边框及点击|shape及图片selector|组合使用|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/ShadowLayout/blob/master/stroke2.gif)|<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/shapeSelector2.gif" alt="Sample"  width="481">|![](https://github.com/lihangleo2/ShadowLayout/blob/master/groupUse2.gif)
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
	        implementation 'com.github.lihangleo2:ShadowLayout:3.0.1'
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


 # 自定义属性
 
 ### 一、关于阴影
 #### 1、是否展示阴影 app:hl_shadowHidden="true"
 - true为隐藏
 <br>
 
 #### 2、阴影的颜色 app:hl_shadowColor="#29000000"
 - 需要带透明度的颜色，如果不带透明度。默认设置透明底16%
 <br>

 #### 3、阴影扩散程度 app:hl_shadowLimit="5dp"
 - 阴影的扩散区域
  <br>
  
 #### 4、 控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走:app:hl_shadowSymmetry="false"
 - 如下图：右边是对称，不管你怎么偏移，控件所占的区域都是均等的，这也造成了有些同学ui上不好控制。那么你可以加上app:hl_isSym="false"属性。控件区域随着阴影改变，如下图左边样子。
<img src="https://github.com/lihangleo2/ShadowLayout/blob/master/isSym_half.jpg" alt="Sample"  width="350">
  
 #### 5、x轴的偏移量 app:hl_shadowOffsetX="0dp"
 - 也可以理解为左右偏移量
  <br>
  
 #### 6、y轴的偏移量 app:hl_shadowOffsetY="0dp"
 - 也可以理解为上下的偏移量
  <br>
  
 #### 7、阴影的4边，隐藏哪一边 app:hl_shadowHiddenLeft="true"
 - 左边的阴影不可见，其他3边保持不变，其他3边同理
  <br>

 ### 二、关于圆角
 #### 8、圆角 app:hl_cornerRadius="30dp"
 - 统一大小，其中包括了阴影，shape、背景图、stroke边框圆角
 <br>
 
 #### 9、某个角的圆角 app:hl_cornerRadius_leftTop="0dp"
 - 左上角圆角大小，设置后。左上角会忽略hl_cornerRadius的熟悉，其他边保持不变。其他3个角同理
 <br>
  
 ### 三、关于shape及selector
 #### 10、控件按压方式 app:hl_shapeMode="pressed"
 - 有2种模式：pressed和selected。和系统一样。不难理解
 <br>
  
 #### 11、false时：控件默认背景 app:hl_layoutBackground="#fff"
 - 这里可以传颜色值，也可以传图片。当为false时的默认背景
 <br>
  
 #### 12、true时：控件默认背景 app:hl_layoutBackground_true="#ff0000"
 - 这里可以传颜色值，也可以传图片。当为true时的默认背景
 <br>
 
 ### 四、关于stroke边框
 #### 13、stroke边框线宽度 app:hl_strokeWith="1dp"
 - stroke边宽，stroke的宽度
 <br>
 
 #### 14、false时：边框默认颜色 app:hl_strokeColor="#fff"
 - 边框颜色值。当为false时的默认颜色
 <br>

 #### 15、true时：边框默认颜色 app:hl_strokeColor_true="#ff0000"
 - 边框颜色值。当为true时的默认颜色
 <br>
 
  #### 16、类似于系统的Clickable app:hl_layoutBackgroundClickableFalse="false"
 - Clickable为false时，要展示的图片或颜色。注意，此属性应当在app:hl_shapeMode="pressed"生效。其他模式下只是不能点击，并不会展示此图
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
