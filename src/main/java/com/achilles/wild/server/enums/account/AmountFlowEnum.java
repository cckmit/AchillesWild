package com.achilles.wild.server.enums.account;

import com.achilles.wild.server.enums.DescedEnum;
import com.achilles.wild.server.enums.NumbericEnum;

public enum AmountFlowEnum implements DescedEnum, NumbericEnum {


    PLUS(1,"加"),

    MINUS(0,"减");

    private Integer value;
    private String desc;

    AmountFlowEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int toNumbericValue() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }
}
