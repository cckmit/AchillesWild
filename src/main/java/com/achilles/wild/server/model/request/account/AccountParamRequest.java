package com.achilles.wild.server.model.request.account;

import com.achilles.wild.server.model.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.achilles.wild.server.model.request.account.AccountParamRequest", description = "新增账户参数")
public class AccountParamRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 5814282994311417228L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    private Integer count;

    private Integer accountType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
