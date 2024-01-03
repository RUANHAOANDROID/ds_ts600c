package com.reader.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class IDCard extends ID2Parser {
    public IDCard() {
    }


   
    public IDCard(byte[] data, int offset) {
        super(data, offset);
    }

    public IDCard(byte[] data) {
        super(data);
    }

    public Bitmap getPicBitmap() {
        byte[] pic = getPic();
        return BitmapFactory.decodeByteArray(pic, 0, pic.length);
    }
}
