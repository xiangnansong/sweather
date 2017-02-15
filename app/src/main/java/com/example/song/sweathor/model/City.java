package com.example.song.sweathor.model;

/**
 * Created by song on 15-2-13.
 */
public class City {
    private int id;
    private String Name;
    private String Code;
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pid;
    }

    public void setpId(int id) {
        this.pid = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
