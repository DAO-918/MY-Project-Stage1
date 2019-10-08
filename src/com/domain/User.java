package com.domain;

public class User {
    private int u_id;
    private String u_username;
    private String u_password;
    private String u_sex;
    private String u_hobbies;
    private String u_phone;
    private String u_email;
    private String u_address;
    private String is_delete;

    @Override
    public String toString() {
        return "{" +
                "u_id=" + u_id +
                ", u_username='" + u_username + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_sex='" + u_sex + '\'' +
                ", u_hobbies='" + u_hobbies + '\'' +
                ", u_phone=" + u_phone +
                ", u_email='" + u_email + '\'' +
                ", u_address='" + u_address + '\'' +
                ", is_delete='" + is_delete + '\'' +
                '}';
    }

    public User() {
    }

    public User(int u_id, String u_username, String u_password, String u_sex, String u_hobbies, String u_phone, String u_email, String u_address, String is_delete) {
        this.u_id = u_id;
        this.u_username = u_username;
        this.u_password = u_password;
        this.u_sex = u_sex;
        this.u_hobbies = u_hobbies;
        this.u_phone = u_phone;
        this.u_email = u_email;
        this.u_address = u_address;
        this.is_delete = is_delete;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_hobbies() {
        return u_hobbies;
    }

    public void setU_hobbies(String u_hobbies) {
        this.u_hobbies = u_hobbies;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }
}
