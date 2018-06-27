# WaterDropIndicatorDemo
水滴效果指示器
有任何问题或者建议欢迎大家在Issues里向我提出
# 效果图
![效果预览](https://github.com/juyao/WaterDropIndicatorDemo/blob/master/gif/ezgif-4-882802b2e1.gif)
# Gradle快速集成
   Add it in your root build.gradle at the end of repositories:
   ```
   allprojects {
   		repositories {
   			...
   			maven { url 'https://jitpack.io' }
   		}
   	}

   	```
   	在项目中添加依赖：
   	```
   	dependencies {
    	        implementation 'com.github.juyao:WaterDropIndicatorDemo:v1.0.2'
    	}
    ```
# 使用方法：
    在xml直接引用：
    ```
    <top.dodeman.waterdropindicator.WaterDropIndicator
            android:id="@+id/waterdrop"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:selectColor="@color/colorAccent"
            app:defaultColor="@color/colorPrimary"
            app:itemSpace="60dp"
            app:bigRadius="15dp"/>
    ```
    代码中引用：
    ```
    viewpager.adapter=MyVpAdapter()
    waterdrop.setViewPager(viewpager)//关联viewpager
    waterdrop.setItemNum(3)//必须方法，设置viewpager item的个数
    ```
 ## 属性说明
|属性名称|作用|
|:---|:---|
|selectColor|选中的颜色|
|defaultColor|默认的颜色|
|itemSpace|指示器之间的距离|
|bigRadius|指示器半径|

