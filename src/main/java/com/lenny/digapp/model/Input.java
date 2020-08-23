package com.lenny.digapp.model;

import java.util.ArrayList;
import java.util.List;

public class Input {

    private String username;
    private List<Double> values = new ArrayList<>();
    private Integer amountOfGroups;
    private Integer size;

    public Input() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public Integer getAmountOfGroups() {
        return amountOfGroups;
    }

    public void setAmountOfGroups(Integer amountOfGroups) {
        this.amountOfGroups = amountOfGroups;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
