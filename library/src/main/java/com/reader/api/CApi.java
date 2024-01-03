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
	密服返回数据后，传递进去，获取社保卡的其他信息
	*/
	public static native String mfGetSbInfo(int devid,String mfRetStr);

	public static native String ICC_Reader_PowerOn(int icHandle,int icc_slot_no);

}
