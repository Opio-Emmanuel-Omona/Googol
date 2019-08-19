package com.example.googol;

public class ButtonModel {
    private String value;
    private int hiddenValue;

    public ButtonModel(String value, int hiddenValue) {
        this.value = value;
        this.hiddenValue = hiddenValue;
    }

    public String getValue() {
        return value;
    }

    public int getHiddenValue() {
        return hiddenValue;
    }

}
