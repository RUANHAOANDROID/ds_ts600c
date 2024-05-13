package com.hao.ts600c.desheng

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.hao.ts600c.CustomCardInfo
import com.reader.api.CApi
import com.reader.api.ID2Parser
import com.reader.api.IDCard
import com.reader.api.SfzTransOp
import com.reader.api.StringUtil
import com.reader.api.TransOpParam
import com.sensor.idcard.IDCardDevice
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * TS600CV2
 * 选卡 1-社保卡,2-身份证,IC 34,QR：187
 */
class TS600CV2 : IDCardDevice {
    private val TAG = "TS600CV2"
    private var comDevId = -1
    private lateinit var idCall: (Int, String, IDCardInfo?) -> Unit
    private lateinit var icCall: (String) -> Unit
    private var qrCall: (String) -> Unit = {}
    private var enable = true
    var lastTicket: String = ""
    var lastTime = System.currentTimeMillis()
    var interval = 3000L
    private var additionalInterval = 1000L
    private val thread = Thread {
        while (enable) {
            runCatching {
                Thread.sleep(500)
                val card = searchCard() ?: return@runCatching
                if (card.type.isEmpty())
                    return@runCatching
                if (card.id.isEmpty())
                    return@runCatching
                val sensed = switchCard(card)
                if (sensed)
                    Thread.sleep(interval)
            }.onFailure {
                it.printStackTrace()
                LogUtils.file(it)
            }
        }
    }

    override fun start(idCall: (Int, String, IDCardInfo?) -> Unit, icCall: (String) -> Unit) {
        this.interval = interval
        this.idCall = idCall
        this.icCall = icCall
        Log.d(TAG, "start: ")
        enable = true
        val status = CApi.OpenDevice("/dev/ttyACM0", 115200)
        if (status > 0) {
            comDevId = status
            LogUtils.file("打开成功")
        } else {
            LogUtils.file("打开串口失败")
            return
        }
        thread.start()
    }

    override fun stop() {
        enable = false
        thread.interrupt()
    }

    fun addQrCall(qrCall: (String) -> Unit) {
        this.qrCall = qrCall
    }

    private fun switchCard(card: CustomCardInfo): Boolean {
        var sensed = false
        val type = card.type.toInt()
        Log.d(TAG, "switchCard: Type=${type}")
        val id = card.id
        when (type) {
            187 -> {//二维码
                val qrCode = String(Base64.decode(id, Base64.DEFAULT))
                Log.d(TAG, "Qr Call->${qrCode}")
                if (lastTicket == qrCode) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastTime > interval + additionalInterval) {
                        Log.d(TAG, "old qrcode->${qrCode}")
                        lastTime = System.currentTimeMillis()
                        lastTicket = qrCode
                        if (qrCode.length == 22) {//9200000013859456860002
                            requestProxyServer("1", qrCode)
                        } else {
                            qrCall(qrCode)
                        }
                    }
                } else {
                    Log.d(TAG, "new qrcode->${qrCode}")
                    if (qrCode.length == 22) {//9200000013859456860002
                        requestProxyServer("1", qrCode)
                    } else {
                        qrCall(qrCode)
                    }
                    lastTime = System.currentTimeMillis()
                    lastTicket = qrCode
                }
                sensed = true
            }

            1 -> {
                val sbInfo = CApi.getSbCardApproveData(comDevId)
                Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
                val cardInfo = JSONObject(sbInfo).getString("cardinfo")
                requestProxyServer(type = "2", cardInfo)
            }

            17 -> {//卡片
                if (id.length == 16) {//IC
                    val sn = id.substring(6, id.length - 2)
                    Log.d(TAG, "M1 card sn 16进制 :${sn}")
                    Log.d(TAG, "M1 card sn 10进制 :${sn.toLongOrNull(16)}")
                    Log.d(TAG, "M1 card sn 16进制反转 :${swapHexOrder(sn)}")
                    val hexLong = swapHexOrder(sn).toLong(16)
                    Log.d(
                        TAG,
                        "M1 card sn 10进制反转 :$hexLong"
                    )
                    Log.d(TAG, "call ic")
                    val ticket = hexLong.toString()
                    icCall(ticket)
                    sensed = true
                }
//                if (id.length > 16) {//社保卡
//                    val sbInfo = CApi.getSbCardApproveData(comDevId)
//                    Log.d(TAG, "cardAuto 社保卡: ${sbInfo}")
//                    val cardInfo = JSONObject(sbInfo).getString("cardinfo")
//                    requestProxyServer(type = "2", cardInfo)
//                }
            }

