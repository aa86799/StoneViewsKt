package com.stone.stoneviewskt.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
import android.view.WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.ui.simulate.SimulateScriptsFragment
import com.stone.stoneviewskt.util.logi
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.newTask
import org.jetbrains.anko.runOnUiThread

class SimulateService : AccessibilityService() {

    // private val EVENT_TYPE_CLICK = 1
    // private val EVENT_TYPE_SWIPE = 2
    // private val EVENT_TYPE_SWIPE_UP = 3
    // private val EVENT_TYPE_SWIPE_DOWN = 4
    // private val EVENT_TYPE_BACK = 5
    // private val EVENT_TYPE_HOME = 6

    // private val handler = object : Handler(Looper.getMainLooper()) {
    //     override fun handleMessage(msg: Message) {
    //         when (msg.what) {
    //             EVENT_TYPE_CLICK -> {
    //                 (msg.data?.get("point") as? Point)?.let {
    //                     dispatchGestureClick(it.x, it.y)
    //                 }
    //             }
    //             EVENT_TYPE_SWIPE_UP -> {
    //                 dispatchGestureSwipeUp()
    //             }
    //             EVENT_TYPE_SWIPE_DOWN -> {
    //                 dispatchGestureSwipeDown()
    //             }
    //         }
    //     }
    // }

    var floatView: View? = null

    override fun onServiceConnected() {
        super.onServiceConnected()

        initView()
        MMKV.defaultMMKV().encode("switch", false)
        floatView?.findViewById<Button>(R.id.btn_run)?.setOnClickListener {
            it as Button
            if (it.text == "start") {
                it.text = "stop"
                MMKV.defaultMMKV().encode("switch", true)
            } else {
                it.text = "start"
                MMKV.defaultMMKV().encode("switch", false)
            }
        }

        floatView?.find<Button>(R.id.btn_scripts)?.setOnClickListener {
            // startActivity<BaseActivity>(BaseActivity.KEY_FRAGMENT to cls,  *params)
            startActivity(Intent(applicationContext, BaseActivity::class.java).putExtra(BaseActivity.KEY_FRAGMENT, SimulateScriptsFragment::class.java).newTask())
        }
    }

    private fun initView() {
        val wm = this.getSystemService(AccessibilityService.WINDOW_SERVICE) as? WindowManager
        val lp = WindowManager.LayoutParams().apply {
            type = TYPE_ACCESSIBILITY_OVERLAY // 因为此权限才能展示处理
            layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            format = PixelFormat.TRANSLUCENT
            flags = flags or
                FLAG_LAYOUT_NO_LIMITS or
                // FLAG_NOT_TOUCHABLE or  // 透传触摸事件
                FLAG_NOT_FOCUSABLE or  // 透传输入事件
                FLAG_LAYOUT_IN_SCREEN
            width = WRAP_CONTENT
            height = WRAP_CONTENT
            // horizontalMargin = 0f
            gravity = Gravity.END or Gravity.BOTTOM
        }
        // 通过 LayoutInflater 创建 View
        floatView = LayoutInflater.from(this).inflate(R.layout.layout_simulator, null)
        wm?.addView(floatView, lp)
    }

    private var job: Job? = null

    var switch = false

