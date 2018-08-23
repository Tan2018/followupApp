package com.ry.fu.esb.test;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private static final long serialVersionUID = -632646058725071073L;

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
