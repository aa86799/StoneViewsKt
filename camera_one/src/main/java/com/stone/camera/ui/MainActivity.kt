package com.stone.camera.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.stone.camera.R
import com.stone.camera.common.CameraInterface
import com.stone.camera.common.CameraSurfaceView
import com.stone.camera.util.DisplayUtil
import kotlinx.android.synthetic.main.acitivty_main.*

/**
 * desc     :
 * author   : stone
 * email    : aa86799@163.com
 * time     : 23/03/2018 17 42
 */
class MainActivity : AppCompatActivity() {
    private var mSurfaceView: CameraSurfaceView? = null
    private var mPermissions = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivty_main)
        val isAllGranted = checkPermissionAllGranted(mPermissions)
        if (isAllGranted) {
            hasPermission()
        } else {
            ActivityCompat.requestPermissions(this, mPermissions, 0x1)
        }

        btn_shutter.setOnClickListener { v ->
            when (v.id) {
                R.id.btn_shutter -> CameraInterface.instance?.doTakePicture()
                else -> {

                }
            }
        }
    }

    private fun hasPermission() {
        val layout: FrameLayout = findViewById<FrameLayout>(R.id.camera_fl)
        layout.addView(CameraSurfaceView(this@MainActivity).also { mSurfaceView = it })
        initUI()
        initViewParams()
    }


    private fun initUI() {

    }

    private fun initViewParams() {
        mSurfaceView?.let {
            val params: ViewGroup.LayoutParams = it.layoutParams
            val p: Point = DisplayUtil.getScreenMetrics(this)
            params.width = p.x
            params.height = p.y
            mSurfaceView?.layoutParams = params
        }

        //手动设置拍照ImageButton的大小为120dip×120dip
        val p2: ViewGroup.LayoutParams = btn_shutter.layoutParams
        val v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120f, resources.displayMetrics);
        p2.width = v.toInt()
        p2.height = v.toInt()
//        p2.width = DisplayUtil.dip2px(this, 120f)
//        p2.height = DisplayUtil.dip2px(this, 120f)
        btn_shutter.layoutParams = p2
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            var isAllGranted = true
            // 判断是否所有的权限都已经授予了
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
                Log.e("err", "权限都授权了")
                hasPermission()
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                //MyDialog("提示", "某些权限未开启,请手动开启", 1) ;
            }
        }
    }

}