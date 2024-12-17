package com.fei.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.fei.stock.constant.StockConstant;
import com.fei.stock.mapper.SysUserMapper;
import com.fei.stock.pojo.entity.SysUser;
import com.fei.stock.utils.IdWorker;
import com.fei.stock.vo.req.LoginReqVo;
import com.fei.stock.vo.resp.LoginRespVo;
import com.fei.stock.vo.resp.ResponseCode;
import com.fei.stock.vo.resp.Rest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fei.stock.service.UserService;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUser findByUserName(String userName)
    {
        return sysUserMapper.findUserByUserName(userName);
    }

    /**
     * 用户登录功能
     * @param vo
     * @return
     */
    @Override
    public Rest<LoginRespVo> login(LoginReqVo vo)
    {
        //判断参数是否合法
        if(vo==null|| StringUtils.isBlank(vo.getUsername())||StringUtils.isBlank(vo.getPassword())||StringUtils.isBlank(vo.getCode()))
        {
            return Rest.error(ResponseCode.DATA_ERROR);
        }
        //判断验证码和sessionid是否为空
        if(StringUtils.isBlank(vo.getSessionId())||StringUtils.isBlank(vo.getCode()))
        {
            return Rest.error(ResponseCode.CHECK_CODE_ERROR);
        }
        String redisCode = (String)redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        //验证sessionid的value和验证码是否相同
        if(StringUtils.isBlank(redisCode))
        {
            return Rest.error(ResponseCode.CHECK_CODE_TIMEOUT);
        }
        if(!redisCode.equalsIgnoreCase(vo.getCode()))
        {
            return Rest.error(ResponseCode.CHECK_CODE_ERROR);
        }

        //根据用户名查询用户信息
        SysUser user = sysUserMapper.findUserByUserName(vo.getUsername());
        if(user==null)
        {
            //用户不存在
            return Rest.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        if(!passwordEncoder.matches(vo.getPassword(),user.getPassword()))
        {
                return Rest.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        LoginRespVo loginRespVo = new LoginRespVo();
        BeanUtils.copyProperties(user,loginRespVo);
        //调用密码匹配器匹配输入的密码
        return Rest.ok(loginRespVo);
    }

    @Override
    public Rest<Map> getCaptchaCode()
    {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        lineCaptcha.setBackground(Color.lightGray);
        String lineCaptchaCode = lineCaptcha.getCode();
        String imageBase64 = lineCaptcha.getImageBase64();
        long l = idWorker.nextId();
        String sessionId = String.valueOf(l);
        //保存数据到redis sessionid作为key(雪花算法实现) 验证码内容作为value
        log.info("图片验证码: "+lineCaptchaCode+" 会话id: "+sessionId+" -----------------------------------" +
                "图片校验码: "+imageBase64);
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX+sessionId,lineCaptchaCode,6, TimeUnit.MINUTES);
        //组装返回到前端的数据
        Map<String,String>data=new HashMap<>();
        data.put("imageData",imageBase64);
        data.put("sessionId",sessionId);

        return Rest.ok(data);
    }
}
