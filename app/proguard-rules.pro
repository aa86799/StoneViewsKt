# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-verbose
-dontoptimize  #不优化
-dontpreverify #不预校验

# 混淆字典
-obfuscationdictionary obfuscation_dictionary.txt
# 类 混淆字典
-classobfuscationdictionary obfuscation_dictionary.txt
# 包 混淆字典
-packageobfuscationdictionary obfuscation_dictionary.txt

#保持源文件和行号的信息,用于混淆后定位错误位置
-keepattributes SourceFile,LineNumberTable

-libraryjars libs/aspectjrt-1.7.3.jar  #不处理 class_path指定的文件
-libraryjars libs/isoparser-1.0.6.jar  #不处理 class_path指定的文件
-dontusemixedcaseclassnames  #不使用大小写混合类名
-dontskipnonpubliclibraryclasses #不跳过jars中的非public classes。在proguard4.5时，是默认选项
-repackageclasses com.stone #将包里的类混淆成n个再重新打包到一个统一的package中 会覆盖flattenpackagehierarchy选项


-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite {
    <fields>;
}


-keepattributes *Annotation* #保持含有Annotation字符串的 attributes
#保持 本地化方法及其类声明
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持view及子类成员： getter setter
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#保持Activity及子类成员：参数为一个View类型的方法   如setContentView(View v)
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持枚举类的成员：values方法和valueOf  (每个enum 类都默认有这两个方法)
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保持Parcelable的实现类和它的成员：类型为android.os.Parcelable$Creator 名字任意的 属性
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 任意包名.R类的类成员属性。  即保护R文件中的属性名不变
-keepclassmembers class **.R$* {
    public static <fields>;
}
#不警告 support包
-dontwarn android.support.**

-keepattributes Signature   #保持签名

# gson相关
-keep class sun.misc.Unsafe {*;}
-keep class com.google.gson.examples.android.model.**{*;}
-keep class com.tv.kuaisou.bean.* {*;}

# eventbus 相关
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# 保持，android 组件和子类的声明
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
