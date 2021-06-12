/* 会使用 kotlin androidx 相关库 */

/* https://maven.google.com/web/index.html 查看google相关的类库的版本 */

/* -------------------------------- 构建设置 -------------------------------------- */
val builds = mutableMapOf<String, Any>()
extra["builds"] = builds
builds["compileSdkVersion"] = 30
builds["minSdkVersion"] = 19
builds["targetSdkVersion"] = 30
builds["buildToolsVersion"] = "30.0.1"
builds["versionCode"] = 1
builds["versionName"] = "1.0"

/* -------------------------------- 版本号 -------------------------------------- */

val versions = mutableMapOf<String, String>()
//test setting
versions["junit"] = "4.12"
versions["androidxTestExt"] = "1.1.1"
versions["espresso"] = "3.2.0"

//kotlin
versions["kotlin"] = "1.4.32"
//versions["kotlin"] = "1.5.10"
//Android KTX 是一组 Kotlin 扩展程序，属于 Android Jetpack 系列. url: https://developer.android.google.cn/kotlin/ktx?hl=zh_cn
versions["ktx"] = "1.3.2"
//协程. url: https://github.com/Kotlin/kotlinx.coroutines
versions["kotlinCoroutine"] = "1.4.2"
//Kotlin库，它使Android应用程序开发更快更容易. url: https://github.com/Kotlin/anko
versions["anko"] = "0.10.8"
versions["androidxLifecycle"] = "2.2.0-rc03"

//安卓兼容包，未来是 androidx
versions["supportAppcompat"] = "28.0.0"
//官方约束布局.  url: https://dl.google.com/dl/android/maven2/index.html
//versions.constraintLayout = "1.1.4"
versions["constraintLayout"] = "2.0.4"
versions["recyclerviewX"] = "1.1.0"
versions["viewpager2"] = "1.0.0"
//原 design 包下的大部分类移到了这里，含有：appbar, tabLayout 等
versions["material"] = "1.1.0"
//官方多 dex 分包. doc: https://developer.android.com/studio/build/multidex
versions["multidex"] = "2.0.1"
versions["multidexInstrumentation"] = "2.0.0"
versions["androidxCompat"] = "1.1.0"
versions["navigationX"] = "2.2.0-rc04"

//阿里界面路由. url: https://github.com/alibaba/ARouter
//与 kotlin 的兼容性，还是有问题
versions["arouter"] = "1.5.0"
versions["arouterApt"] = "1.2.2"
//工具类集. url: https://github.com/Blankj/AndroidUtilCode
versions["utilcode"] = "1.23.6"

//网络. url: https://github.com/square/okhttp
//对 4.4 以下兼容，要使用 3.12.x 版本。项目中用的3.9
versions["okhttp"] = "4.2.2"
versions["okio"] = "2.4.1"

//封装网络请求与响应。可结合 okhttp、gson、rxjava. url: https://github.com/square/retrofit
versions["retrofit"] = "2.6.2"

//动态切换 retrofit 中的 baseUrl。 url: https://github.com/JessYanCoding/RetrofitUrlManager
versions["retrofitUrlManage"] = "1.4.0"

//google json 库. url: https://github.com/google/gson
versions["gson"] = "2.8.6"

//快速解析和生成json数据. url: https://github.com/alibaba/fastjson
versions["fastjson"] = "1.1.71.android"

//RecyclerView.Adapter 实用封装. url: https://github.com/CymChad/BaseRecyclerViewAdapterHelper
versions["brvah"] = "2.9.46"
//Android智能下拉刷新框架. url: https://github.com/scwang90/SmartRefreshLayout
versions["smartRefreshLayout"] = "1.1.0-alpha-24"
//安卓 fragment 封装. url: https://github.com/YoKeyword/Fragmentation
versions["fragmentation"] = "1.3.6"
versions["fragmentationx"] = "1.0.2"

