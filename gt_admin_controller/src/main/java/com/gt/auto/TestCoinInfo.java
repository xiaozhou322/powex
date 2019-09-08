package com.gt.auto;

/**
 * Created by lx on 17-1-18.
 */
public enum TestCoinInfo {
    // 比特币
    BTC(1,5,0.0005,0.0001,2),
    // 莱特币
    LTB(3,0.2,0.005,0.001,8);

    // 货币类型id
    private int code;
    // 人民币最小下限
    private double rmb;
    // 虚拟币最小下线
    private double xnb;
    // 挂单比例
    private double bl;
    // 撤单分钟数
    private int minutes;

    TestCoinInfo(int code, double rmb, double xnb,double bl,int minutes){
        this.code = code;
        this.rmb = rmb;
        this.xnb = xnb;
        this.bl = bl;
        this.minutes = minutes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getRmb() {
        return rmb;
    }

    public void setRmb(double rmb) {
        this.rmb = rmb;
    }

    public double getXnb() {
        return xnb;
    }

    public void setXnb(double xnb) {
        this.xnb = xnb;
    }

    public double getBl() {
        return bl;
    }

    public void setBl(double bl) {
        this.bl = bl;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public static TestCoinInfo getCoinInfo(int code){

        for (TestCoinInfo temp : TestCoinInfo.values()){
            if (code == temp.getCode()){
                return temp;
            }
        }
        return null;
    }
}
