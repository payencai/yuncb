package com.example.yunchebao.account;

import org.litepal.crud.LitePalSupport;

public class MyAccount extends LitePalSupport {
    private String phone;
    private String password;
    private String head;
    private boolean isCurrent;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}
