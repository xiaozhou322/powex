package com.gt.auto;

/**
 * Created by lx on 17-1-17.
 */
public enum  CoinType {

    BTC(1,"btc"),
    LTC(3,"ltc");

    private int code;
    private String name;

    CoinType(int code,String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CoinType getCoinType(int code){
        CoinType result = null;
        for (CoinType coinType : CoinType.values()){
            if (coinType.getCode() == code){
                result = coinType;
                break;
            }
        }
        return result;
    }
}
