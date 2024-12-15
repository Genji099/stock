package com.fei.stock.mapper;

import com.fei.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 59747
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 通过名称查询用户信息
     * @param userName
     * @return
     */
    SysUser findUserByUserName(@Param("userName") String userName);

    /**
     * 查找所有用户
     * @return
     */
    List<SysUser> findAllUser();

}
