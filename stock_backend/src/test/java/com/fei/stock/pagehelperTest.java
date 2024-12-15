package com.fei.stock;

import com.fei.stock.mapper.SysUserMapper;
import com.fei.stock.pojo.entity.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class pagehelperTest
{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Test
    public void test01()
    {
        Integer page=2;
        Integer pageSize=5;
        PageHelper.startPage(2,5);
        List<SysUser> allUser = sysUserMapper.findAllUser();
        System.out.println(allUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>();
        int pageNum = pageInfo.getPageNum();//获取当前页
        int pages = pageInfo.getPages();//获取总页数
        int pageSize1 = pageInfo.getPageSize();//每页大小
        int size = pageInfo.getSize();//获取当前页的记录数
        long total = pageInfo.getTotal();//获取总记录数
        List<SysUser> list = pageInfo.getList();//获取数据
    }
}