//流式事件. url: https://github.com/ReactiveX/RxJava
versions["rxjava"] = "2.2.9"
//安卓版. 使用时，最好也要依赖 rxjava. url: https://github.com/ReactiveX/RxAndroid
versions["rxandroid"] = "2.1.1"
//url: https://github.com/ReactiveX/RxKotlin
versions["rxkotlin"] = "2.3.0"
//依赖 rxjava 的动态权限申请. url: https://github.com/tbruyelle/RxPermissions
//可以结合 JakeWharton/RxBinding
versions["rxpermissions"] = "0.10.2"
// 对 view 添加 rxjava 式的 响应事件. url: https://github.com/JakeWharton/RxBinding
versions["rxbinding"] = "3.0.0-alpha2"

//rxlifecycle AndroidX.
versions["rxlifecycle"] = "3.0.0"
//防止 rxjajva 订阅，造成的内存泄露. url: https://github.com/trello/RxLifecycle
//versions.rxlifecycle = "2.2.2"

//权限处理 url: https://github.com/yanzhenjie/AndPermission
// url: https://github.com/permissions-dispatcher
versions["andPermission"] = "2.0.1"

//权限处理 url: https://github.com/permissions-dispatcher/PermissionsDispatcher
versions["permissionsDispatcher"] = "4.7.0"

//依赖注入. url: https://github.com/google/dagger
versions["dagger"] = "2.25.2"

//编译时生成 .java 源文件. url: https://github.com/square/javapoet
versions["javapoet"] = "1.12.1"

//google auto-projects (AutoFactory AutoService AutoValue Common )  url: https://github.com/google/auto/
//一个 对 jsr-330(依赖注入标准)兼容的 源代码生成工厂。 url: https://github.com/google/auto/tree/master/factory
versions["autoFactory"] = "1.0-beta7"
//使用java.util.Servic"]eLoader-style service providers，编译后生成 java 注解处理器源数据的配置
//      url: https://github.com/google/auto/tree/master/service
versions["autoService"] = "1.0-rc6"
// 生成属性值不可变的 java 类.
// url: https://github.com/google/auto/tree/master/value  https://github.com/google/auto/blob/master/value/userguide/index.md
versions["autoValue"] = "1.7.1"
// Auto 项目的 对帮助简化使用 注解处理环境 的 一个通用的工具(类的)集合。 url: https://github.com/google/auto/tree/master/common
versions["autoCommon"] = "0.10"

//A circular ImageView for Android. url: https://github.com/hdodenhof/CircleImageView
versions["hdoCircleImageview"] = "3.0.0"

//java aop(Aspect Oriented Programming). url: https://github.com/eclipse/org.aspectj
//guide url: https://www.eclipse.org/aspectj/doc/released/progguide/index.html
versions["aspectj"] = "1.9.3.RC1"

//一个基于AspectJ并在此基础上扩展出来可应用于Android开发平台的AOP框架，可作用于java源码，class文件及jar包，同时支持kotlin的应用。
// url: https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
versions["AspectJX"] = "2.0.4"

//绑定 android 资源与事件. url: https://github.com/JakeWharton/butterknife
versions["butterknife"] = "10.1.0"

//跨组件、线程通信. url: https://github.com/greenrobot/EventBus
versions["eventbus"] = "3.1.1"

//内存泄露检测. url: https://github.com/square/leakcanary
versions["leakcanary"] = "2.0-alpha-3"

//时间、省市区联动等的选择器. url: https://github.com/Bigkoo/Android-PickerView
versions["androidPickerView"] = "4.1.8"
//androidPickerView作者的 基础滚动选择器
versions["apvWheelView"] = "4.0.9"
//滚轮选择器(可以设置 adapter，绑定布局文件). url: https://github.com/venshine/WheelView
versions["venshineWheelView"] = "1.3.3"
//other 滚动选择器. url: https://github.com/wangjiegulu/WheelView

//一个聚集于平滑显示的对于安卓图片的加载和缓存库. url: https://github.com/bumptech/glide
versions["glide "]= "4.10.0"

//图片加载和缓存库. url: https://github.com/square/picasso
versions["picasso"] = "2.5.2"

//图片选择. url: https://github.com/jeasonlzy/ImagePicker
versions["imagepicker"] = "1.0.6"

//图片选择. url: https://github.com/zhihu/Matisse
//注意调用 ImageEngine，由于新版的 Glide api变化，所以 GlideEngine 需要单独自定义
versions["matisse"] = "0.5.2-beta4"

