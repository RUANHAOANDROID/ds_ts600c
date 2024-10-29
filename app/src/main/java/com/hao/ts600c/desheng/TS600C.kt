package com.sensor.idcard.desheng

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Base64
import android.util.Log
import com.hao.ts600c.desheng.IDCardInfo
import com.reader.api.CApi
import com.reader.api.ID2Parser
import com.reader.api.IDCard
import com.reader.api.SfzTransOp
import com.reader.api.StringUtil
import com.reader.api.TransOpParam
import com.sensor.idcard.IDCardDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class TS600C(
    private val context: Context,
    private val cScope: CoroutineScope
) : IDCardDevice {
    val TAG = "TS600C"
    var comDevId = -1
    lateinit var idCall: (Int, String, IDCardInfo?) -> Unit
    lateinit var icCall: (String) -> Unit
    private var qrCall: (String) -> Unit = {}
    override fun start(idCall: (Int, String, IDCardInfo?) -> Unit, icCall: (String) -> Unit) {
        this.idCall = idCall
        this.icCall = icCall
        Log.d(TAG, "start: ")
        cardAuto()
    }

    override fun stop() {

    }

    fun addQrCall(qrCall: (String) -> Unit) {
        this.qrCall = qrCall
    }

    private fun cardAuto() {
        cScope.launch(Dispatchers.IO) {
            while (true) {
                with(searchCard()) {
                    if (this == null)
                        return@with
                    if (type.isEmpty())
                        return@with
                    if (id.isEmpty())
                        return@with
                    switchCard(type.toInt())
                }
            }
        }
    }

    private fun CustomCardInfo.switchCard(type: Int) {
        when (type) {
            1 -> {//二维码
                if (id.contains("040008")) {
                    val sn = id.substring(6, id.length - 2)
                    Log.d(TAG, "M1 card sn 16进制 :${sn}")
                    Log.d(TAG, "M1 card sn 10进制 :${sn.toLongOrNull(16)}")
                    Log.d(TAG, "M1 card sn 16进制反转 :${swapHexOrder(sn)}")
                    Log.d(
                        TAG,
                        "M1 card sn 10进制反转 :${swapHexOrder(sn).toLong(16)}"
                    )
                    qrCall(sn)
                }
                if (id.length > 16) {//社保卡
                    val sbInfo = CApi.getSbCardApproveData(comDevId)
                    Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Title")
                        .setMessage(sbInfo)
                        .setIcon(R.drawable.ic_dialog_alert)
                        .setPositiveButton("OK") { dialog, id ->
                            // 处理确定按钮的点击事件
                        }
                        .setNegativeButton("Cancel") { dialog, id ->
                            // 处理取消按钮的点击事件
                        }
                    val params = JSONObject(sbInfo).getString("cardinfo").split("|")
                    for (item in params) {
                        Log.d(TAG, "item: ${item.hexToUnicode()}")
                    }
                    Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
                    Log.d(TAG, "cardAuto: unix${id.hexToUnicode()}")
                    Log.d(TAG, "cardAuto: unix${id.hexToUnicode()}")
                    hexToBytes(sbInfo)?.let {
                        try {
                            val res = String(it, Charset.forName("GBK"))
                            Log.d(TAG, "社保卡 gbk:${res}")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            2 -> {//身份证
                var id2Parser: ID2Parser?
                runCatching {
                    val opsfz = SfzTransOp()
                    opsfz.cmdOp1 = 0x03
                    opsfz.cmdOp2 = 0x00
                    opsfz.lendataSend = 0
                    val status = CApi.getSfzInfo(opsfz, this@TS600C.comDevId)
                    if (status == 0) {
                        val tmpRecv = ByteArray(opsfz.lenRecv)
                        System.arraycopy(opsfz.recvBuf, 0, tmpRecv, 0, opsfz.lenRecv)
                        Log.d(this@TS600C.TAG, "sfz_read sfz:" + StringUtil.bcd2Str(tmpRecv))
                        id2Parser = IDCard(opsfz.recvBuf, 3)
                        id2Parser?.let<ID2Parser, Unit> {
                            Log.d(this@TS600C.TAG, "sfz_read address = ${it.text.mAddress}")
                            Log.d(this@TS600C.TAG, "sfz_read cardNumber= ${it.text.mID2Num}")
                            //                    runOnUiThread {
                            if (id2Parser == null) {
                                Log.d(this@TS600C.TAG, "sfz_read 读取失败，请重试...\n")
                            }
                            val idCard = it as IDCard
                            val img = idCard.picBitmap
                            //                    }
                        }
                    }
                }
            }

            187 -> {//二维码
                Log.d(TAG, "cardAuto: qrcode ${""}")
                val qrCode = String(Base64.decode(id, Base64.DEFAULT))
                Log.d(TAG, "cardAuto: $qrCode")
                qrCall(qrCode)
            }

        }
    }

    // 寻卡 返回卡类型
    private fun searchCard(): CustomCardInfo? {
        val opTrans = TransOpParam()
        System.arraycopy("123456".toByteArray(), 0, opTrans.inputPinStr, 0, 6)
        System.arraycopy(byteArrayOf(0x00, 0x00, 0x00, 0x01), 0, opTrans.transNo, 0, 4)
        System.arraycopy(byteArrayOf(0x21, 0x05, 0x16), 0, opTrans.transDate, 0, 3)
        System.arraycopy(byteArrayOf(0x11, 0x15, 0x16), 0, opTrans.transTime, 0, 3)
        opTrans.transAmt = 1 //分为单位
        opTrans.transType = 0x60
        val iret = CApi.TransCardProcess(opTrans, comDevId)
        if (iret != 0) {
            return null
        }
        var cci = CustomCardInfo()
        runCatching {
            val cardInfo = String(opTrans.transOutBuf)
            Log.d(TAG, "searchcard 寻卡返回：$cardInfo")
            Log.d(TAG, "searchcard 返回的数据长度" + opTrans.lenTransOutbuf)
            Log.d(TAG, "searchcard 交易完成后返回 " + StringUtil.bcd2Str(opTrans.transOutBuf))
            val jsondata: JSONObject = JSONObject(cardInfo)
            cci.info = cardInfo
            cci.type = jsondata.getString("cardType")
            cci.id = jsondata.getString("ID")
            cci
        }
        return cci
    }

    private fun swapHexOrder(hexString: String): String {
        if (hexString.length != 8) {
            throw IllegalArgumentException("输入的十六进制字符串必须是8位")
        }

        val reversedHex = hexString.reversed()
        val swappedHex = StringBuilder()

        for (i in 0 until 8 step 2) {
            swappedHex.append(reversedHex[i + 1])
            swappedHex.append(reversedHex[i])
        }
        return swappedHex.toString()
    }
}