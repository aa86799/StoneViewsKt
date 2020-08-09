package com.stone.stoneviewskt.ui.audio

import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.loge
import kotlinx.android.synthetic.main.fragment_media_record.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import java.io.File

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/28 11:26
 */
@RuntimePermissions
class MediaRecordFragment : BaseFragment() {

    private var mMediaRecorder: MediaRecorder? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mAudioFile: File? = null
    private var mStartTime = 0L
    private var mStopTime = 0L
    private var mIsPlaying = false
    private var mIsStartRecording = false

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_media_record_say.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecordWithPermissionCheck()
                }
                MotionEvent.ACTION_UP -> {
                    loge("ACTION_UP" )
                    stopRecord()
                }
            }
            true
        }

        fragment_media_record_play.setOnClickListener {
            doPlay()
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun startRecord() {
        fragment_media_record_say.text = "正在录音"
        fragment_media_record_mic.imageResource = R.drawable.ic_baseline_mic_24

        lifecycleScope.launchWhenResumed {
            releaseRecord()
            val result = withContext(Dispatchers.IO) {
                doStart()
            }
            if (!result) {
                failure()
            }
        }
    }

    private fun stopRecord() {
        fragment_media_record_say.text = "开始录音"
        fragment_media_record_mic.imageResource = R.drawable.ic_baseline_mic_off_24

        lifecycleScope.launchWhenResumed {
            val result = withContext(Dispatchers.Main) {
                doStop()
            }
            if (!result) {
                failure()
            }
        }
    }

    private fun releaseRecord() {
        mMediaRecorder?.reset()
        mMediaRecorder?.release()
        mMediaRecorder = null
    }

    private fun doStart(): Boolean {
        try {
            mAudioFile = File("${mActivity.externalCacheDir}/stone.m4a")
            mAudioFile?.createNewFile()

            mMediaRecorder = MediaRecorder()
            mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mMediaRecorder?.setOutputFile(mAudioFile?.absolutePath)
            mMediaRecorder?.setAudioEncodingBitRate(96000)
            mMediaRecorder?.setAudioSamplingRate(16000) //441000
            mMediaRecorder?.prepare()
            mMediaRecorder?.start()
            mStartTime = System.currentTimeMillis()
            mIsStartRecording = true
            lifecycleScope.launchWhenResumed {
                while (mIsStartRecording) {
                    fragment_media_record_time.text = "${(System.currentTimeMillis() - mStartTime) / 1000}s"
                    delay(1000)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun failure() {
        if (mAudioFile?.exists() == true) {
            mAudioFile?.delete()
        }
        mAudioFile = null
        toast("操作异常")

    }

    private suspend fun doStop(): Boolean {
        loge("stop::::")
        try {
            mIsStartRecording = false
            mMediaRecorder?.stop()
            withContext(Dispatchers.Main) {
                mStopTime = System.currentTimeMillis()
                if (mStopTime - mStartTime >= 3000) {
                    fragment_media_record_tv.text = "录音时长: ${(mStopTime - mStartTime) / 1000}s"
                    fragment_media_record_time.text = "${(mStopTime - mStartTime) / 1000}s"
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun doPlay(): Boolean {
        try {
            mIsPlaying = true
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setDataSource("${mActivity.externalCacheDir}/stone.m4a")
            mMediaPlayer?.setOnCompletionListener {
                stopPlay()
            }
            mMediaPlayer?.setOnErrorListener { mp, what, extra ->
                stopPlay()
                true
            }
            mMediaPlayer?.setVolume(1f, 1f)
            mMediaPlayer?.isLooping = false
            mMediaPlayer?.prepare()
            mMediaPlayer?.start()

            lifecycleScope.launchWhenResumed {
                var seconds = mMediaPlayer!!.duration / 1000
                while (mIsPlaying) {
                    fragment_media_record_play_time.text = "播放倒计时: ${seconds}s"
                    if (seconds == 0) break
                    delay(1000)
                    seconds--
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun stopPlay() {
        mIsPlaying = false
        mMediaPlayer?.setOnCompletionListener {  }
        mMediaPlayer?.setOnErrorListener { mp, what, extra -> true }
        mMediaPlayer?.stop()
        mMediaPlayer?.reset()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    override fun onDestroy() {
        releaseRecord()
        stopPlay()
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