//能打印出调用时所在线程与方法的日志库. url:https://github.com/orhanobut/logger
versions["orhLogger"] = "2.2.0"

//二维码扫描与生成(集成了 zxing 和 zbar). url: https://github.com/bingoogolapple/BGAQRCode-Android
//   如果需要自实现，那就依赖 zxing 和 zbar. url: https://github.com/zxing/zxing
//   zbar 需要自行下载并编译
versions["bgaqrcode"] = "1.3.6"

//可滚动的 tab layout. url: https://github.com/H07000223/FlycoTabLayout
versions["flycoTabLayout"] = "2.1.2@aar"

//标签(自动换行)列表, 单选多选必选. url: https://github.com/donkingliang/LabelsView
versions["labelsView"] = "1.5.0"

//手动签名. https://github.com/gcacace/android-signaturepad
versions["signaturePad"] = "1.2.1"

//材料设计. url: https://github.com/navasmdc/MaterialDesignLibrary
versions["materialDesign"] = "1.5@aar"

//轮播图. url: https://github.com/youth5201314/banner
versions["youthBanner"] = "1.4.10"

//轮播图. url: https://github.com/saiwu-bigkoo/Android-ConvenientBanner     少用这个，问题有点多
versions["convenientBanner"] = "2.1.4"

//今日头条适配方案. url: https://github.com/JessYanCoding/AndroidAutoSize
//多fragment时，遇到了适配出错的问题；暂不使用
versions["androidAutoSize"] = "1.1.2"

//圆角imageView. url: https://github.com/vinc3m1/RoundedImageView
//不支持glide加载gif图片. 作者推荐 glide-transformations
versions["roundedImageView"] = "2.3.0"

//glide变换. url: https://github.com/wasabeef/glide-transformations
versions["glidTransformations"] = "4.0.1"

//类似ios的GPUImage，配合GLSurfaceView使用. url: https://github.com/cats-oss/android-gpuimage
versions["gpuImage"] = "2.0.3"

//缩放，平移，旋转和动画支持，加载大图等.  由于继承的是view，不能用其它加载库来加载网络图片.
//它需要本地有图片文件. 缩放等动画主要也是作用在图的内容上的，对view没有影响.
//url: https://github.com/davemorrissey/subsampling-scale-image-view
versions["scaleImageView"] = "3.10.0"

//流式布局. url: https://github.com/hongyangAndroid/FlowLayout
versions["flowLayout"] = "1.1.2"

//android 4.4以上沉浸式状态栏和沉浸式导航栏管理. url: https://github.com/gyf-dev/ImmersionBar
versions["immersionbar"] = "3.0.0-beta05"

//腾讯插件化框架. url: https://github.com/Tencent/Shadow
//需要下载工程，自行研究下
versions["shadow"] = ""

//组件化方案. url: https://github.com/PrototypeZ/AppJoint/
versions["appJoint"] = "1.6.1"

//支持h5标准的 html 文件解析. url: https://github.com/jhy/jsoup
versions["jsoup"] = "1.12.1"

//java调用websocket数据. url: https://github.com/TooTallNate/Java-WebSocket
versions["websocket"] = "1.4.0"

//基于 mmap 内存映射的 key-value 组件. 替代 SharedPreferences。 url: https://github.com/Tencent/MMKV/blob/master/readme_cn.md
versions["mmkv"] = "1.1.1"

//支持多个滑动布局(RecyclerView、WebView、ScrollView等)和普通控件(TextView、ImageView、LinearLayou、自定义View等)持续连贯滑动的容器。 url: https://github.com/donkingliang/ConsecutiveScroller
versions["consecutiveScroller"] = "2.5.0"

//阿里对flutter的再封装项目. url: https://github.com/alibaba/flutter-go
//有自己的规范，需要单独学习

versions["room"] = "2.2.6"

/* -------------------------------- 类库引用: "group:name:version" -------------------------------------- */
val refs = mutableMapOf<String, String>()
refs["junit"] = "junit:junit:${versions["junit"]}"
refs["androidxTestExt"] = "androidx.test.ext:junit:${versions["androidxTestExt"]}"
refs["espresso"] = "androidx.test.espresso:espresso-core:${versions["espresso"]}"

