package com.ef.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ip_block")
public class IPBlockEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_block")
    private String ip;

    @Column(name = "comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public static IPBlockEntity create() {
        return new IPBlockEntity();
    }

    public IPBlockEntity withIp(final String ip) {
        this.ip = ip;

        return this;
    }

    public IPBlockEntity withComment(final String comment) {
        this.comment = comment;

        return this;
    }
}
