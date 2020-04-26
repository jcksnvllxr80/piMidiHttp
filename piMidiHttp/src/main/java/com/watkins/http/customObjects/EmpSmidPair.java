package com.watkins.http.customObjects;

import java.util.Map;
import java.util.stream.Collectors;

public class EmpSmidPair {
    private Map<SmIdAddress, EmpAddress> empSmidPair;

    public EmpSmidPair() {
    }


    public EmpSmidPair(Map<SmIdAddress, EmpAddress> empSmidPair) {
        this.empSmidPair = empSmidPair;
    }


    public void setEmpSmidPair(Map<SmIdAddress, EmpAddress> empSmidPair) {
        this.empSmidPair = empSmidPair;
    }


    public Map<SmIdAddress, EmpAddress> getEmpSmidPair() {
        return this.empSmidPair;
    }


    @Override
    public String toString() {
        return this.empSmidPair.entrySet().stream().map(pair -> "{SMID: " + pair.getKey() + ", EMP Address: " + pair.getValue() + "}").collect(Collectors.joining(" "));
    }
}
