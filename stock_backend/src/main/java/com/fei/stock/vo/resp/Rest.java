package com.fei.stock.vo.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回数据类
 * @JsonInclude 保证序列化json的时候,如果是null的对象,key也会消失
 * @param <T>
 */
@ApiModel(description = "返回数据类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rest<T> implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 7735505903525411467L;

    // 成功值,默认为1
    @ApiModelProperty("成功值,默认为1")
    private static final int SUCCESS_CODE = 1;
    // 失败值,默认为0
    @ApiModelProperty("失败值,默认为0")
    private static final int ERROR_CODE = 0;

    //状态码
    @ApiModelProperty("状态码")
    private int code;
    //消息
    @ApiModelProperty("消息")
    private String msg;
    //返回数据
    @ApiModelProperty("返回数据")
    private T data;

    private Rest(int code){
        this.code = code;
    }
    private Rest(int code, T data){
        this.code = code;
        this.data = data;
    }
    private Rest(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    private Rest(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Rest<T> ok(){
        return new Rest<T>(SUCCESS_CODE,"success");
    }
    public static <T> Rest<T> ok(String msg){
        return new Rest<T>(SUCCESS_CODE,msg);
    }
    public static <T> Rest<T> ok(T data){
        return new Rest<T>(SUCCESS_CODE,data);
    }
    public static <T> Rest<T> ok(String msg, T data){
        return new Rest<T>(SUCCESS_CODE,msg,data);
    }

    public static <T> Rest<T> error(){
        return new Rest<T>(ERROR_CODE,"error");
    }
    public static <T> Rest<T> error(String msg){
        return new Rest<T>(ERROR_CODE,msg);
    }
    public static <T> Rest<T> error(int code, String msg){
        return new Rest<T>(code,msg);
    }
    public static <T> Rest<T> error(ResponseCode res){
        return new Rest<T>(res.getCode(),res.getMessage());
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }
}