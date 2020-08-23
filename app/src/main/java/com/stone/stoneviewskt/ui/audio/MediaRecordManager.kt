package com.stone.stoneviewskt.ui.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import com.stone.stoneviewskt.StoneApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * desc:    单例，持有了 application - context；
 *          存储格式改成mp3，利于上传server后， web 端直接播放
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/30 21:36
 */
object MediaRecordManager {

    private var mMediaRecorder: MediaRecorder? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mAudioFile: File? = null
    private var mStartTime = 0L
    private var mStopTime = 0L
    var mIsPlaying = false
    var mIsRecording = false

    private var mOnFailureBlock: (() -> Unit)? = null
    private var mOnRecordingBlock: ((startTime: Long) -> Unit)? = null
    private var mOnCompleteBlock: ((startTime: Long, stopTime: Long) -> Unit)? = null
    private var mOnPlayBlock: ((duration: Int) -> Unit)? = null
    private var mOnPlayCompleteBlock: (() -> Unit)? = null

    suspend fun startRecord() {
        releaseRecord()
        val result = withContext(Dispatchers.IO) {
            doStart(StoneApplication.instance.applicationContext)
        }
        if (!result) {
            failure()
        }
    }

    suspend fun stopRecord() {
        val result = withContext(Dispatchers.Main) {
            doStop()
        }
        if (!result) {
            failure()
        }
    }

    private fun doStart(context: Context): Boolean {
        try {
//            mAudioFile = File("${context.externalCacheDir}/stone.m4a")
            mAudioFile = File("${context.externalCacheDir}/stone.mp3")
            mAudioFile?.createNewFile()

            mMediaRecorder = MediaRecorder()
//            MediaRecorder.AudioSource.VOICE_RECOGNITION //语音识别
//            MediaRecorder.AudioSource.VOICE_COMMUNICATION //语音通话
            mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mMediaRecorder?.setOutputFile(mAudioFile?.absolutePath)
            mMediaRecorder?.setAudioEncodingBitRate(96000) //设置录制的音频编码比特率
            mMediaRecorder?.setAudioSamplingRate(441000) //采样率 44.1khz。
            mMediaRecorder?.prepare()
            mMediaRecorder?.start()
            mStartTime = System.currentTimeMillis()
            mIsRecording = true
            mOnRecordingBlock?.invoke(mStartTime)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun doStop(): Boolean {
        try {
            mIsRecording = false
            mMediaRecorder?.stop()
            mStopTime = System.currentTimeMillis()
            mOnCompleteBlock?.invoke(mStartTime, mStopTime)
            releaseRecord()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun failure() {
        if (mAudioFile?.exists() == true) {
            mAudioFile?.delete()
        }
        mAudioFile = null
        //toast("操作异常")
        mOnFailureBlock?.invoke()
    }

    fun play(context: Context): Boolean {
        try {
            mIsPlaying = true
            mMediaPlayer = MediaPlayer()
            //sdcard/Android/data/{package}/cache/stone.m4a
            mMediaPlayer?.setDataSource("${context.externalCacheDir}/stone.mp3")
            mMediaPlayer?.setOnCompletionListener {
                stopPlay()
                mOnPlayCompleteBlock?.invoke()
            }
            mMediaPlayer?.setOnErrorListener { mp, what, extra ->
                stopPlay()
                true
            }
            mMediaPlayer?.setVolume(1f, 1f)
            mMediaPlayer?.isLooping = false
            mMediaPlayer?.prepare()
            mMediaPlayer?.start()
            mOnPlayBlock?.invoke(mMediaPlayer!!.duration)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun stopPlay() {
        mIsPlaying = false
        mMediaPlayer?.setOnCompletionListener { }
        mMediaPlayer?.setOnErrorListener { mp, what, extra -> true }
        mMediaPlayer?.stop()
        mMediaPlayer?.reset()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    fun releaseRecord() {
        mMediaRecorder?.reset()
        mMediaRecorder?.release()
        mMediaRecorder = null
    }

    /**
     * 获得录音的音量，范围 0-32767, 归一化到0 ~ 1.0
     * mMediaRecorder?.maxAmplitude 最大值为 32767
     * @param maxLevel 最大音量等级
     * @return
     */
    fun getMaxAmplitude(maxLevel: Int = 1): Float {
        return if (mIsRecording) {
            (mMediaRecorder?.maxAmplitude ?: 0) * 1.0f / (32768f / maxLevel)
        } else 0f
    }

    fun setOnFailureBlock(block: (() -> Unit)?) {
        this.mOnFailureBlock = block
    }

    fun setOnRecordingBlock(block: ((startTime: Long) -> Unit)?) {
        this.mOnRecordingBlock = block
    }

    fun setOnCompleteBlock(block: ((startTime: Long, stopTime: Long) -> Unit)?) {
        this.mOnCompleteBlock = block
    }

    fun setOnPlayBlock(block: ((duration: Int) -> Unit)?) {
        this.mOnPlayBlock = block
    }

    fun setOnPlayCompleteBlock(block: (() -> Unit)?) {
        this.mOnPlayCompleteBlock = block
    }

}