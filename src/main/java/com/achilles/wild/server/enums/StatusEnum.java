package com.achilles.wild.server.enums;

/**
 * ͨ��״̬
 */
public enum StatusEnum implements DescedEnum, NumbericEnum{

    DELETED(0,"DELETED"),
    NORMAL(1,"NORMAL"),
    FINALE(2,"NORMAL"),;


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
