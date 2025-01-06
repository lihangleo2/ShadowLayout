# SmartLoadingView - 一个自带loading的联网请求按钮

## 特点功能
想让你的按钮动起来吗，想让你的app具备活力吗。那来试试它吧。具体介绍如下：
```
    1. 支持在任何布局上使用
    2. smart_full_screen模式：全屏扩散跳转动画，让你的按钮和跳转页面更有活力
    3. smart_tick模式：仿抖音，关注按钮及动画
    4. smart_tick_hide模式：打勾动画及隐藏
    5. smart_tick_center_hide模式：打勾隐藏，及移动到屏幕中间提醒模式
    6. smart_button模式：正常关注和取消关注
```

## 效果展示
为录制流畅，截图分辨率模糊。可下载apk查看真机效果
* ### smart_full_screen模式：全屏扩散动画（不支持关注）
|全屏扩散页面跳转|加载失败|失败文案在按钮上|
|:---:|:---:|:---:|
|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/fullScreen.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/fullScreen_fail_toast.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/fullScreen_fail_show.gif" alt="Sample"  width="100%">
<br/>

* ### smart_button模式：正常按钮动画（支持关注）
|关注并加载成功|关注失败|不带动画关注|
|:---:|:---:|:---:|
|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartButton.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartButton_fail.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartButton_noAnimal.gif" alt="Sample"  width="100%">
<br/>

* ### smart_tick模式：仿抖音打勾关注（支持关注）；smart_tick_hide模式：打勾隐藏（支持关注）；smart_tick_center_hide模式：打勾隐藏，并移至中间提醒（支持关注）。
|仿抖音打勾关注|打勾隐藏|打勾隐藏，移至中间|
|:---:|:---:|:---:|
|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartTick.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartTick_hide.gif" alt="Sample"  width="100%">|<img src="https://github.com/lihangleo2/SmartLoadingView/blob/master/gif/smartTick_hide_center.gif" alt="Sample"  width="100%">
<br/>

## 基本使用
#### 一、简单使用
xml设置如下，注意app:hl_button_type="smart_button"
```xml
            <com.lihang.SmartLoadingView
                android:id="@+id/smart_loading_view"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="#37B3C3"
                android:text="点击关注"
		app:hl_animaled_text="关注成功"
                android:textColor="#ffffff"
                android:textSize="16sp"
                app:hl_button_type="smart_button"
                app:hl_corners_radius="30dp" />
```
<br>

使用如下：没看错就这么简单（5种buttonType均如此）
```java
//1.1 设置点击事件，点击调用加载loading
smartLoadingView.startLoading()

//1.2 联网成功，关注成功调用如下代码
smartLoadingView.finishLoading()

//1.3 联网失败，调用如下代码
smartLoadingView.finishLoading(false)

```
<br>

#### 二、全屏模式：smart_full_screen（注意：此模式不支持关注）
##### 2.1 全屏扩散及页面跳转：smartLoadingView.finishLoadingWithFullScreen(Activity activity, Class clazz)
xml如下：
```xml
            <com.lihang.SmartLoadingView
                android:id="@+id/smart_loading_view"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="#37B3C3"
                android:text="点击加载，2s后加载成功并跳转"
                android:textColor="#ffffff"
                android:textSize="16sp"
                app:hl_button_type="smart_full_screen"
                app:hl_corners_radius="30dp" />
```
<br>

当点击按钮时，开启加载loading
<br>

```java
smartLoadingView.startLoading()
```
<br>

当得到联网结果为success，且需要跳转页面时调用如下：
```java
smartLoadingView.finishLoadingWithFullScreen(this, SecondActivity::class.java)
```
<br>

如果你不想用封装的api,想再扩散动画结束后自己操作，你可以使用如下方法：
```java
//kotlin使用如下：
smartLoadingView.finishLoading(true) {
	//处理自定义逻辑
}

//java使用如下：
smartLoadingView.finishLoading(true, success -> {
	//处理自定义逻辑
});
```
<br>

