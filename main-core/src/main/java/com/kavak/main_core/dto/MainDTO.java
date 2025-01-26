package com.kavak.main_core.dto;

import java.io.Serializable;

public abstract class MainDTO <Key extends Object> implements Serializable {
    protected Key id;
    private boolean enabled;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
