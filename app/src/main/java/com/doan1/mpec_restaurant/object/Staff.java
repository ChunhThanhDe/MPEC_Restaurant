package com.doan1.mpec_restaurant.object;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Staff implements Serializable {
    private Integer id;
    @SerializedName("username")
    private String account;
    private String password;
    private String name;
    private String dateOfBirth;
    private String address;
    private String sex;

    public Staff(Integer id, String account, String password, String name, String dateOfBirth, String address, String sex) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
