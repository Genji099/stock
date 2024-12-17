package com.fei.stock.vo.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@ApiModel(description = "登录后响应前端的vo")
@Data
@Builder
public class LoginMenusRespVo
{
    /**
     * 在前端中long类型数据过长会导致数据失真 英雌我们在传递的过程中需要将数据转化为String类型
     */
    //用户ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //用户名称
    private String username;
    //手机号
    private String phone;
    //昵称
    private String nickName;
    //真实名称
    private String realName;
    //性别
    private Integer sex;
    //状态
    private Integer status;
    //邮件
    private String email;
    //侧边栏权限树
    private List menus;
    //按钮权限标识
    private List permissions;
}
