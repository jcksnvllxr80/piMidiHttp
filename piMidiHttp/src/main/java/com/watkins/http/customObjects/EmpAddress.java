package com.watkins.http.customObjects;

public class EmpAddress {
    private String empAddress;

    public EmpAddress() {
    }


    public EmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }


    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }


    public String getEmpAddress() {
        return this.empAddress;
    }


    @Override
    public String toString() {
        return this.empAddress;
    }
}
