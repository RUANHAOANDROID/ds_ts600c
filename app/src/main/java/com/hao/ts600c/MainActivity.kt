package com.hao.ts600c

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.reader.api.CApi
import com.reader.api.ID2Parser
import com.reader.api.IDCard
import com.reader.api.SfzTransOp
import com.reader.api.StringUtil
import com.reader.api.TransOpParam
import org.json.JSONObject

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
            cardAuto()
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

    private fun cardAuto() {
        Thread {
            var iret = 0
            var tickStrart: Long = 0
            var tickend: Long = 0
            while (true) {
                val info = searchcard()
                if (info != null) {
                    iret = info.type.toInt()
                    tickStrart = System.currentTimeMillis()
                    if (iret == 2) {
                        if (sfz_read() == 0) {
                            tickend = System.currentTimeMillis()
                            Log.d(TAG, "耗时:" + (tickend - tickStrart))
                        }
                    }
                    if (iret == 187) {
                        Log.d(TAG, "cardAuto: qrcode ${""}")
                        val cardId = String(Base64.decode(info.id, Base64.DEFAULT))
                        Log.d(TAG, "cardAuto: ${cardId}")
                    }
                    if (iret == 1) {
                        if (info.id.contains("040008")) {
                            val sn = info.id.substring(6, info.id.length - 2)
                            Log.d(TAG, "M1 card sn 16进制 :${sn}")
                            Log.d(TAG, "M1 card sn 10进制 :${sn.toLong(16)}")
                            Log.d(TAG, "M1 card sn 16进制反转 :${swapHexOrder(sn)}")
                            Log.d(TAG, "M1 card sn 10进制反转 :${swapHexOrder(sn).toLong(16)}")
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
    private fun searchcard(): CustomCardInfo? {
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

    private var isReading = false

    //读身份证
    private fun sfz_read(): Int {
        runOnUiThread {
//                    logImg.setImageBitmap(bitmap);

        }
        isReading = true
        var id2Parser: ID2Parser? = null
        try {
            val opsfz = SfzTransOp()
            opsfz.cmdOp1 = 0x03
            opsfz.cmdOp2 = 0x00
            opsfz.lendataSend = 0
            val iret = CApi.getSfzInfo(opsfz, comDevId)
            if (iret == 0) {
                val tmpRecv = ByteArray(opsfz.lenRecv)
                System.arraycopy(opsfz.recvBuf, 0, tmpRecv, 0, opsfz.lenRecv)
                Log.d(TAG, "sfz_read sfz:" + StringUtil.bcd2Str(tmpRecv))
                var r: IDCard? = null
                try {
                    r = IDCard(opsfz.recvBuf, 3)
                    id2Parser = r
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            val idData = id2Parser
            isReading = false
            runOnUiThread {
//                    BaseIDReadActivity.this.onIDCardRead(idData);
                if (idData == null) {
                    Log.d(TAG, "sfz_read 读取失败，请重试...\n")
                    imgHead.setImageBitmap(null)
                } else {
                    Log.d(TAG, "sfz_read address = ${idData.text.mAddress}")
                    Log.d(TAG, "sfz_read cardNumber= ${idData.text.mID2Num}")
                    //                        addLog("\n");
                    val idCard = idData as IDCard
                    val img = idCard.picBitmap
                    imgHead.setImageBitmap(img)
                }
            }
            return if (idData == null) 1 else 0
        }
    }

    fun swapHexOrder(hexString: String): String {
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