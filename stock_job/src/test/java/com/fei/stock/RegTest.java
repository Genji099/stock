package com.fei.stock;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class RegTest
{
    @Test
    public void testRep2(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?\n" +
                "123456";
        String pattern = "(\\D*)(\\d+)(.*)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        }
    }
    @Test
    public void fun1()
    {
        String data=
                "var hq_str_sh000001=\"上证指数,3370.9405,3368.8553,3404.0764,3418.2608,3364.2061,0,0,698186572,685981058114,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2024-12-06,15:30:39,00,\";";
        String reg="var hq_str_(.+)=\"(.+)\";";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(data);
        while (matcher.find())
        {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
    @Test
    public void fun2()
    {
        String data=
                "var S_Finance_bankuai_sinaindustry = {\n" +
                "\"new_blhy\":\"new_blhy,玻璃行业,19,19.293684210526,-0.17052631578947,-0.87610188740468,315756250,5258253314,sh600586,3.464,9.260,0.310,金晶科技\",\n" +
                "\"new_cbzz\":\"new_cbzz,船舶制造,8,12.15875,0.0125,0.10291242152928,214866817,2282104956,sh600150,0.978,24.790,0.240,中国船舶\",\n" +
                "}";
        String[] split = data.split("=");
        System.out.println(split[1]);
        Map<String, String> map = JSON.parseObject(split[1], Map.class);
        System.out.println(map);
        Collection<String> values = map.values();


    }
}
