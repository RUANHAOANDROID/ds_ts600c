/***************************************************************************************
 * <p>文件名称：CanPkg.java </p>  
 * <p>创建时间：2016年1月19日 上午9:40:21 </p> 
 * <p>文件说明： </p>     
 * @author liuwenrui  
 * @version 1.0   
 * @since JDK 1.6      
 ****************************************************************************************/

package com.reader.api;


public class CApi{

	static {
		System.loadLibrary("LxApi");
	}
	/*
	 * 打开设备，目前是串口的标识符，如："/dev/ttyACM0"
	 * bauld 波特率，默认 115200
	 * 成功返回设备的标识符,>=0,其他失败
	 */
	public static native int OpenDevice(String devname,int bauld);
	/*
	获取版本号,返回TLV编码的字符串
	 */
	public static native String getterminfo(int devId);
	/*
	解析终端返回的版本信息
	*/
	 public static native int parseTermInfo(byte[]infoTermbuf,int leninfoterminfo,Terminfo terminfo);

	/*
	 * 关闭设备，devId:需要关闭的设备Id
	 */
	public static native int CloseDevice(int devId);

	/*
	寻卡和消费处理
	*/
	public static native int TransCardProcess(TransOpParam opParam,int devid);

	/*
	获取社保卡信息
	*/
	public static native String getSbCardInfo(int devid);
	/*
	获取身份证信息
	*/
	public static native int getSfzInfo(SfzTransOp opsfz,int devid);

	/**
	 * 升级
	 * @param firmBin 固件数据
	 * @param lenfirmbin 长度
	 * @param devid 串口号
	 * @return
	 */
	public static native int CCUpdateFirm(byte[]firmBin,int lenfirmbin,int devid);

	/*
	发送和获取命令，可通过该接口和卡片进行交互
	*/
	public static native int CmdOp(CmdOpTrans opParam,int devid);

	/*
	设置日志级别,
	  ANDROID_LOG_UNKNOWN = 0,
    ANDROID_LOG_DEFAULT=1,
	ANDROID_LOG_VERBOSE=2,
	ANDROID_LOG_DEBUG=3,
	ANDROID_LOG_INFO=4,
	ANDROID_LOG_WARN=4,
	ANDROID_LOG_ERROR=5,
	ANDROID_LOG_FATA=6,
	ANDROID_LOG_SILENT=7,
	默认为ANDROID_LOG_DEBUG
	*/
	public static native int SetLogLevel(int levelLog);

	/*
	获取社保卡认证信息
	*/
	public static native String getSbCardApproveData(int devid);

	/*
	社保卡整合封装
		//通过PSAM卡获取卡片所有信息,op=1，借助PSAM卡获取社保卡信息，op=02:返回社保卡授权信息，03：密服返回的数据输入社保卡授权，返回需要读到的数据
	/*OP=1
	*读出的社保卡基本信息各数据项，依次为：发卡地区行政区划代码（卡识别码前6位）、社会保障号码、卡号、卡识别码、姓名、卡复位信息（仅取历史字节）、
	规范版本、发卡日期、卡有效期、终端机编号、终端设备号。各数据项之间以“|”分割，且最后一个数据项以“|”结尾
	OP=2:
	输出参数为读出的社保卡内部认证和外部认证的计算数据，依次为：发卡地区行政区划代码（卡识别码前6位）、卡复位信息（仅取历史字节）、
	算法标识、卡识别码、内部认证过程因子、内部认证鉴别所需的原始信息、外部认证过程因子、
	外部认证鉴别所需的原始信息，其中外部认证相关数据项全部不为空或全部为空。各数据项之间以“|”分割，且最后一个数据项以“|”结尾
	OP=3:
	返回信息同OP=1
	OP=4:
	输入参数无
	返回:卡片发行机构代码|社保卡卡号|卡片识别码|卡复位的信息|卡片版本|发行日期|有效日期
	*/
	public static native String SbGetAllInfo(int devComid,int op,String serverdata);
	/*
	密服返回数据后，传递进去，获取社保卡的其他信息
	*/
	public static native String mfGetSbInfo(int devid,String mfRetStr);

	public static native String ICC_Reader_PowerOn(int icHandle,int icc_slot_no);

}
