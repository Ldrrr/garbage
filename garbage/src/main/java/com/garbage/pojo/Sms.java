package com.garbage.pojo;

public class Sms {
    String phone;
    String code;
    int min;

    public Sms(String phone, String code, int min) {
        this.phone = phone;
        this.code = code;
        this.min = min;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", min=" + min +
                '}';
    }
}
