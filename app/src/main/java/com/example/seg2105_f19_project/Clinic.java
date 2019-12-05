package com.example.seg2105_f19_project;

public class Clinic {

    private String name;
    private String address;
    private String phone;
    private String insurance;
    private String payment;
    private String services;

    public Clinic (String name, String address, String phone, String insurance, String payment, String services) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.insurance = insurance;
        this.payment = payment;
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getPayment() {
        return payment;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

}
