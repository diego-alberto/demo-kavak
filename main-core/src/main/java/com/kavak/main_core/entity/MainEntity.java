package com.kavak.main_core.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Calendar;

@MappedSuperclass
public abstract class MainEntity <Key extends Object> implements Serializable, Comparable<MainEntity> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private java.util.Calendar creationDate;

    private boolean enabled;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @PrePersist
    void onCreate() {
        this.setCreationDate(Calendar.getInstance());
    }

    @Override
    public int compareTo(MainEntity o) {
        return Math.toIntExact(this.getId().hashCode() - o.getId().hashCode());
    }
}
