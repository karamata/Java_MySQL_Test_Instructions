package com.ef.model;

import java.io.Serializable;

public class IPRequestCounting implements Serializable {
    private String ip;
    private Long threshold;

    public IPRequestCounting(final String ip, final Long threshold) {
        this.ip = ip;
        this.threshold = threshold;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(final Long threshold) {
        this.threshold = threshold;
    }
}
