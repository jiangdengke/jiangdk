package com.jiang.pojo.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private String code;//状态码
    private String msg;//返回信息
    private T data;//返回时携带的数据
    public static Result success(){
        Result result = new Result();
        result.setCode("200");
        result.setMsg("success");
        return result;
    }
    public static<T> Result<T> success(T data){//静态方法使用泛型，需要在static后加<T>
        Result result = new Result();
        result.setCode("200");
        result.setMsg("success");
        result.setData(data);
        return result;
    }
    public static Result error(String code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static<T> Result<T> error(String code,String msg,T data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}