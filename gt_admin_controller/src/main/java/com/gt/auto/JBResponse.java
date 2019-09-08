package com.gt.auto;

import java.util.List;

/**
 * Created by lx on 17-1-16.
 */
public class JBResponse {

    private String max;

    private String min;

    private String sum;

    private List<String> d;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public List<String> getD() {
        return d;
    }

    public void setD(List<String> d) {
        this.d = d;
    }
}
