package com.wristband.gaoyusheng.base_lib_module.card_machine;

/**
 * Created by gaoyusheng on 17/3/21.
 */

public class LockEntity {

    public static final int BLOCK_BYTE_SIZE = 16;

    // block count
    private int blockCount;
    // extra sector count
    private int extraSectorCount;
    // extra sector number 额外扇区地址(0~15)
    private int extraSectorNo;
    // card extra write data
    private byte[] extraData;
    // card int extraSectorBlockMask 额外扇区需要写入哪几块的Mask(bit0-block0, bit1-block1, bit2-block2)
    private int extraSectorBlockMask;
    // card type A/B
    private byte cardType;
    // card read key
    private byte[] readKey;
    // card read start block
    private byte startBlock;
    // card read sector
    private byte sector;
    // card write data
    private byte[] writeData;
    // card write key
    private byte[] writeKey;
    // absolute block position
    private byte absoluteBlock;
    // card sn
    private String cardSn;

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public byte getCardType() {
        return cardType;
    }

    public void setCardType(byte cardType) {
        this.cardType = cardType;
    }

    public byte[] getReadKey() {
        return readKey;
    }

    public void setReadKey(byte[] readKey) {
        this.readKey = readKey;
    }

    public byte getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(byte startBlock) {
        this.startBlock = startBlock;
    }

    public byte getSector() {
        return sector;
    }

    public void setSector(byte sector) {
        this.sector = sector;
    }

    public byte[] getWriteData() {
        return writeData;
    }

    public void setWriteData(byte[] writeData) {
        this.writeData = writeData;
    }

    public byte[] getWriteKey() {
        return writeKey;
    }

    public void setWriteKey(byte[] writeKey) {
        this.writeKey = writeKey;
    }

    public byte getAbsoluteBlock() {
        return absoluteBlock;
    }

    public void setAbsoluteBlock(byte absoluteBlock) {
        this.absoluteBlock = absoluteBlock;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public int getExtraSectorCount() {
        return extraSectorCount;
    }

    public void setExtraSectorCount(int extraSectorCount) {
        this.extraSectorCount = extraSectorCount;
    }

    public byte[] getExtraData() {
        return extraData;
    }

    public void setExtraData(byte[] extraData) {
        this.extraData = extraData;
    }

    public int getExtraSectorNo() {
        return extraSectorNo;
    }

    public void setExtraSectorNo(int extraSectorNo) {
        this.extraSectorNo = extraSectorNo;
    }

    public int getExtraSectorBlockMask() {
        return extraSectorBlockMask;
    }

    public void setExtraSectorBlockMask(int extraSectorBlockMask) {
        this.extraSectorBlockMask = extraSectorBlockMask;
    }
}
