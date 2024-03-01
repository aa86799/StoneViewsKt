package com.stone.stoneviewskt.ui.ks

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentKstoreBinding
import com.stone.stoneviewskt.util.logi
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import kotlin.random.Random

/**
 * desc:    AndroidKeyStore 加解密(AES、RSA) 示例
 * author:  stone
 * email:   aa86799@163.com
 * time:    2024/2/7 16:17
 */
class KeyStoreFragment: BaseBindFragment<FragmentKstoreBinding>(R.layout.fragment_kstore) {

    /*
     * 其它示例：
     * https://github.com/zhengdd/Tortoise/blob/master/Tortoise/src/main/java/com/dongdong/animal/tortoise/keystorage/KeyStoreManager.java
     */

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.tvInfo.text = """
            尽管非对称加密在速度和资源消耗方面存在劣势，
            但它在安全性方面提供了对称加密所不具备的优势，
            特别是在密钥交换和数字签名方面。
            为了结合两种加密方式的优点，常见的做法是：
            使用非对称加密来安全地交换对称加密的密钥，
            然后使用对称加密来处理实际的数据加密任务。
            这种混合加密方法结合了非对称加密的安全性和对称加密的高效性，
            广泛应用于各种安全通信协议中，如TLS/SSL。
        """.trimIndent()

        // 别名 标识密钥对
        val keystoreAlias = "stone001"
        var encryptData: ByteArray? = null

        val aesSalt = ByteArray(16)
        SecureRandom().nextBytes(aesSalt) // 随机16位字节

        mBind.btnGenerate.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                saveKey(keystoreAlias)
            }
        }

        mBind.btnEncrypt.setOnClickListener {
            val data = "测试数据 test data ${Random.nextLong()}"
            mBind.tvContent.text = "AES 加密原始数据：$data"
            encryptData = encryptData(keystoreAlias, data, aesSalt)
        }

        mBind.btnDecrypt.setOnClickListener {
            encryptData?.let {
                val decryptData = decryptData(keystoreAlias, it, aesSalt)
                mBind.tvContent.text = "${mBind.tvContent.text}\nAES 解密后数据：$decryptData"
                logi("AES 解密后数据：$decryptData")
            }
        }

        mBind.btnClear.setOnClickListener {
            mBind.tvContent.text = ""
        }

        mBind.btnRsa.setOnClickListener {
            // RSA 算法 加解密 test
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                lifecycleScope.launch {
                    KsRsaUtil.test()
                }
            }
        }
    }

    // AES 对称加密方法，只有一个密钥
    @RequiresApi(Build.VERSION_CODES.M) // 6.0
    private fun saveKey(keystoreAlias: String) {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                keystoreAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setKeySize(256) // 128 or 256
                .build())
        // 密钥对，生成后，将自动存储在 AndroidKeyStore 中
        val key = keyGenerator.generateKey()
    }

    // 使用存储在 AndroidKeyStore 中的公钥来加密数据，通过密钥对别名获取
    private fun encryptData(keystoreAlias: String, data: String, salt: ByteArray): ByteArray? {
        return try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val secretKey = keyStore.getKey(keystoreAlias, null)
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            // 手动加盐(IV)报错了: java.security.InvalidAlgorithmParameterException:
            // Caller-provided IV not permitted   (不被允许)
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(salt))
            // 自动 生成 IV
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val byteArray = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
            val iv = cipher.iv
            MMKV.defaultMMKV().encode("aesIv", iv) // 缓存 iv
            byteArray
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 使用存储在 AndroidKeyStore 中的私钥来解密数据，通过密钥对别名获取
    private fun decryptData(keystoreAlias: String, encryptedData: ByteArray, salt: ByteArray): String? {
        return try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val secretKey = keyStore.getKey(keystoreAlias, null)
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            // 手动加盐(IV)报错了
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(salt))
            val aesIv = MMKV.defaultMMKV().decodeBytes("aesIv")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(aesIv))
            val decryptedData = cipher.doFinal(encryptedData)
            String(decryptedData, StandardCharsets.UTF_8)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
}