    /**
     * 监听窗口变化的回调
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val eventType = event?.eventType ?: return
        logi("eventtype: ${eventType.toString(16)}")

        /*
         * 增加一个开关 switch 用于启动 脚本的执行
         *
         * 脚本编辑界面：
         * 列表展示已有脚本列表， + ： 添加新的脚本
         * 点击一个脚本进入查看， 增加编辑按钮
         *
         * 单个脚本的编辑模式，  +: 增加一个 action；   每个 action 后面跟一个  删除按钮；
         * 增加一个 action： 弹窗选择 动作类型， 普通点击动作要输入 x/y 坐标
         *
         * 脚本循环次数 编辑， 下次循环的时间间隔？  需不需要弄个 浮窗 展示一些信息(eg. 下次动作是什么，当前动作是什么，下次动作还要多少时间？)
         *
         *
         *
         */
        switch = MMKV.defaultMMKV().decodeBool("switch")
        if (switch && job == null) {
            job?.cancel()
            job = GlobalScope.launch { // 读取一个脚本文件， 比如有一个 编辑脚本文件的界面， 一个脚本内含有多个 SimulateAction 对象
                val list = mutableListOf<SimulateAction>()
                list.add(SimulateAction(EventType.HOME, 1000))
                list.add(SimulateAction(EventType.CLICK, 2000, 522, 1430))
                list.add(SimulateAction(EventType.SWIPE_DOWN, 5000))
                list.forEachIndexed { index, it ->
                    when (it.type) {
                        EventType.CLICK -> { // 普通点击
                            delay(it.delayTime)
                            dispatchGestureClick(it.tapX, it.tapY)
                        }
                        EventType.SWIPE_UP -> { // 上滑
                            delay(it.delayTime)
                            dispatchGestureSwipeUp()
                        }
                        EventType.SWIPE_DOWN -> { // 下滑
                            delay(it.delayTime)
                            dispatchGestureSwipeDown()
                        }
                        EventType.SWIPE_LEFT -> { // 左滑
                            delay(it.delayTime)
                            dispatchGestureSwipeLeft()
                        }
                        EventType.SWIPE_RIGHT -> { // 右滑
                            delay(it.delayTime)
                            dispatchGestureSwipeRight()
                        }
                        EventType.BACK -> { // click back
                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                        }
                        EventType.HOME -> { // click home
                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
                        }

                    }
                    if (index == list.lastIndex) {
                        job = null
                        switch = false
                        MMKV.defaultMMKV().encode("switch", false)
                        runOnUiThread {
                            floatView?.findViewById<Button>(R.id.btn_run)?.text = "start"
                        }
                    }
                }
            }
        } else if (!switch && job != null) {
            job?.cancel()
            job = null
        }

        // typeWindowContentChanged, eventType = 800; 该事件在窗口内容变化时，会多次快速执行 到onAccessibilityEvent()
        // 所以，要使得只执行一次
        // auto(event, eventType.toString(16))

        // job?.cancel()
        // job = GlobalScope.launch {
        //     delay(1000)
        //     logi("auto: 执行查找 qq")
        //
        //     // Cmd.run("input tap 138 192") // adb shell
        //     // delayClickMsg(scope, 522, 1430, 1000)
        //
        //     auto(this, event, eventType.toString(16))
        //     job = null
        // }

        // rootInActiveWindow.findAccessibilityNodeInfosByText()
        // rootInActiveWindow.findFocus()
        // rootInActiveWindow.findAccessibilityNodeInfosByViewId()

        // val qq = rootInActiveWindow.findAccessibilityNodeInfosByText("QQ")
        // qq.forEach { node ->
        //     (0 until node.childCount).forEach {
        //         val child = node.getChild(it) // if (child.text ==
        //
        //     }
        // }

        // handler.sendMessageDelayed(Message.obtain(handler).also {
        //     it.data = Bundle().apply {
        //         putParcelable("point", Point(550, 1420))
        //     }
        // }, 11500)

