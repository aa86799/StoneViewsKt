# StoneViews [![AppVeyor](https://img.shields.io/badge/StoneViews-stone-red.svg)](https://github.com/aa86799/StoneViews)   [![HitCount](https://hits.dwyl.com/aa86799/StoneViewsKt.svg?style=flat)](http://hits.dwyl.com/aa86799/StoneViewsKt)

  
  之前的[MyCustomView](https://github.com/aa86799/MyCustomView)项目，以module来分隔各种效果，造成后期
  setting.xml越来越难控制(特别是在创建临时性的demo-module后)。
  本项目会穿插kotlin代码，以达到自我熟练使用的目的。

# Kotlin 小坑
  SurfaceView用Kotlin写，就是无法显示view，吐血，好坑。改成继承View，暂时没问题

# All Effects

 - RouletteView 自实现轮盘，实现圆形等分成扇形绘制，每个扇形区的点击事件。

    ![RouletteView](https://github.com/aa86799/images/blob/master/rouletteview2.gif)

 - SatelliteMenu 卫星式菜单，绘制四分之一圆，n个item，则夹角为90度/(n-1); 利用弧度、角度、半径(作三角斜边)，以正、余弦公式求(x,y)

    ![SatelliteMenu](https://github.com/aa86799/images/blob/master/satellitemenu.gif)

 - RadarView 雷达效果，主要是SweepGradient的运用

    ![RadarView](https://github.com/aa86799/images/blob/master/radar.gif)

 - BaseGrayActivity(通过 window.decorView 实现灰度化)、InjectGrayWebView(注入 css 实现灰度化)
 
    ![BaseGrayActivity](https://github.com/aa86799/images/blob/master/grayapp.png)

 - ObliqueProgressbarView 斜线进度条，与随机位置绘制小点
 
    ![ObliqueProgressbarView](https://github.com/aa86799/images/blob/master/ObliqueProgressbarView.gif) 

 - CircleProgressbarView 圆环进度。自适应text宽度
 
    ![CircleProgressbarView](https://github.com/aa86799/images/blob/master/CircleProgressbarView.gif)  

 - ComplexProgressBarView 左边横向圆角进度条，右边文本为  "进度/最大进度"
 
    ![ComplexProgressBarView](https://github.com/aa86799/images/blob/master/ComplexProgressBarView.gif)
    
 - ClockView 时钟
 
    ![ComplexProgressBarView](https://github.com/aa86799/images/blob/master/ClockView.gif)
    
 - TabLayoutFragment,  MD-TabLayout，自定义Tab，加ViewPager+FragmentPagerAdapter 
 
    ![ComplexProgressBarView](https://github.com/aa86799/images/blob/master/TabLayoutFragment.gif)

 - CircleLayoutFragment:  FloatingActionButton + ConstraintLayout(圆形布局) + 两个属性动画的同步使用
 
    ![ComplexProgressBarView](https://github.com/aa86799/images/blob/master/CircleLayout.gif)

 - obfuscation_dictionary.txt 代码混淆字典，见 proguard-rules.pro 中的应用。编译后，dex2jar 看到的效果：

   ![obfuscation dictionary](https://github.com/aa86799/images/blob/master/obfuscation_code.jpg)

