package com.watkins.http.customObjects;

public class Part {
    private String empAddress;

    public Part() {
    }


    public Part(String empAddress) {
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
