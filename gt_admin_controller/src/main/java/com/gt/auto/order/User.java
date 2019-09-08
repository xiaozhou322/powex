package com.gt.auto.order;

import java.util.HashMap;
import java.util.Map;

import com.gt.auto.TestCoinInfo;

/**
 * Created by lx on 17-1-19.
 */
public class User {
    // 用户id
    private int uid;
    // 虚拟币金额
    private double xnb;
    // 人民币金额
    private double rmb;

    private double xnbFrozen;

    private double rmbFrozen;
    // 用户的状态
    private Integer orderTye;

    private Map<Integer,Boolean> statusMap = new HashMap<Integer, Boolean>();

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getXnb() {
        return xnb;
    }

    public void setXnb(double xnb) {
        this.xnb = xnb;
    }

    public double getRmb() {
        return rmb;
    }

    public void setRmb(double rmb) {
        this.rmb = rmb;
    }

    public double getXnbFrozen() {
        return xnbFrozen;
    }

    public void setXnbFrozen(double xnbFrozen) {
        this.xnbFrozen = xnbFrozen;
    }

    public double getRmbFrozen() {
        return rmbFrozen;
    }

    public void setRmbFrozen(double rmbFrozen) {
        this.rmbFrozen = rmbFrozen;
    }

    public Integer getOrderTye() {
        return orderTye;
    }

    public void setOrderTye(Integer orderTye) {
        this.orderTye = orderTye;
    }

    /**
     * 初始化状态
     * @param coinInfo
     */
    public void initStatusMap(TestCoinInfo coinInfo){
        this.statusMap.clear();
        Boolean xnbFlag = xnb > coinInfo.getXnb()?Boolean.TRUE:Boolean.FALSE;
        Boolean rmbFlag = rmb > coinInfo.getRmb()?Boolean.TRUE:Boolean.FALSE;
        statusMap.put(OrderType.sell.getCode(),xnbFlag);
        statusMap.put(OrderType.buy.getCode(),rmbFlag);
        Boolean errorFlag = Boolean.FALSE;
        if (!xnbFlag && !rmbFlag){
            errorFlag = Boolean.TRUE;
        }
        statusMap.put(OrderType.error.getCode(),errorFlag);
    }

    /**
     * 取第一个可以够状态
     * @return
     */
    private Integer getMapValue(){
        return getMapValue(null);
    }

    /**
     * 取认购状态
     * @param no
     * @return
     */
    public Integer getMapValue(Integer no){
        for (Map.Entry<Integer,Boolean> entry : statusMap.entrySet()){
            if (entry.getValue()){
                if (no == null || no.intValue() != entry.getKey().intValue()){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 加载用户的状态
     * @param last
     */
    public void loadStatus(User last){
        if (this.orderTye == null){
            // 初始化用户的方向
            OrderType orderType = last == null?OrderType.sell:OrderType.buy;
            this.orderTye = new Integer(orderType.getCode());
        }
        Integer temp  = statusMap.get(orderTye)?orderTye:getMapValue();
        if (last != null){
            if (temp.intValue() == last.getOrderTye().intValue()){
                Integer next = getMapValue(temp);
                if (next == null){
                    // 只能改变last的数据
                    Integer lastTemp = last.getMapValue(last.getOrderTye());
                    if (lastTemp != null){
                        last.setOrderTye(lastTemp);
                    }
                } else {
                    temp = next;
                }
            }
        }
        this.orderTye = temp;
        /*Integer temp = last==null?getMapValue():getMapValue(last.getOrderTye());
        if (temp == null){

        }*/
        /*if (!statusMap.get(orderTye)){
            // 当前的状态不可以使用,这需要调整
            Integer temp = getMapValue();
            if (last != null){
                if (last.getOrderTye().intValue() == temp.intValue()){
                    modifyLast(last,temp);
                }
            }
            this.orderTye = temp;
        } else {
            if (last != null){
                if (last.getOrderTye().intValue() == orderTye.intValue()){
                    // 两个相等则取下一个状告
                    Integer temp = getMapValue(orderTye);
                    if (temp != null){
                        orderTye = temp;
                    } else {
                        modifyLast(last,orderTye);
                    }
                }

            }
        }*/
    }

    private void modifyLast(User last,Integer no){
        // 改变状态
        Integer lastTemp = last.getMapValue(no);
        if (lastTemp != null){
            last.setOrderTye(lastTemp);
        }
    }

    public Map<Integer, Boolean> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Integer, Boolean> statusMap) {
        this.statusMap = statusMap;
    }
}
