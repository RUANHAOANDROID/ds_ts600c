package com.reader.api;


import ToBmp.Wlt;

public class ID2Parser {
    private byte[] mID2TxtRAW = new byte[256];
    private byte[] mID2PicRAW = new byte[1024];
    private byte[] mID2FPRAW = new byte[1024];
    private byte[] mID2AddRAW = new byte[73];
    static Wlt wlt = new Wlt();

    public ID2Parser() {
    }

    public ID2Parser(byte[] data, int offset) {
        decode(data, offset);
    }

    public ID2Parser(byte[] data) {
        this(data, 0);
    }


    public void decode(byte[] _raw, int offset) {
        if ((_raw[offset + 0] == 1) && (_raw[offset + 1] == 0)) {
            // 文字
            System.arraycopy(_raw, offset + 4, this.mID2TxtRAW, 0, 256);
        }
        if ((_raw[offset + 2] == 4) && (_raw[offset + 3] == 0)) {
            //照片
            System.arraycopy(_raw, offset + 260, this.mID2PicRAW, 0, 1024);

        }
        if ((_raw[offset + 4] == 4) && (_raw[offset + 5] == 0) && (_raw[offset + 1286] == 67)) {
            //指纹
            System.arraycopy(_raw, offset + 1284, this.mID2FPRAW, 0, 1024);
        }
        if (_raw.length - offset >= 2383 &&
                (_raw[offset + 2310] == 0) &&
                (_raw[offset + 2311] == 0) &&
                (_raw[offset + 2312] == -112)) {
            System.arraycopy(_raw, offset + 2310, this.mID2AddRAW, 0, 73);
        }
    }

    public ID2Txt getText() {
        return new ID2Txt(this.mID2TxtRAW);
    }

    public byte[] getPic() {
        byte[] h = new byte[38862];
        wlt.GetBmpByBuffNoLic(mID2PicRAW, h);
        return h;
    }

    public byte[] getFP_Raw() {
        return mID2FPRAW;
    }
}
