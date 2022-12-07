package com.stone.stoneviewskt.ui.jetpack.workmanager

import android.os.Bundle
import android.view.View
import androidx.work.*
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentWorkManagerBinding
import java.util.concurrent.TimeUnit

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/27 08:47
 */
class WorkManagerFragment: BaseBindFragment<FragmentWorkManagerBinding>(R.layout.fragment_work_manager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBind.btnOneTime.setOnClickListener {
            // 周期性工作任务
            val periodic = PeriodicWorkRequestBuilder<UploadWorker>(10L, TimeUnit.SECONDS)
            // 一次性任务
            val oneTime = OneTimeWorkRequestBuilder<UploadWorker>()
            // 工作约束
            val constraints = Constraints.Builder() // 设置约束
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络已连接
                .setRequiresCharging(false) // 若为 true，那么工作只能在设备充电时运行
                .setRequiresBatteryNotLow(true) // 若为 true，电量非低时，才能运行
                // .setRequiresDeviceIdle(true) // 若为 true，则设备必须处于空闲状态才能运行worker  api23新增
                .setRequiresStorageNotLow(true) // 若为 true，那么当设备上的存储空间不足时，不会运行工作
                // .addContentUriTrigger(uri, true) // api24； 监听 uri的变化，第二个参数若为true， 表示如果子进程中的任何更改导致此工作请求运行，则返回true
                // .setTriggerContentMaxDelay(10, TimeUnit.MINUTES) // api24; 最大延迟触发时间
                // .setTriggerContentUpdateDelay(1, TimeUnit.MINUTES) // api24;
                .build()

            /*
             * addTriggerContentUri(uri, boolean)
             * 假设需求是监听联系人的uri: ContactsContract.Contacts.CONTENT_URI ； 每当联系人变化时，就启动worker任务查询所有联系人的信息。
             *
             * 假设某个时候，例如批量删除联系人时，联系人数据库变化很快，worker将会频繁运行；
             * setTriggerContentUpdateDelay(1, TimeUnit.SECONDS) 更新延时设为1秒；
             * 表示在1s 内 uri 连续变化，不会触发任务；1s 内无变化，即超过1s才变化，才会启动任务。
             * 简短的说：超过设定时间的更新变化，才会触发新的任务。
             *
             * setTriggerContentMaxDelay(10, TimeUnit.SECONDS) 最大延时。
             * 基于上个设定(UpdateDelay)，若总耗时为30s，且每次变化都在1s 内，那么worker任务就要在30s 后执行。
             * 设定MaxDelay后，10s 后执行任务。
             */

            val uploadWorkRequest: WorkRequest = oneTime
                .setConstraints(constraints)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            // val uploadWorkRequest: WorkRequest = OneTimeWorkRequest.from(UploadWorker::class.java)
            WorkManager.getInstance(requireContext())
                .enqueue(uploadWorkRequest) // 执行worker的确切时间取决于 WorkRequest 中使用的约束和系统优化方式
        }
    }
}