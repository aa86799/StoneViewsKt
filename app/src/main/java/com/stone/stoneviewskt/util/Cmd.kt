package com.stone.stoneviewskt.util

import java.io.BufferedReader
import java.io.InputStreamReader

object Cmd {

    val result = StringBuilder()

    // 设备需要root
    fun run(cmd: String): String {
        /*var bufferedReader: BufferedReader? = null
        var dos: DataOutputStream? = null
        var receive = ""

        try {
            Runtime.getRuntime().exec("su")?.run { // 经过Root处理的android系统即有su命令
                logi("Cmd run: $cmd")
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                dos = DataOutputStream(outputStream).apply {
                    writeBytes(cmd + "\n")
                    flush()
                    writeBytes("exit\n")
                    flush()
                }

                bufferedReader?.run {
                    while (readLine().also { receive = it } != null) {
                        result.append("\n").append(receive)
                    }
                }

                waitFor()
            }
        } catch (e: Exception) {
            return false
        }
        try {
            dos?.close()
            bufferedReader?.close()
        } catch (e: Exception) {
            return false
        }
        return true*/

        try {
            val process = Runtime.getRuntime().exec(cmd)
            val inputStream = process.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            try {
                process.waitFor()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var line: String? = null
            while ((bufferedReader.readLine().also { line = it }) != null ) {
                result.append(line).append("\n")
            }
            inputStream.close()
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result.toString()
    }
}
