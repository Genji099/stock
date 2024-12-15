package com.fei.stock;

import com.alibaba.excel.EasyExcel;
import com.fei.stock.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelTest
{
    public List<User> init(){
        //组装数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAddress("上海"+i);
            user.setUserName("张三"+i);
            user.setBirthday(new Date());
            user.setAge(10+i);
            users.add(user);
        }
        return users;
    }
    @Test
    public void fun1()
    {
        List<User> userList = this.init();
        //到处到excel上
        EasyExcel.write("C:\\Users\\59747\\Desktop\\user.xls",User.class)
                .sheet("用户信息").doWrite(userList);
    }
}
