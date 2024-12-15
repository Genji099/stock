package com.fei.stock.service;

import com.fei.stock.pojo.entity.SysUser;
import com.fei.stock.vo.req.LoginReqVo;
import com.fei.stock.vo.resp.LoginRespVo;
import com.fei.stock.vo.resp.Rest;

import java.util.Map;

/**
 *
 */
public interface UserService
{
    SysUser findByUserName(String userName);

    Rest<LoginRespVo> login(LoginReqVo vo);

    Rest<Map> getCaptchaCode();
}
