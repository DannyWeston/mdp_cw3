package com.dannyweston.mdp_cw3.viewmodels;

public class Action {
    public static final Action RESET_ACTION = new Action(0);
    private final long _value;

    public Action(long value){
        this._value = value;
    }

    public long getValue(){
        return _value;
    }
}
