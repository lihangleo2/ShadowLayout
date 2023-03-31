## glide版本冲突终极解决方案
如果你出现了版本冲突，不知道怎么解决的话。就用Library方式集成，同时修改shadowLibrary里build.gradle的glide版本号。（如果版本过低的话，可能需要修改GlideRoundUtils里glide的加载方法）

## 如果你不知道怎么集成的话，按如下步骤
### 1、首先下载本demo，打开项目如下
![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/glide1.png)


### 2、然后点击 File --> New --> Import Module
![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/glide2.jpg)


### 3、出现如下假面后，点击右边的文件夹，选中shadowLibrary，然后点击Finish
![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/glide3.png)


finish后就会以module的方式出现在你的项目里。


### 4、来到你项目的build.gradle里引入下
implementation project(':shadowLibrary')


### 5、最后来到shadowLibrary的bulid.gradle里修改下glide版本即可
![](https://github.com/lihangleo2/ShadowLayout/blob/master/showImages/glide4.png)



