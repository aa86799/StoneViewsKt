package com.stone.stoneviewskt.ui.audio

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMediaRecordBinding
import com.stone.stoneviewskt.util.loge
import com.stone.stoneviewskt.util.logi
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
class MediaRecordFragment2 : BaseBindFragment<FragmentMediaRecordBinding>(R.layout.fragment_media_record) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        MediaRecordManager.setOnRecordingBlock { startTime ->
            lifecycleScope.launchWhenResumed {
                async {
                    while (MediaRecordManager.mIsRecording) {
                        mBind.fragmentMediaRecordWave.refreshElement(MediaRecordManager.getMaxAmplitude())
                        delay(100)
                    }
                }
                while (MediaRecordManager.mIsRecording) {
                    mBind.fragmentMediaRecordTime.text = "${(System.currentTimeMillis() - startTime) / 1000}s"
                    mBind.fragmentMediaRecordWave.setText("${(3 * 60 * 1000 - (System.currentTimeMillis() - startTime)) / 1000}s")
                    delay(1000)
                    logi("音量:${MediaRecordManager.getMaxAmplitude()}")
                }
            }
        }

        MediaRecordManager.setOnFailureBlock {
            toast("操作异常")
        }

        MediaRecordManager.setOnCompleteBlock { startTime, stopTime ->
            val span = (stopTime - startTime) / 1000
            mBind.fragmentMediaRecordWave.setText("录音倒计时：${3 * 60 - span}s")
            if (span >= 3) {
                mBind.fragmentMediaRecordTv.text = "录音时长: ${span}s"
                mBind.fragmentMediaRecordTime.text = "${span}s"
            }
            if (span < 5) {
                //todo 小于5s
            }
        }

        MediaRecordManager.setOnPlayBlock { duration ->
            lifecycleScope.launchWhenResumed {
                var seconds = duration / 1000
                while (MediaRecordManager.mIsPlaying) {
                    mBind.fragmentMediaRecordPlayTime.text = "播放倒计时: ${seconds}s"
                    if (seconds == 0) break
                    delay(1000)
                    seconds--
                }
            }
        }

        mBind.fragmentMediaRecordSay.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecordWithPermissionCheck()
                }
                MotionEvent.ACTION_UP -> {
                    loge("ACTION_UP")
                    stopRecord()
                    mBind.fragmentMediaRecordWave.reset()
                }
            }
            true
        }

        mBind.fragmentMediaRecordPlay.setOnClickListener {
            if (MediaRecordManager.mIsPlaying) {
                MediaRecordManager.stopPlay()
            } else {
                MediaRecordManager.play(requireContext())
            }
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun startRecord() {
        mBind.fragmentMediaRecordSay.text = "正在录音"
        mBind.fragmentMediaRecordMic.imageResource = R.drawable.ic_baseline_mic_24

        lifecycleScope.launchWhenResumed {
            MediaRecordManager.releaseRecord()
            MediaRecordManager.startRecord()
        }
    }

    private fun stopRecord() {
        mBind.fragmentMediaRecordSay.text = "开始录音"
        mBind.fragmentMediaRecordMic.imageResource = R.drawable.ic_baseline_mic_off_24

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

}