package com.model;


public class CustomerModel {
    private Integer id;
    private Integer division;
    private String name;
    private String address;
    private String zip;
    private String phone;
    private String country;


    public CustomerModel(Integer id, String name, String phone, String address, String postCode, Integer division, String country) {

        this.id = id;
        this.division = division;
        this.name = name;
        this.address = address;
        this.zip = postCode;
        this.phone = phone;
        this.country = country;
    }

    /*
     * Set-all Customer constructor
     *@param cId customer ID
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    /*
     *  Getter and Setter methods for Customer class
     * */



}
