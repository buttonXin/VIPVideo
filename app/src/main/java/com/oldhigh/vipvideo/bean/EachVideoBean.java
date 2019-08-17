package com.oldhigh.vipvideo.bean;

/**
 * 描述：单个链接的类
 *
 * @author 老高
 * @date 2019/8/17
 */
public class EachVideoBean {


    /**
     * 视频地址
     */
    private String address;
    /**
     * 视频集数
     */
    private String grade;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "EachVideoBean{" +
                "address='" + address + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