            2 -> {//身份证
                var id2Parser: ID2Parser?
                runCatching {
                    val opsfz = SfzTransOp()
                    opsfz.cmdOp1 = 0x03
                    opsfz.cmdOp2 = 0x00
                    opsfz.lendataSend = 0
                    val status = CApi.getSfzInfo(opsfz, this@TS600CV2.comDevId)
                    if (status == 0) {
                        val tmpRecv = ByteArray(opsfz.lenRecv)
                        System.arraycopy(opsfz.recvBuf, 0, tmpRecv, 0, opsfz.lenRecv)
                        Log.d(this@TS600CV2.TAG, "sfz_read sfz:" + StringUtil.bcd2Str(tmpRecv))
                        id2Parser = IDCard(opsfz.recvBuf, 3)
                        id2Parser?.let<ID2Parser, Unit> {
                            Log.d(this@TS600CV2.TAG, "sfz_read address = ${it.text.mAddress}")
                            Log.d(this@TS600CV2.TAG, "sfz_read cardNumber= ${it.text.mID2Num}")
                            if (id2Parser == null) {
                                Log.d(this@TS600CV2.TAG, "sfz_read 读取失败，请重试...\n")
                            }
                            val idCard = it as IDCard
                            val img = idCard.picBitmap ?: return@runCatching
                            Log.d(TAG, "call id")
                            idCall(
                                0,
                                "Success",
                                createOne("${it.text.mID2Num}", it.text.mName, img)
                            )
                            sensed = true
                        }
                    }
                }.onFailure {
                    it.printStackTrace()
                    LogUtils.file(it)
                }
            }
        }
        return sensed
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
        }.onFailure {
            it.printStackTrace()
            LogUtils.file(it)
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

    private fun hexToBytes(hexStr: String): ByteArray? {
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

    private fun createOne(id: String, name: String, photo: Bitmap?): IDCardInfo.Chinese =
        IDCardInfo.Chinese(
            name = name,
            sex = "",
            nationality = "",
            birthday = "",
            address = "",
            number = id,
            signedDepartment = "",
            effectiveDate = "",
            expiryDate = "",
            headImage = photo
        )

    private fun requestProxyServer(type: String = "2", value: String) {
        LogUtils.d("代理服务解析")
        val okHttPClient = OkHttpClient()
        val mediaType: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestJson = "{\n" +
                "    \"machineNo\": \"wlcsjg123\",\n" +
                "    \"content\": \"${value}\",\n" +
                "    \"type\": \"$type\"\n" +
                "}"
        LogUtils.d(requestJson)
        val requestBody = requestJson.toRequestBody(mediaType)
        val request =
            Request.Builder().url("http://card.web.yyxcloud.com/card/insureCard").post(requestBody)
                .build()
        try {
            val resp = okHttPClient.newCall(request).execute()
            LogUtils.d(resp)
            if (resp.isSuccessful) {
                resp.body?.let {
                    val bodyString = it.string()
                    LogUtils.d("requestProxyServer resp", bodyString)
                    val jsonObj = JSONObject(bodyString)
                    if (type == "1") {
                        bodyString.contains("aac002")
                        val id = jsonObj.optString("aac002")
                        val name = jsonObj.optString("aac003")
                        assert(id.isNotEmpty())
                        assert(name.isNotEmpty())
                        val one = createOne(id = id, name = name, photo = null)
                        idCall(0, "Success", one)
                    } else {
                        val inst = jsonObj.optString("inst")
                        val content = CApi.mfGetSbInfo(comDevId, inst)
                        LogUtils.d("串口返回值${comDevId}", content)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.file(e)
        }
    }
}