##### 2.2 如果联网结果失败fail时
执行完，就会平滑回到初始状态
```java
//kotlin使用如下：
smartLoadingView.finishLoading(false) {
    ToastUtils.showShort("加载失败")
}

//java使用如下：
smartLoadingView.finishLoading(false, success -> {
    ToastUtils.showShort("加载失败")
});
```
<br>

##### 2.3 如果联网结果失败,你想将错误信息显示在按钮上可以这样
xml如下：
```xml
            <com.lihang.SmartLoadingView
                android:id="@+id/smart_loading_view"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="#3776C3"
                android:text="点击加载，展示失败文案"
                android:textColor="#ffffff"
                android:textSize="16sp"
                app:hl_animaled_background="#f54949"
                app:hl_animaled_text="网络异常，请稍后再试噢"
                app:hl_animaled_textColor="#ffffff"
                app:hl_button_type="smart_full_screen"
                app:hl_corners_radius="30dp" />
```
<br>

调用如下即可。
```java
//如果想自定义错误文案，在调用finishLoading前，设置文案
//smartLoadingView.setAnimaledText("我是自定义错误文案")
smartLoadingView.finishLoading(false)

```
<br>

#### 三、正常模式详细讲解：smart_button（支持关注）
特别说明：smart_button、smart_tick、smart_tick_hide、smart_tick_center_hide 这四种模式，用法一致。所以这里以smart_button 讲解为主
##### 3.1 这里我们用一个关注功能来说：如果我们有个按钮，初始状态显示"点击关注"，点击按钮进行网络请求，成功了显示"关注成功"；此时再点击，进行网络请求，成功后再显示"点击关注"
当点击按钮时， 我们要判断当前是什么状态，来进行接下来的逻辑：
```java
if (!smartLoadingView.isFinished) {
    //当前不时结束，状态，开启加载loading
    smartLoadingView.startLoading()
} else {
    //当前结束时，再次点击回到初始状态
    //kotlin使用
    smartLoadingView.isFinished = false
    //java使用
    //smartLoadingView.setFinished(false);
}
```
<br>

##### 3.2 联网出结果，成功 or 失败同样调用如下
```java
//true则走成功，false则走失败
//kotlin使用
smartLoadingView.finishLoading(true) {
	
}

//java使用
smartLoadingView.finishLoading(true, success -> {
    
});
```
<br>

##### 3.3 如果你不想使用动画，可以调用如下api。
```java
//kotlin使用
smartLoadingView.isFinished = true

//java使用
smartLoadingView.setFinished(true);
```
<br>

## 属性表格（Attributes）
因为SmartLoadingView就是TextView的拓展控件。部分属性延用了系统属性

|name|format|description|系统api|
|:---:|:---:|:---:|:---:|
|android:text|string|文案内容|是|
|app:hl_animaled_text|string|动画结束文案,默认为text|否|
|android:textColor|color|文案颜色|是|
|app:hl_animaled_textColor|color|动画结束文字颜色,默认为textColor|否|
|android:textSize|dimension|文案字体大小|是|
|android:background|color|背景色|是|
|app:hl_animaled_background|color|动画结束背景色,默认为background颜色值|否|
|app:hl_corners_radius|dimension|圆角属性|否|
|android:enabled|boolean|是否可被点击|是|
|app:hl_unEnabled_background|color|不可点击状态下背景色|否|
|app:hl_ellipsize|enum|reverse:来回滚动；marquee：跑马灯。需文字大于控件宽度生效|否|
|app:hl_ellipsize_speed|integer|文字滚动速度|否|
|app:hl_button_type|enum|5种buttonType样式|否|
<br/>


## 方法表格（Method）
|name|format|description|
|:---:|:---:|:---:|
|startLoading()|void|开启加载loading|
|setFinished()|boolean|不带动画设置控件结束状态|
|finishLoading()|boolean|带动画设置控件结束状态|
<br/>





 



 
 

