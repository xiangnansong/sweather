package com.example.song.sweathor.model;

/**
 * Created by song on 15-2-13.
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String name) {
        provinceName = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String code) {
        provinceCode = code;
    }
}
