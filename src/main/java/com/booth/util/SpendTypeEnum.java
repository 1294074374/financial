package com.booth.util;

/**
 * @author laijunlin
 * @date 2021-02-01 11:35
 */
public enum SpendTypeEnum {
    RESTAURANT(1,"餐饮美食"),
    TOP_UP_PAYMENT(2,"充值缴费"),
    CLOTHES(3,"服装衣服"),
    TRANSPORTATION(4,"交通出行"),
    BEAUTY_SALON(5,"美容美发"),
    DAILY_PROVISIONS(6,"日用百货"),
    LIVE_SERVICE(7,"生活服务"),
    REFUND(8,"退款"),
    WECHAT_TRANSFER(9,"微信转账"),
    TRANSFER_RED_ENVELOPE(10,"转账红包"),
    OTHER(11,"其他");


    private int type;
    private String typeName;

    SpendTypeEnum(int type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
