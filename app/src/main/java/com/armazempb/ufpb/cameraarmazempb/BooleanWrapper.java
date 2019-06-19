package com.armazempb.ufpb.cameraarmazempb;

public class BooleanWrapper {
    public boolean value;
    public BooleanWrapper(boolean value) {
        this.value = value;
    }
    public void invert() {
        this.value = !this.value;
    }
}
