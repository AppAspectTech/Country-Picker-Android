package com.country.countrypickerlibrary.model;


import com.country.countrypickerlibrary.db.DBObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryData implements DBObject {

    private int country_id;

    @SerializedName("name")
    @Expose
    private String country_name;

    @SerializedName("dial_code")
    @Expose
    private String country_dial_code;

    @SerializedName("code")
    @Expose
    private String country_code;

    private String country_displayName;
    private boolean isChecked;

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_displayName() {
        return country_displayName;
    }

    public void setCountry_displayName(String country_displayName) {
        this.country_displayName = country_displayName;
    }

    public String getCountry_dial_code() {
        return country_dial_code;
    }

    public void setCountry_dial_code(String country_dial_code) {
        this.country_dial_code = country_dial_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String toString()
    {
        return country_name;
    }
}