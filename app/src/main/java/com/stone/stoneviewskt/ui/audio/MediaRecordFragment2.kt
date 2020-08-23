package com.stone.stoneviewskt.ui.audio

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.loge
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_media_record.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/28 11:26
 */
@RuntimePermissions
class MediaRecordFragment2 : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        MediaRecordManager.setOnRecordingBlock { startTime ->
            lifecycleScope.launchWhenResumed {
                async {
                    while (MediaRecordManager.mIsRecording) {
                        fragment_media_record_wave.refreshElement(MediaRecordManager.getMaxAmplitude())
                        delay(100)
                    }
                }
                while (MediaRecordManager.mIsRecording) {
                    fragment_media_record_time.text = "${(System.currentTimeMillis() - startTime) / 1000}s"
                    fragment_media_record_wave.setText("${(3 * 60 * 1000 - (System.currentTimeMillis() - startTime)) / 1000}s")
                    delay(1000)
                    logi("音量:${MediaRecordManager.getMaxAmplitude()}" )
                }
            }
        }

        MediaRecordManager.setOnFailureBlock {
            toast("操作异常")
        }

        MediaRecordManager.setOnCompleteBlock { startTime, stopTime ->
            val span = (stopTime - startTime) / 1000
            fragment_media_record_wave.setText("录音倒计时：${ 3 * 60 - span}s")
            if (span >= 3) {
                fragment_media_record_tv.text = "录音时长: ${span}s"
                fragment_media_record_time.text = "${span}s"
            }
            if (span < 5) {
                //todo 小于5s
            }
        }

        MediaRecordManager.setOnPlayBlock { duration ->
            lifecycleScope.launchWhenResumed {
                var seconds = duration / 1000
                while (MediaRecordManager.mIsPlaying) {
                    fragment_media_record_play_time.text = "播放倒计时: ${seconds}s"
                    if (seconds == 0) break
                    delay(1000)
                    seconds--
                }
            }
        }

        fragment_media_record_say.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecordWithPermissionCheck()
                }
                MotionEvent.ACTION_UP -> {
                    loge("ACTION_UP")
                    stopRecord()
                    fragment_media_record_wave.reset()
                }
            }
            true
        }

        fragment_media_record_play.setOnClickListener {
            if (MediaRecordManager.mIsPlaying) {
               MediaRecordManager.stopPlay()
            } else {
                MediaRecordManager.play(requireContext())
            }
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun startRecord() {
        fragment_media_record_say.text = "正在录音"
        fragment_media_record_mic.imageResource = R.drawable.ic_baseline_mic_24

        lifecycleScope.launchWhenResumed {
            MediaRecordManager.releaseRecord()
            MediaRecordManager.startRecord()
        }
    }

    private fun stopRecord() {
        fragment_media_record_say.text = "开始录音"
        fragment_media_record_mic.imageResource = R.drawable.ic_baseline_mic_off_24

        lifecycleScope.launchWhenResumed {
            MediaRecordManager.stopRecord()
        }
    }

    override fun onDestroy() {
        MediaRecordManager.releaseRecord()
        MediaRecordManager.stopPlay()
        super.onDestroy()
    }

    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onAudioStoragePermissionDenied() {

    }

    @OnNeverAskAgain(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onAudioStoragePermissionAskAgain() {

    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_media_record
    }
}