        /* when (eventType) { // 通知栏变化
             AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> { // 模拟点击通知栏，因为通知栏弹出有动画，所以需要延迟
                 handler.sendMessageDelayed(Message.obtain(handler).also {
                     it.data = Bundle().apply {
                         putParcelable("point", Point(400, 100))
                     }
                 }, 1500)
             }
             AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                 handler.sendMessageDelayed(Message.obtain(handler).also {
                     it.data = Bundle().apply {
                         putParcelable("point", Point(400, 100))
                     }
                 }, 500)
             }

             // event.source.isFocused
             // GestureDescription.Builder().addStroke()

         }*/
    }

    /**
     * 中断服务的回调
     */
    override fun onInterrupt() {
    }

    private fun dispatchGestureClick(x: Int, y: Int) {
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
        dispatchGesture(
            GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, 0, 100)).build(), null, null
        )
    }

    private fun dispatchGestureSwipe(x1: Int, y1: Int, x2: Int, y2: Int) {
        val path = Path()
        path.moveTo(x1.toFloat(), y1.toFloat())
        path.lineTo(x2.toFloat(), y2.toFloat())
        dispatchGesture(
            GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, 0, 300)).build(), null, null
        )
    }

    // 上滑
    private fun dispatchGestureSwipeUp() {
        dispatchGestureSwipe(dw() / 2, dh() / 3 * 2, dw() / 2, dh() / 3)
    }

    // 下滑
    private fun dispatchGestureSwipeDown() {
        dispatchGestureSwipe(dw() / 2, dh() / 3, dw() / 2, dh() / 3 * 2)
    }

    // 左滑
    private fun dispatchGestureSwipeLeft() {
        dispatchGestureSwipe(dw() / 3 * 2, dh() / 2, dw() / 3, dh() / 2)
    }

    // 右滑
    private fun dispatchGestureSwipeRight() {
        dispatchGestureSwipe(dw() / 3, dh() / 2, dw() / 3 * 2, dh() / 2)
    }

    private fun dw() = MMKV.defaultMMKV().decodeInt("dw")
    private fun dh() = MMKV.defaultMMKV().decodeInt("dh")

    private fun auto(scope: CoroutineScope, event: AccessibilityEvent?, eventTypeHex: String) {
        val qqNode = findAccessibilityNodeInListByText("QQ") // logi("find QQ label exists => $eventTypeHex  ${qqNode != null}, $qqNode")
        qqNode?.run { // open qq
            delayClickMsg(scope, 522, 1430, 1000)
            logi("show QQ")
            qqNode.recycle()
        }

        // swipe to show mini program
        // val contactNode = findAccessibilityNodeInListByText("联系人")
        // val miniWorldNode = findAccessibilityNodeInListByText("小世界")
        // val qqMiniNode = findAccessibilityNodeInListByText("QQ小程序")
        // if (contactNode != null && miniWorldNode != null && qqMiniNode == null) {
        //     delaySwipeDownMsg(scope, 6000)
        //     logi("下拉显示小程序列表")
        // }
        // contactNode?.recycle()
        // miniWorldNode?.recycle()
        //
        // val calcNode = findAccessibilityNodeInListByText("小宝计算...")
        // if (qqMiniNode != null && calcNode != null) {
        //     logi("打开小程序：小宝计算")
        //     delayClickMsg(scope, 180, 880, 3000)
        // }
        // qqMiniNode?.recycle()
        // calcNode?.recycle()

        logi("${findAccessibilityNodeInListByText("QQ小程序")}")
    }

    /**
     * 遍历，获得指定 text 的 AccessibilityNodeInfo
     */
    private fun findAccessibilityNodeInListByText(text: String): AccessibilityNodeInfo? {/*
        但类似微信将关键数据改成了自定义view,不对外开放text内容, 也就拿不到 node.text；
        若没有开放 text，也没有设置 contentDescription ，那后面的比对方法也就失效了
         */ // val list = rootInActiveWindow.findAccessibilityNodeInfosByText(text) // 模糊查找，返回含有 text 的 node list
        // list ?: return null
        // for (info in list) {
        //     if (info.text != null && info.text.toString() == text) {
        //         return info
        //     }
        // }
        // return null

        return findFirstRecursive(rootInActiveWindow, text)
    }

    /**
     * 递归搜索
     */
    private fun findFirstRecursive(parent: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
        var node: AccessibilityNodeInfo? = null
        parent.run {
            (0 until parent.childCount).forEach {
                val child = parent.getChild(it)
                if (child != null) {
                    logi("contentdesc: ${child.contentDescription}, text:${child.text}")
                    if (child.text != null && (child.text.toString() == text || child.contentDescription == text)) {
                        node = child
                        return@run
                    } else {
                        node = findFirstRecursive(child, text)
                        if (node != null) {
                            return node
                        }
                    }
                }
            }
        }
        return node
    }

    private fun delayClickMsg(scope: CoroutineScope, x: Int, y: Int, delayTime: Long) {
        scope.launch {
            delay(delayTime)
            dispatchGestureClick(x, y)
        }
    }

    private fun delaySwipeUpMsg(scope: CoroutineScope, delayTime: Long) {
        scope.launch {
            delay(delayTime)
            dispatchGestureSwipeUp()
        }
    }

    private fun delaySwipeDownMsg(scope: CoroutineScope, delayTime: Long) {
        scope.launch {
            delay(delayTime)
            dispatchGestureSwipeDown()
        }
    }
}

enum class EventType {
    CLICK, SWIPE_UP, SWIPE_DOWN, SWIPE_LEFT, SWIPE_RIGHT, BACK, HOME
}

data class SimulateAction(val type: EventType, val delayTime: Long, val tapX: Int = 0, val tapY: Int = 0)
