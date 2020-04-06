# StoneViews [![AppVeyor](https://img.shields.io/badge/StoneViews-stone-red.svg)](https://github.com/aa86799/StoneViews)
  之前的[MyCustomView](https://github.com/aa86799/MyCustomView)项目，以module来分隔各种效果，造成后期
  setting.xml越来越难控制(特别是在创建临时性的demo-module后)。
  本项目会穿插kotlin代码，以达到自我熟练使用的目的。

# Kotlin 小坑
  SurfaceView用Kotlin写，就是无法显示view，吐血，好坑。改成继承View，暂时没问题

# All Effects

 - RouletteView 自实现轮盘，实现圆形等分成扇形绘制，每个扇形区的点击事件。

    ![RouletteView](https://github.com/aa86799/images/blob/master/rouletteview.gif)

 - SatelliteMenu 卫星式菜单，绘制四分之一圆，n个item，则夹角为90度/(n-1); 利用弧度、角度、半径(作三角斜边)，以正、余弦公式求(x,y)

    ![SatelliteMenu](https://github.com/aa86799/images/blob/master/satellitemenu.gif)

 - RadarView 雷达效果，主要是SweepGradient的运用

    ![RadarView](https://github.com/aa86799/images/blob/master/radar.gif)

