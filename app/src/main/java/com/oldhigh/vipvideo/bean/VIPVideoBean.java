package com.oldhigh.vipvideo.bean;


/**
 * 描述：
 *
 * @author 老高
 * @date 2019/8/17
 */
public class VIPVideoBean {
    /**
     * name : 老男孩
     * type : 台剧
     * address : https:zcxczx
     */

    private String name;
    private String address;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
