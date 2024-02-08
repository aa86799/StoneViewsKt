package com.stone.stoneviewskt.ui.ks

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.stone.stoneviewskt.util.logi
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.crypto.Cipher
import kotlin.random.Random

/**
 * desc:    rsa 非对称加密，两个密钥，一个公钥，一个私钥
 * author:  stone
 * email:   aa86799@163.com
 * time:    2024/2/8 09:20
 */
object KsRsaUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun generateKeyPairAndStore(alias: String?) {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
            keyPairGenerator.initialize(KeyGenParameterSpec.Builder(
                alias!!,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setKeySize(2048)
                .build())
            keyPairGenerator.generateKeyPair()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun encryptData(alias: String?, data: String): ByteArray? {
        return try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.certificate.publicKey)
            cipher.doFinal(data.toByteArray())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decryptData(alias: String?, encryptedData: ByteArray?): String? {
        return try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
            val decryptedData = cipher.doFinal(encryptedData)
            String(decryptedData)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun test() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return

        val keystoreAlias = "androidKeystoreTestKey"
        // 生成并存储密钥对
        generateKeyPairAndStore(keystoreAlias)
        // 待加密的数据
        val dataToEncrypt = "RSA被加密数据： Android Keystore  ${Random.nextLong()}"
        logi("原始数据: ($dataToEncrypt)")
        // 加密数据
        val encryptedData = encryptData(keystoreAlias, dataToEncrypt)
        // 解密数据
        val decryptedData = decryptData(keystoreAlias, encryptedData!!)
        // 打印解密后的数据
        logi("RSA解密Data数据: ($decryptedData)")
    }
}