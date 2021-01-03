package com.achilles.wild.server.model.query;

import com.achilles.wild.server.model.query.base.BaseQuery;

public class ParamsQuery extends BaseQuery {

    private  String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
