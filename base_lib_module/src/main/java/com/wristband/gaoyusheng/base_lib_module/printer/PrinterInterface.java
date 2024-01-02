package com.wristband.gaoyusheng.base_lib_module.printer;

import android.graphics.Bitmap;

/**
 * Created by gaoyusheng on 17/8/23.
 */

public interface PrinterInterface {
    // 清除缓存
    void clean();
    // 打开打印机串口
    boolean openPrinter(String port);

    // 关闭打印机串口
    boolean closePrinter();

    // 存储图片
    void storeBitmap(Bitmap bitmap);

    // 打印存储的图片
    void printStoreBitmap();

    // 打印图片
    void printBitmap(Bitmap bitmap, int marginLeft, int marginTop);

    // 粗体
    void setBold(boolean isBold);

    // 下划线高度
    void setUnderLineHeight(int height);

    void setDoubleWidthSize();

    void setExpandWidth();

    // 加宽还原
    void restoreWidthSize();

    //换行
    void newLine();

    //对齐方式 左 中 右
    void setAlignment(int position);

    // 切纸
    void cutPaper();

    // 打印文字
    void printText(String str);


}