refs["kotlin"] = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions["kotlin"]}"
refs["kotlinCoroutineAndroid"] = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions["kotlinCoroutine"]}"
refs["kotlinCoroutineCore"] = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions["kotlinCoroutine"]}"
refs["ktx"] = "androidx.core:core-ktx:${versions["ktx"]}"
refs["anko"] = "org.jetbrains.anko:anko:${versions["anko"]}"
refs["ankoDesign"] = "org.jetbrains.anko:anko-design:${versions["anko"]}"
refs["androidxLifecycleExtensions"] = "androidx.lifecycle:lifecycle-extensions:${versions["androidxLifecycle"]}"
refs["androidxLifecycleComplier"] = "androidx.lifecycle:lifecycle-compiler:${versions["androidxLifecycle"]}"
//LiveDataReactiveStreams 需要结合 RxJava2 一起用。
refs["androidxLifecycleStreams"] = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${versions["androidxLifecycle"]}"
refs["androidxLifecycle"] = "androidx.lifecycle:lifecycle-runtime-ktx:${versions["androidxLifecycle"]}"
refs["androidxViewmodel"] = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions["androidxLifecycle"]}"
refs["androidxLivedata"] = "androidx.lifecycle:lifecycle-livedata-ktx:${versions["androidxLifecycle"]}"

refs["supportAppcompat"] = "com.android.support:appcompat-v7:${versions["supportAppcompat"]}"
refs["design"] = "com.android.support:design:${versions["supportAppcompat"]}"
refs["recyclerview"] = "com.android.support:recyclerview-v7:${versions["supportAppcompat"]}"
refs["recyclerviewX"] = "androidx.recyclerview:recyclerview:${versions["recyclerviewX"]}"
refs["viewpager2"] = "androidx.viewpager2:viewpager2:${versions["viewpager2"]}"
refs["material"] = "com.google.android.material:material:${versions["material"]}"
refs["cardview"] = "com.android.support:cardview-v7:${versions["supportAppcompat"]}"
refs["constraintLayout"] = "androidx.constraintlayout:constraintlayout:${versions["constraintLayout"]}"
refs["multidex"] = "androidx.multidex:multidex:${versions["multidex"]}"
refs["multidexInstrumentation"] = "androidx.multidex:multidex-instrumentation:${versions["multidexInstrumentation"]}"
refs["extensionsX"] = "android.arch.lifecycle:extensions:${versions["extensionsX"]}"
refs["naviCommon"] = "androidx.navigation:navigation-common-ktx:${versions["navigationX"]}"
refs["naviFragment"] = "androidx.navigation:navigation-fragment-ktx:${versions["navigationX"]}"
refs["naviRuntime"] = "androidx.navigation:navigation-runtime-ktx:${versions["navigationX"]}"
refs["naviUI"] = "androidx.navigation:navigation-ui-ktx:${versions["navigationX"]}"

refs["androidxCompat"] = "androidx.appcompat:appcompat:${versions["androidxCompat"]}"

refs["arouter"] = "com.alibaba:arouter-api:${versions["arouter"]}"
refs["arouterApt"] = "com.alibaba:arouter-compiler:${versions["arouterApt"]}"

refs["utilcode"] = "com.blankj:utilcode:${versions["utilcode"]}"

refs["retrofit"] = "com.squareup.retrofit2:retrofit:${versions["retrofit"]}"
refs["retrofitConverterScalars"] = "com.squareup.retrofit2:converter-scalars:${versions["retrofit"]}"
refs["retrofitConverterGson"] = "com.squareup.retrofit2:converter-gson:${versions["retrofit"]}"
refs["retrofitAdapterRxjava"] = "com.squareup.retrofit2:adapter-rxjava2:${versions["retrofit"]}"
refs["retrofitUrlManage"] = "me.jessyan:retrofit-url-manager:${versions["retrofitUrlManage"]}"

refs["gson"] = "com.google.code.gson:gson:${versions["gson"]}"

refs["fastjson"] ="com.alibaba:fastjson:${versions["fastjson"]}"

