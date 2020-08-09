package com.stone.stoneviewskt.ui.audio

/**
 * desc:    音高处理，jni调用 smbPitchShift.cpp 进行处理音高变调
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/2 12:24
 */
class AudioPitchProcessor {

    private val mBufferSize: Int
    private val mOutputBuffer: ByteArray
    private val mFloatInput: FloatArray
    private val mFloatOutput: FloatArray

    constructor(bufferSize: Int) {
        this.mBufferSize = bufferSize
        this.mOutputBuffer = ByteArray(mBufferSize)
        //两个byte 对应一个 float
        this.mFloatInput = FloatArray(mBufferSize shr 1)
        this.mFloatOutput = FloatArray(mBufferSize shr  1)
    }

    fun process(ratio: Float, sampleRate: Int, input: ByteArray): ByteArray {
        process(ratio, input, mOutputBuffer, mBufferSize, sampleRate, mFloatInput, mFloatOutput)
        return mOutputBuffer
    }

    /*
     * ratio 在1.5以内比较正常；2.0 就听不清了
     */
    private external fun process(ratio: Float, input: ByteArray, output: ByteArray, size: Int,
                                 sampleRate: Int, floatInput: FloatArray, floatOutput: FloatArray)
}