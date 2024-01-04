package com.hao.ts600c

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hao.ts600c.desheng.IDCardInfo
import com.reader.api.CApi
import com.reader.api.ID2Parser
import com.reader.api.IDCard
import com.reader.api.SfzTransOp
import com.reader.api.StringUtil
import com.reader.api.TransOpParam
import com.sensor.idcard.desheng.TS600C
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var comDevId = -1
    val imgHead by lazy { findViewById<ImageView>(R.id.imgHead) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnSearchCard).setOnClickListener {
            findCard()
        }
        findViewById<Button>(R.id.btnAudo).setOnClickListener {
//            cardAuto()
            testTS600C()
        }
        val status = CApi.OpenDevice("/dev/ttyACM0", 115200)
        if (status > 0) {
            comDevId = status;
        } else {

        }
        Log.d(TAG, "OpenDevice status =${status}")
//        Thread {
//            while (true) {
//                Thread.sleep(1000)
//                readSBCard()
//                Thread.sleep(500)
//                readIdCard()
//            }
//        }.start()
    }

    private fun testTS600C() {
        val scope = CoroutineScope(Dispatchers.Default)
        val tS600C = TS600C(this, scope)
        tS600C.addQrCall {
            Log.d(TAG, "testTS600C: qr =${it}")
        }
        val idCall: (Int, String?, IDCardInfo?) -> Unit = { status, message, idCardInfo ->
            if (status == 0 && idCardInfo != null) {
                if (idCardInfo is IDCardInfo.Chinese) {
                    Log.d(TAG, "id card call:  ${idCardInfo.number}")
                }
            }
        }
        val icCall: (String) -> Unit = { icNo ->
            Log.d(TAG, "testTS600C:  ${icNo}")
        }
        tS600C.start(idCall, icCall)
    }

    private fun cardAuto() {
        Thread {
            var iret = 0
            var tickStrart: Long = 0
            var tickend: Long = 0
            while (true) {
                with(searchCard()) {
                    if (this == null)
                        return@with
                    if (type.isEmpty())
                        return@with
                    if (id.isEmpty())
                        return@with
                    iret = type.toInt()
                    tickStrart = System.currentTimeMillis()
                    when (iret) {
                        1 -> {

                            if (id.contains("040008")) {
                                val sn = id.substring(6, id.length - 2)
                                Log.d(TAG, "M1 card sn 16进制 :${sn}")
                                Log.d(TAG, "M1 card sn 10进制 :${sn.toLongOrNull(16)}")
                                Log.d(TAG, "M1 card sn 16进制反转 :${swapHexOrder(sn)}")
                                Log.d(
                                    TAG,
                                    "M1 card sn 10进制反转 :${swapHexOrder(sn).toLong(16)}"
                                )
                            }
                            if (id.length > 16) {
                                val sbInfo = CApi.getSbCardApproveData(comDevId)
                                Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
                                val params = JSONObject(sbInfo).getString("cardinfo").split("|")
                                for (item in params) {
                                    Log.d(TAG, "item: ${item.hexToUnicode()}")
                                }
                                Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
                                //社保卡先签到
                                Log.d(TAG, "cardAuto: unix${id.hexToUnicode()}")

                                Log.d(TAG, "cardAuto: unix${id.hexToUnicode()}")
//                                hb_sb_token = Api().checkLogin(
//                                    CheckLogin(
//                                        "TSB",
//                                        "bcfe9e3890e6c26deb554643e208bfb8",
//                                        "bcfe9e3890e6c26deb554643e208bfb8"
//                                    )
//                                ).data()
//                                val mac = Mac(sbInfo)
//                                com.sodo.qr600sdk.TestActivity.hb_sb_server_msg =
//                                    Api().setTokenid(com.sodo.qr600sdk.TestActivity.hb_sb_token)
//                                        .innerAuthenticate(
//                                            mac
//                                        ).data()
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

                        2 -> {
                            idCardRead()
                            tickend = System.currentTimeMillis()
                            Log.d(TAG, "耗时:" + (tickend - tickStrart))
                        }

                        187 -> {
                            Log.d(TAG, "cardAuto: qrcode ${""}")
                            val cardId = String(Base64.decode(id, Base64.DEFAULT))
                            Log.d(TAG, "cardAuto: ${cardId}")
                        }

                    }
                }


            }
        }.start()
    }

    private fun findCard() {
        val opTrans = TransOpParam()
        System.arraycopy("123456".toByteArray(), 0, opTrans.inputPinStr, 0, 6)
        System.arraycopy(byteArrayOf(0x00, 0x00, 0x00, 0x01), 0, opTrans.transNo, 0, 4)
        System.arraycopy(byteArrayOf(0x21, 0x05, 0x16), 0, opTrans.transDate, 0, 3)
        System.arraycopy(byteArrayOf(0x11, 0x15, 0x16), 0, opTrans.transTime, 0, 3)
        opTrans.transAmt = 1 //分为单位
        opTrans.transType = 0x60
        val iret = CApi.TransCardProcess(opTrans, comDevId)
        if (iret != 0) {
            Log.d(TAG, "寻卡失败：" + String(opTrans.transOutBuf))
        }
        Log.d(TAG, "findCard 寻卡返回：" + String(opTrans.transOutBuf))
        Log.d(TAG, "findCard 返回的数据长度" + opTrans.lenTransOutbuf)
        Log.d(TAG, "findCard 交易完成后返回" + StringUtil.bcd2Str(opTrans.transOutBuf))
        Log.d(TAG, "findCard 交易完成后返回" + StringUtil.bcd2Str(opTrans.inputPinStr))
    }

    fun readQRCode() {

    }

    fun readIdCard() {

    }

    fun readSBCard() {
        val sbCardInfo = CApi.getSbCardInfo(comDevId)
        Log.d(TAG, "readSBCard: $sbCardInfo")
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

    //读身份证
    private fun idCardRead() {
        var id2Parser: ID2Parser? = null
        runCatching {
            val opsfz = SfzTransOp()
            opsfz.cmdOp1 = 0x03
            opsfz.cmdOp2 = 0x00
            opsfz.lendataSend = 0
            val status = CApi.getSfzInfo(opsfz, comDevId)
            if (status == 0) {
                val tmpRecv = ByteArray(opsfz.lenRecv)
                System.arraycopy(opsfz.recvBuf, 0, tmpRecv, 0, opsfz.lenRecv)
                Log.d(TAG, "sfz_read sfz:" + StringUtil.bcd2Str(tmpRecv))
                id2Parser = IDCard(opsfz.recvBuf, 3)
                id2Parser?.let {
                    Log.d(TAG, "sfz_read address = ${it.text.mAddress}")
                    Log.d(TAG, "sfz_read cardNumber= ${it.text.mID2Num}")
                    runOnUiThread {
                        if (id2Parser == null) {
                            Log.d(TAG, "sfz_read 读取失败，请重试...\n")
                            imgHead.setImageBitmap(null)
                            return@runOnUiThread
                        }
                        val idCard = it as IDCard
                        val img = idCard.picBitmap
                        imgHead.setImageBitmap(img)
                    }
                }
            }
        }
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

    fun hexToBytes(hexStr: String): ByteArray? {
        var hexStr = hexStr ?: return null
        if (hexStr.length == 1) {
            hexStr = "0$hexStr"
        }
        val length = hexStr.length / 2
        val result = ByteArray(length)
        for (i in 0 until length) {
            result[i] = hexStr.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
        return result
    }

    private fun String.hexToUnicode(): String {
        val unicodeStringBuilder = java.lang.StringBuilder()
        val hexChunks = this.chunked(4)
        for (chunk in hexChunks) {
            val swappedChunk = chunk.substring(2, 4) + chunk.substring(0, 2)
            val unicodeValue = swappedChunk.toInt(16)
            unicodeStringBuilder.append(unicodeValue.toChar())
        }
        return unicodeStringBuilder.toString()
    }
}