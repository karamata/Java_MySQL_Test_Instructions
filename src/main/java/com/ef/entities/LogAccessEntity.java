package com.ef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "log_access")
public class LogAccessEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_date")
    private Date logDate;

    @Column(name = "ip")
    private String ip;

    @Column(name = "request")
    private String request;

    @Column(name = "status")
    private Integer status;

    @Column(name = "user_agent")
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(final Date logDate) {
        this.logDate = logDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(final String request) {
        this.request = request;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    public static LogAccessEntity create() {
        return new LogAccessEntity();
    }


    public LogAccessEntity withLogDate(final Date logDate) {
        this.logDate = logDate;
        return this;
    }

    public LogAccessEntity withIp(final String ip) {
        this.ip = ip;
        return this;
    }

    public LogAccessEntity withRequest(final String request) {
        this.request = request;
        return this;
    }

    public LogAccessEntity withStatus(final Integer status) {
        this.status = status;
        return this;
    }

    public LogAccessEntity withUserAgent(final String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
}
