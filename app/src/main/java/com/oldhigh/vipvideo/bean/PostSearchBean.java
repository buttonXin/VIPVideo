package com.oldhigh.vipvideo.bean;

public class PostSearchBean {
    /**
     * 搜索请求的字段，不要更改！
     */
    private String wd;

    public PostSearchBean(String wd) {
        this.wd = wd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }
}