refs["okhttp"] = "com.squareup.okhttp3:okhttp:${versions["okhttp"]}"
refs["okhttpLogInterceptor"] = "com.squareup.okhttp3:logging-interceptor:${versions["okhttp"]}"
refs["okio"] = "com.squareup.okio:okio:${versions["okio"]}"

refs["brvah"] = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${versions["brvah"]}"

refs["smartRefreshLayout"] = "com.scwang.smartrefresh:SmartRefreshLayout:${versions["smartRefreshLayout"]}"
refs["smartRefreshLayoutHeader"] = "com.scwang.smartrefresh:SmartRefreshHeader:${versions["smartRefreshLayout"]}"

refs["fragmentation"] = "me.yokeyword:fragmentation:${versions["fragmentation"]}"
refs["fragmentationSwipeback"] = "me.yokeyword:fragmentation-swipeback:${versions["fragmentation"]}"
refs["fragmentationx"] = "me.yokeyword:fragmentationx:${versions["fragmentationx"]}"
refs["fragmentationxSwipeback"] = "me.yokeyword:fragmentationx-swipeback:${versions["fragmentationx"]}"

refs["rxjava"] = "io.reactivex.rxjava2:rxjava:${versions["rxjava"]}"
refs["rxandroid"] = "io.reactivex.rxjava2:rxandroid:${versions["rxandroid"]}"
refs["rxkotlin"] = "io.reactivex.rxjava2:rxkotlin:${versions["rxkotlin"]}"

refs["rxpermissions"] = "com.github.tbruyelle:rxpermissions:${versions["rxpermissions"]}"

//refs["rxbinding"] = "com.jakewharton.rxbinding2:rxbinding:${versions["rxbinding"]}"
refs["rxbinding"] = "com.jakewharton.rxbinding3:rxbinding:${versions["rxbinding"]}"

refs["rxlifecycle"] = "com.trello.rxlifecycle3:rxlifecycle:${versions["rxlifecycle"]}"
// If you want to bind to Android-specific lifecycles
refs["rxlifecycleAndroid"] = "com.trello.rxlifecycle3:rxlifecycle-android:${versions["rxlifecycle"]}"
//If you want pre-written Activities and Fragments you can subclass as providers
refs["rxlifecycleComponents"] = "com.trello.rxlifecycle3:rxlifecycle-components:${versions["rxlifecycle"]}"
refs["rxlifecycleKotlin"] = "com.trello.rxlifecycle3:rxlifecycle-kotlin:${versions["rxlifecycle"]}"

refs["andPermission"] = "com.yanzhenjie.permission:support:${versions["andPermission"]}"

refs["permissionsDispatcher"] = "org.permissionsdispatcher:permissionsdispatcher:${versions["permissionsDispatcher"]}"
refs["permissionsDispatcherCompiler"] = "org.permissionsdispatcher:permissionsdispatcher-processor:${versions["permissionsDispatcher"]}"

refs["dagger"] = "com.google.dagger:dagger:${versions["dagger"]}"
refs["daggerCompiler"] = "com.google.dagger:dagger-compiler:${versions["dagger"]}"

refs["javapoet"] = "com.squareup:javapoet:${versions["javapoet"]}"

refs["autoService"] = "com.google.auto.service:auto-service:${versions["autoService"]}"
refs["autoFactory"] = "com.google.auto.factory:auto-factory:${versions["autoFactory"]}"
refs["autoValue"] = "com.google.auto.value:auto-value-annotations:${versions["autoValue"]}"
refs["autoValueCompiler"] = "com.google.auto.value:auto-value:${versions["autoValue"]}"
refs["autoCommon"] = "com.google.auto:auto-common:${versions["autoCommon"]}"

refs["hdoCircleImageview"] = "de.hdodenhof:circleimageview:${versions["hdoCircleImageview"]}"

refs["aspectjRt"] = "org.aspectj:aspectjrt:${versions["aspectj"]}"
refs["aspectjTools"] = "org.aspectj:aspectjtools:${versions["aspectj"]}"
refs["aspectjWeaver"] = "org.aspectj:aspectjweaver:${versions["aspectj"]}"

