## 因jitpack gradle7.0问题，导致原有版本3.2.4无法依赖问题。（实属jitpack大姨夫问题，请大家放心使用）
3.3.2已经解决以上问题，以及demo里的gradle也升级到最新，本以为已发布的不会有问题，给大家带来的不便深感抱歉。

## 解决方法
原有3.2.4 release版本超过7天不能被更新。如果还在使用3.2.4且无法依赖的话，依赖以下
```java
implementation 'com.github.lihangleo2:ShadowLayout:3.2.4rz'
```



