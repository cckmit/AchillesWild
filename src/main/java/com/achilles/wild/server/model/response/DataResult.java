package com.achilles.wild.server.model.response;

public class DataResult<T> extends BaseResult {

    private int count;

    private T data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataResult() {
    }

    public static <T> DataResult<T> success(T data) {
        return success(new DataResult<>(), data);
    }

    public static <T> DataResult<T> success(DataResult<T> result, T data) {
        if (result == null) {
            return success(data);
        }
        result.setData(data);
        return result;
    }

    public static <T> DataResult<T> success(T data, int count) {
        return success(new DataResult<>(), data, count);
    }

    public static <T> DataResult<T> success(DataResult<T> result, T data, int count) {
        if (result == null) {
            return success(data, count);
        }
        result.setData(data);
        result.setCount(count);
        return result;
    }

    public static <T> DataResult<T> baseFail() {
        BaseResult baseResult = BaseResult.fail();
        DataResult dataResult = new DataResult<>();
        dataResult.setSuccess(false);
        dataResult.setCode(baseResult.getCode());
        dataResult.setMessage(baseResult.getMessage());
        return dataResult;
    }

    public static <T> DataResult<T> baseFail(ResultCode resultCode) {
        DataResult dataResult = new DataResult<>();
        dataResult.setSuccess(false);
        dataResult.setCode(resultCode.code);
        dataResult.setMessage(resultCode.message);
        return dataResult;
    }

    public static <T> DataResult<T> baseFail(ResultCode resultCode, T data) {
        DataResult dataResult = DataResult.baseFail(resultCode);
        dataResult.setData(data);
        return dataResult;
    }

}