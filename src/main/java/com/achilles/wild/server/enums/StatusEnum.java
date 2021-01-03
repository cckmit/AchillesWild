package com.achilles.wild.server.enums;

/**
 * ͨ��״̬
 */
public enum StatusEnum implements DescedEnum, NumbericEnum{


    NORMAL(1,"����"),

    ABNORMAL(2,"������");


    private Integer value;
    private String desc;

    StatusEnum(Integer value, String desc) {
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
