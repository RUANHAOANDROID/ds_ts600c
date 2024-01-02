package com.reader.api;

public class SfzTransOp {
    public int cmdOp1;//2-获取二代证的UID，其他获取二代证信息
    public int cmdOp2;//获取二代证信息，1-不带指纹数据，2-带指纹数据
    public byte[] dataSend=new byte[1024];
    public int lendataSend;
    public byte[] recvBuf=new byte[4096];
    public int  lenRecv;
}
