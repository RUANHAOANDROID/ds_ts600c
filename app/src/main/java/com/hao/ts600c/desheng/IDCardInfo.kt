package com.hao.ts600c.desheng

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * ID card info
 *  完整的身份证信息
 *  @author 锅得铁
 *  @property name 姓名
 *  @property sex 性别
 *  @property nationality 国籍
 *  @property birthday 生日
 *  @property birthday 户籍
 *  @property number 身份证号码
 *  @property signedDepartment 颁发部门
 *  @property signedDepartment 颁发部门
 *  @property expiryDate 到期时间
 *  @property headImage 头像
 *  @constructor create IDCard info
 * @date 2022/10/9
 * @author hao
 * @since v1.0
 */
@Parcelize
sealed class IDCardInfo : Parcelable {
    @Parcelize
    data class Chinese(
        val name: String,
        val sex: String,
        val nationality: String,
        val birthday: String,
        val address: String,
        val number: String,
        val signedDepartment: String,
        val effectiveDate: String,
        val expiryDate: String,
        var headImage: Bitmap?
    ) : IDCardInfo(), Parcelable
    @Parcelize
    data class Other(
        val name: String,
        val sex: String,
        val nationality: String,
        val birthday: String,
        val address: String,
        val number: String,
        val signedDepartment: String,
        val effectiveDate: String,
        val expiryDate: String,
        var headImage: Bitmap?
    ) : IDCardInfo(), Parcelable
}

