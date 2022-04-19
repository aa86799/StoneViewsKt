package com.stone.stoneviewskt.ui.jetpack.workmanager

import android.content.Context
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.stone.stoneviewskt.util.logi

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Do the work here--in this case, upload the images.
        uploadImages()

        // Indicate whether the work finished successfully with the Result
        /*
        Result.success()：工作成功完成。
        Result.failure()：工作失败。
        Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
         */
        return Result.success()
    }

    private fun uploadImages() {
        logi("uploadImages")
    }

    /**
     * worker 不知道自身所执行的工作是否已加急 。
     * 这样做会在 Android 12 之前的版本中创建通知
     */
    override fun getForegroundInfoAsync(): ListenableFuture<ForegroundInfo> {
        return super.getForegroundInfoAsync()
    }
}
