package com.ywg.androidcommon.widget.QuickIndexBar;

public class ConatactModel {

    /**
     * 姓名
     */
    private String name;
    /**
     *
     */
    private String pinYin;
    private String firstPinYin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getFirstPinYin() {
        return firstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        this.firstPinYin = firstPinYin;
    }

    public String toString() {
        return "姓名：" + getName() + "   拼音：" + getPinYin() + "    首字母："
                + getFirstPinYin();

    }
}