package com.stone.stoneviewskt.ui.audio

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.*
import android.os.Bundle
import android.view.MotionEvent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentAudioRecordBinding
import com.stone.stoneviewskt.util.loge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.math.max

/**
 * desc:    AudioRecord 录制 pcm 格式，文件较大
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/28 11:26
 */
@RuntimePermissions
class AudioRecordFragment : BaseBindFragment<FragmentAudioRecordBinding>(R.layout.fragment_audio_record) {

    init {
        System.loadLibrary("native-lib")
    }

    private var mAudioRecord: AudioRecord? = null
    private var mAudioTrack: AudioTrack? = null
    private var mAudioFile: File? = null
    private var mStartTime = 0L
    private var mStopTime = 0L
    private val mBufferSize = 2048
    private val mBuffer = ByteArray(mBufferSize)
    private var mIsRecording = false
    private var mIsPlaying = false

    private lateinit var mAudioPitchProcessor: AudioPitchProcessor
    private var mPitchRatio: Float = 1f

    companion object {
        private const val mSampleRateInHz = 44100
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mAudioPitchProcessor = AudioPitchProcessor(mBufferSize)

        mBind.fragmentAudioRecordSay.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecordWithPermissionCheck()
                }
                MotionEvent.ACTION_UP -> {
                    loge("ACTION_UP")
                    stopRecord()
                }
            }
            true
        }

        mBind.fragmentAudioRecordPlay.setOnClickListener {
            doPlay()
        }

        mBind.fragmentAudioPitch1.setOnClickListener {
            mPitchRatio = 1f
        }

        mBind.fragmentAudioPitch2.setOnClickListener {
            mPitchRatio = 1.5f
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun startRecord() {
        mBind.fragmentAudioRecordSay.text = "正在录音"
        mBind.fragmentAudioRecordMic.imageResource = R.drawable.ic_baseline_mic_24

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
        mBind.fragmentAudioRecordSay.text = "开始录音"
        mBind.fragmentAudioRecordMic.imageResource = R.drawable.ic_baseline_mic_off_24

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
        mAudioRecord?.release()
        mAudioFile?.delete()
    }

    private fun doStart(): Boolean {
        try {
            mAudioFile = File("${requireActivity().externalCacheDir}/stone.pcm")
            mAudioFile?.createNewFile()

            val os = FileOutputStream(mAudioFile)

            val audioSource = MediaRecorder.AudioSource.MIC
            val channelConfig = AudioFormat.CHANNEL_IN_MONO
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val bufferSizeInBytes = AudioRecord.getMinBufferSize(mSampleRateInHz, channelConfig, audioFormat)

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return false
            }
            mAudioRecord = AudioRecord(audioSource, mSampleRateInHz, channelConfig, audioFormat, max(bufferSizeInBytes, mBufferSize))
            mAudioRecord?.startRecording()
            mStartTime = System.currentTimeMillis()
            mIsRecording = true
            while (mIsRecording) {
                val read = mAudioRecord?.read(mBuffer, 0, mBufferSize) ?: 0
                if (read > 0) {
                    os.write(mBuffer, 0, read)
                } else {
                    return false
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
            mIsRecording = false
            mAudioRecord?.stop()
            mAudioRecord?.release()
            mAudioRecord = null
            withContext(Dispatchers.Main) {
                mStopTime = System.currentTimeMillis()
                if (mStopTime - mStartTime >= 3000) {
                    mBind.fragmentAudioRecordTv.text = "录音时长: ${(mStopTime - mStartTime) / 1000}s"
                }
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun onDestroy() {
        releaseRecord()
        super.onDestroy()
    }

    private fun doPlay(): Boolean {
        try {
            mIsPlaying = true
            val streamType = AudioManager.STREAM_MUSIC
//            val sampleRateInHz = (mSampleRateInHz * 1.5).toInt() //播放时采样率，若比源大，会有加速效果，反之减速。 这里 *1.5 以1.5倍速播放；但音调变化太大
            val sampleRateInHz = mSampleRateInHz
            val channelConfig = AudioFormat.CHANNEL_OUT_MONO //单声道； CHANNEL_OUT_STEREO 双声道
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val mode = AudioTrack.MODE_STREAM
            val bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)

            mAudioTrack = AudioTrack(
                streamType, sampleRateInHz, channelConfig,
                audioFormat, max(bufferSizeInBytes, mBufferSize), mode
            )
            mAudioTrack?.play() //调一次播放，等待写入
            val fis = FileInputStream("${requireActivity().externalCacheDir}/stone.pcm")
            var read = fis.read(mBuffer)
            while (read > 0) {
                val buffer = if (mPitchRatio == 1.0f) mBuffer else mAudioPitchProcessor.process(mPitchRatio, mSampleRateInHz, mBuffer)
                when (mAudioTrack?.write(buffer, 0, read)) {
                    AudioTrack.ERROR_BAD_VALUE, AudioTrack.ERROR_DEAD_OBJECT, AudioTrack.ERROR_INVALID_OPERATION -> {
                        //播放错误
//                        stopPlay()
                        return false
                    }
                }
                read = fis.read(mBuffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun stopPlay() {
        mIsPlaying = false
        mAudioTrack?.stop()
        mAudioTrack?.release()
        mAudioTrack = null
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