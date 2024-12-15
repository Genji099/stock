package com.fei.stock.controller;

import com.fei.stock.pojo.entity.SysUser;
import com.fei.stock.service.UserService;
import com.fei.stock.vo.req.LoginReqVo;
import com.fei.stock.vo.resp.LoginRespVo;
import com.fei.stock.vo.resp.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "用户处理器")
@ResponseBody
@Controller
@RequestMapping("/api")
public class UserController
{
    @Autowired
    private UserService userService;
    /**
     * 根据用户名称查询用户信息
     * @param userName
     * @return
     */
    @ApiOperation(value = "根据用户名查询用户信息")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "userName",value = "用户名称",required = true,type ="path" )})
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName)
    {
        return userService.findByUserName(userName);
    }

    @ApiOperation(value="用户登录功能")
    @PostMapping("/login")
    public Rest<LoginRespVo> login(@RequestBody LoginReqVo vo)
    {

        return userService.login(vo);
    }

    @ApiOperation(value="验证码生成器")
    @GetMapping("/captcha")
    public Rest<Map> getCaptchaCode()
    {
        return userService.getCaptchaCode();
    }



}
