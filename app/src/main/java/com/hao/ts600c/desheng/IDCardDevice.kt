package com.sensor.idcard

import com.hao.ts600c.desheng.IDCardInfo

/**
 * 身份证模块
 * @date 2022/10/9
 * @author hao
 * @since v1.0
 */
interface IDCardDevice {
    /**
     *
     *开始读卡
     * @param idCall (status:Int message:String info:IDCardInfo?)
     */
    fun start(idCall: (Int, String, IDCardInfo?) -> Unit, icCall: (String) -> Unit={})

    /**
     * 停止读卡
     *
     */
    fun stop()
}