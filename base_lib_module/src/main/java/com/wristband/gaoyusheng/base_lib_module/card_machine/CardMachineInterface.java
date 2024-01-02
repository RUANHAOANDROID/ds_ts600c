package com.wristband.gaoyusheng.base_lib_module.card_machine;

/**
 * Created by gaoyusheng on 17/5/12.
 */

public interface CardMachineInterface {
    CardDriverResponse getStatus();

    CardDriverResponse readCardInfo(LockEntity entity, String cardSn);

    CardDriverResponse readCardId();

    CardDriverResponse writeCardData(LockEntity entity);

    CardDriverResponse connect(String port);

    CardDriverResponse disconnect();

    CardDriverResponse moveCardToReadCardPosition();

    CardDriverResponse moveCardToHolder();

    CardDriverResponse moveCardToMouth();

    CardDriverResponse reset();

    CardDriverResponse receiveCard();

    CardDriverResponse receiveCardToReceiveCardBox();

    String getDefaultPort();

    byte getATypeByte();

    byte getBTypeByte();
}
