package com.reader.api;

public class TransOpParam {
    public int transType; //交易类型，0x60,寻卡，0x40,消费
    public byte[]transDate=new byte[3]; //当前的日期
    public byte[]transTime=new byte[3];//当前的时间
    public int transAmt; //交易金额
    public byte[]transNo=new byte[4];//交易流水号
    public byte[]transOutBuf=new byte[4096];//交易完成后返回
   public int lenTransOutbuf;//返回的数据长度

   public byte[]inputPinStr=new byte[16];//消费密码，ASC码

}
