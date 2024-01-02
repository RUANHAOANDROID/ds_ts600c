package com.wristband.gaoyusheng.base_lib_module.identify_card;

import android.content.Context;

/**
 * Created by gaoyusheng on 17/8/23.
 */

public interface IDInterface {
    void setupContext(Context context);
    void openPort(String port);
    boolean findCard();
    IdentityCardEntity readIDCard();
    void stopReadCard();
    String readCardUID();
    String readM1CardUID();
    void stopReadM1CardUID();
    void reSet();
    String readQRCode();
}