refs["butterknife"] = "com.jakewharton:butterknife:${versions["butterknife"]}"
refs["butterknifeCompiler"] = "com.jakewharton:butterknife-compiler:${versions["butterknife"]}"

refs["eventbus"] = "org.greenrobot:eventbus:${versions["eventbus"]}"

refs["leakcanary"]= "com.squareup.leakcanary:leakcanary-android:${versions["leakcanary"]}"

refs["androidPickerView"] = "com.contrarywind:Android-PickerView:${versions["androidPickerView"]}"
refs["apvWheelView"] = "com.contrarywind:wheelview:${versions["apvWheelView"]}"
refs["venshineWheelView"] = "com.wx.wheelview:wheelview:${versions["venshineWheelView"]}"

refs["glide"] = "com.github.bumptech.glide:glide:${versions["glide"]}"
refs["glideCompiler"] = "com.github.bumptech.glide:compiler:${versions["glide"]}"
refs["glideOkhttp"] = "com.github.bumptech.glide:okhttp3-integration:${versions["glide"]}"

refs["picasso"] = "com.squareup.picasso:picasso:${versions["picasso"]}"

refs["imagepicker"] = "com.cysion:ImagePicker:${versions["imagepicker"]}"

refs["orhLogger"] = "com.orhanobut:logger:${versions["orhLogger"]}"

refs["bgaqrcodeZxing"] = "cn.bingoogolapple:bga-qrcode-zxing:${versions["bgaqrcode"]}"
refs["bgaqrcodeZbar"] = "cn.bingoogolapple:bga-qrcode-zbar:${versions["bgaqrcode"]}"

refs["flycoTabLayout"] = "com.flyco.tablayout:FlycoTabLayout_Lib:${versions["flycoTabLayout"]}"

refs["labelsView"] = "com.github.donkingliang:LabelsView:${versions["labelsView"]}"

refs["signaturePad"] = "com.github.gcacace:signature-pad:${versions["signaturePad"]}"

refs["materialDesign"] = "com.github.navasmdc:MaterialDesign:${versions["materialDesign"]}"

refs["youthBanner"] = "com.youth.banner:banner:${versions["youthBanner"]}"

refs["convenientBanner"] = "com.bigkoo:ConvenientBanner:${versions["convenientBanner"]}"

refs["matisse"] = "com.zhihu.android:matisse:${versions["matisse"]}"

refs["androidAutoSize"] = "me.jessyan:autosize:${versions["androidAutoSize"]}"

refs["roundedImageView"] = "com.makeramen:roundedimageview:${versions["roundedImageView"]}"

refs["glidTransformations"] = "jp.wasabeef:glide-transformations:${versions["glidTransformations"]}"

refs["gpuImage"] = "jp.co.cyberagent.android:gpuimage:${versions["gpuImage"]}"

refs["scaleImageView"] = "com.davemorrissey.labs:subsampling-scale-image-view:${versions["scaleImageView"]}"

refs["flowLayout"] = "com.hyman:flowlayout-lib:${versions["flowLayout"]}"

refs["immersionbar"] = "com.gyf.immersionbar:immersionbar:${versions["immersionbar"]}"
refs["immersionbarComponents"] = "com.gyf.immersionbar:immersionbar-components:${versions["immersionbar"]}"

refs["appJoint"] = "io.github.prototypez:app-joint-core:${versions["appJoint"]}"

refs["jsoup"] = "org.jsoup:jsoup:${versions["jsoup"]}"

refs["websocket"] = "org.java-websocket:Java-WebSocket:${versions["websocket"]}"

refs["mmkv"] = "com.tencent:mmkv-static:${versions["mmkv"]}"

refs["consecutiveScroller"] = "com.github.donkingliang:ConsecutiveScroller:${versions["consecutiveScroller"]}"

refs["roomRuntime"] = "androidx.room:room-runtime:${versions["room"]}"
refs["roomCompiler"] = "androidx.room:room-compiler:${versions["room"]}"
refs["roomKtx"] = "androidx.room:room-ktx:${versions["room"]}"

//refs["=":${versions[""
/* ---------------------------------------------------------------------- */


