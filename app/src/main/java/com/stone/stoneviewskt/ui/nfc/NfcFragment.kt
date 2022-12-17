package com.stone.stoneviewskt.ui.nfc

import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.stoneToast
import java.nio.charset.Charset

/**
 * desc:    不同的 nfc 实现协议，对应不的 inter-filter
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/23 16:53
 */
class NfcFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val adapter = NfcAdapter.getDefaultAdapter(requireContext())
        if (adapter == null) {
            stoneToast("设备不支持 NFC")
        } else {
            if (!adapter.isEnabled) {
                stoneToast("NFC 功能 不可用")
            } else {
                stoneToast("NFC 功能 是可用的")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun writeNFC() {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == _mActivity.intent.action) {
            val c = _mActivity.intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(c)
            val ndefTextRecord = NdefRecord.createTextRecord("zh_CN", "石破天")
            val ndefUriRecord = NdefRecord.createUri(Uri.parse("http://www.google.com"))
//            val message = NdefMessage(ndefTextRecord) //单记录
            val message = NdefMessage(arrayOf(ndefTextRecord, ndefUriRecord)) //多记录
            ndef.writeNdefMessage(message)
        }
    }

    private fun read() {
        val array = _mActivity.intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        array?.forEach {
            val message = it as NdefMessage
            message.records.forEach {
                val record = String(it.payload, Charset.forName("utf-8"))
            }
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_nfc
    }
}