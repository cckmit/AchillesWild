package com.achilles.wild.server.enums.account;

import com.achilles.wild.server.enums.DescedEnum;
import com.achilles.wild.server.enums.NumbericEnum;

/**
 * 账户类型
 */
public enum AccountTypeEnum implements DescedEnum, NumbericEnum {


    MASTER_ACCOUNT(1,"主账户"),

    SLAVE_ACCOUNT(2,"子账户"),

    COLLECTION_ACCOUNT(3,"收款账户"),

    PAY_ACCOUNT(4,"付款账户");


    private Integer value;
    private String desc;

    AccountTypeEnum(Integer value, String desc) {
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

    public static AccountTypeEnum of(Integer value) {

        for (AccountTypeEnum type : AccountTypeEnum.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static boolean contains(Integer value){

        if(value==null){
            return false;
        }

        AccountTypeEnum[] accountTypeEnums = AccountTypeEnum.values();
        for (AccountTypeEnum accountTypeEnum:accountTypeEnums){
            if(accountTypeEnum.value.intValue()==value){
                return true;
            }
        }

        return false;
    }

    public static boolean contains(AccountTypeEnum accountTypeEnum){

        if(accountTypeEnum==null){
            return false;
        }

        AccountTypeEnum[] accountTypeEnums = AccountTypeEnum.values();
        for (AccountTypeEnum accountTypeEnu:accountTypeEnums){
            if(accountTypeEnum.equals(accountTypeEnu)){
                return true;
            }
        }

        return false;
    }
}
