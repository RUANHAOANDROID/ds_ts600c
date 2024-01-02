package com.wristband.gaoyusheng.base_lib_module.pos;

import android.content.Context;

/**
 * Created by gaoyusheng on 17/5/9.
 */

public interface PosInterface {
    // init with context
    void init(Context context);

    // pos sign in, input result action
    void signIn(PosCallBackSubscriber<PosResponse> action1);

    // amount is price
    void preAuth(String amount, PosCallBackSubscriber<PosResponse> action1);

    void preAuthUndo(String amount, String transDate, String authNumber, String traceNo, String cardNumber, String expireDate, PosCallBackSubscriber<PosResponse> action1);

    void authComplete(String amount, String hostSerialNumber, String transDate /* MMDD */, String authNumber,
                      String cardNumber, String expireDate /* MMDD */, PosCallBackSubscriber<PosResponse> action1);

    void authCompleteUndo(String amount, String traceNo, String hostSerialNumber, String transDate /* MMDD */, String authNumber,
                          String cardNumber, String expireDate /* MMDD */, PosCallBackSubscriber<PosResponse> action1);

    void consume(String amount, PosCallBackSubscriber<PosResponse> action1);

    void consumeUndo(String amount, String traceNo, String refNo, PosCallBackSubscriber<PosResponse> action1);

    void settlement(PosCallBackSubscriber<PosResponse> action1);

    void balance(PosCallBackSubscriber<PosResponse> action1);

    void help(PosCallBackSubscriber<PosResponse> action1);

    String getSettlementString();

    String getSourceFilePath();

    String generateUploadString(String content);

    String[] splitMerchantGuestString(String content);

    // 100 -> 中银 1.0 通联？
    String getRealAmountFromBankTypeAmount(String inputAmount);
}
