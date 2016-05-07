package com.apps4j.javatests.iteration;

class Person {
    private String givenName;
    private String surName;
    private int age;
    private String address;
    private String eMail;
    private String phone;

    public Person(String givenName, String surName, int age, String address, String eMail, String phone) {
        this.givenName = givenName;
        this.surName = surName;
        this.age = age;
        this.address = address;
        this.eMail = eMail;
        this.phone = phone;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
