package com.reader.api;

public class CmdOpTrans {
    public int cmdIns;//命令操作符
    public int cmdOp1;//命令操作参数1
    public int cmdOp2;//命令操作参数2
    public byte[] dataSend=new byte[1024];
    public int lendataSend;
    public byte[] recvBuf=new byte[4096];
    public int  lenRecv;

    public byte cardType=0;//操作卡类型 00：非接卡，0A 接触卡

    public byte[]apduExchange(byte[]apducmd,int lencmd,int devid)
    {
        int iret=0;
        CmdOpTrans opTrans=new CmdOpTrans();
        opTrans.cmdIns=0xCC;
        if(cardType==0)
            opTrans.cmdOp1=0x01;
        else
            opTrans.cmdOp1=0x0A;
        opTrans.cmdOp2=0x03;
        opTrans.lendataSend=lencmd;
        System.arraycopy(apducmd,0,opTrans.dataSend,0,lencmd);
        iret=CApi.CmdOp(opTrans,devid);
        if(iret!=0)
            return null;
        byte[]tmprecv=new byte[opTrans.lenRecv];
        System.arraycopy(opTrans.recvBuf,0,tmprecv,0,opTrans.lenRecv);
        return tmprecv;
    }
    public byte[]apduExchangeToQrcodeDevice(byte[]apducmd,int lencmd,int devid)
    {
        int iret=0;
        CmdOpTrans opTrans=new CmdOpTrans();
        opTrans.cmdIns=0xCC;

        opTrans.cmdOp1=0xF0;
        opTrans.cmdOp2=0x00;
        opTrans.lendataSend=lencmd;
        System.arraycopy(apducmd,0,opTrans.dataSend,0,lencmd);
        iret=CApi.CmdOp(opTrans,devid);
        if(iret!=0)
            return null;
        if(opTrans.lenRecv>0) {
            byte[] tmprecv = new byte[opTrans.lenRecv];
            System.arraycopy(opTrans.recvBuf, 0, tmprecv, 0, opTrans.lenRecv);
            return tmprecv;
        }
        return null;
    }
    public byte[]selectCmd(byte[]fileid,int devid)
    {
        int iret=0;
        byte []tmpbuf=new byte[256];
        int index=0;
        tmpbuf[index++]=0x00;
        tmpbuf[index++]= (byte) 0xA4;
        tmpbuf[index++]=0x02;
        tmpbuf[index++]=0x00;
        tmpbuf[index++]= (byte) fileid.length;
        System.arraycopy(fileid,0,tmpbuf,index,fileid.length);
        index+=fileid.length;

        tmpbuf[index++]=0x00;

        return apduExchange(tmpbuf,index,devid);
    }
    public byte[]readRecord(int indexrecord,int lenread,int devid)
    {
        int iret=0;
        byte []tmpbuf=new byte[256];
        int index=0;
        tmpbuf[index++]=0x00;
        tmpbuf[index++]= (byte) 0xB2;
        tmpbuf[index++]= (byte) indexrecord;
        tmpbuf[index++]=0x00;
        tmpbuf[index++]= (byte) lenread;
        return apduExchange(tmpbuf,index,devid);
    }
    /*
    获取卡片记录数据
     */
    public byte[]getPiccCardData(byte[]aid,int recordIndex,int lenread,int devid)
    {
        byte[]tmp=selectCmd(aid,devid);
        if(tmp==null)
            return null;
        byte[]tmpRecord=readRecord(recordIndex,lenread,devid);
        return tmpRecord;
    }
}
