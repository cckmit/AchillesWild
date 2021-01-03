package com.achilles.wild.server.enums.account;

import com.achilles.wild.server.enums.DescedEnum;
import com.achilles.wild.server.enums.NumbericEnum;

/**
 * inter account
 */
public enum AccountInterTypeEnum implements DescedEnum, NumbericEnum {


    PROVISION_ACCOUNT(0,"备付金"),

    COLLECTION_ACCOUNT(3,"收款账户"),

    PAY_ACCOUNT(4,"付款账户");


    private Integer value;
    private String desc;

    AccountInterTypeEnum(Integer value, String desc) {
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
