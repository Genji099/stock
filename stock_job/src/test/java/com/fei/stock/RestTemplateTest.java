package com.fei.stock;

import com.fei.stock.pojo.Account;
import com.fei.stock.service.StockTimerTaskService;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForReadableInstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class RestTemplateTest
{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void  fun1()
    {
        String url="http://localhost:6766/account/getByUserNameAndAddress?userName=zhangsan&address=sh";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        HttpHeaders headers = entity.getHeaders();
        System.out.println(headers);
        HttpStatus statusCode = entity.getStatusCode();
        System.out.println(statusCode);
        String body = entity.getBody();
        System.out.println(body);
    }
    @Test
    public void  fun2()
    {
        String url="http://localhost:6766/account/getByUserNameAndAddress?userName=zhangsan&address=sh";
        ResponseEntity<Account> entity = restTemplate.getForEntity(url, Account.class);
        HttpHeaders headers = entity.getHeaders();
        System.out.println(headers);
        HttpStatus statusCode = entity.getStatusCode();
        System.out.println(statusCode);
        Account body = entity.getBody();
        System.out.println(body);
    }
    @Test
    public void  fun3()
    {
        String url="http://localhost:6766/account/getByUserNameAndAddress?userName=zhangsan&address=sh";

        Account forObject = restTemplate.getForObject(url, Account.class);
        System.out.println(forObject);
    }
    @Test
    public void  fun4()
    {
        if(restTemplate==null)
        {
            System.out.println("sb");
        }
    }
    @Test
    public void  fun5()
    {
        String url="http://localhost:6666/account/getHeader";
        //设置请求头参数
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","zhangsan");
        //请求头填充到请求对象下
        HttpEntity<Map> entry = new HttpEntity<>(headers);
        //发送请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entry, String.class);
        String result = responseEntity.getBody();
        System.out.println(result);

    }
    @Test
    public void  fun6()
    {
        String url="http://localhost:6766/account/addAccount";
        //设置请求头，指定请求数据方式
        HttpHeaders headers = new HttpHeaders();
        //告知被调用方，请求方式是form表单提交，这样对方解析数据时，就会按照form表单的方式解析处理
        headers.add("Content-type","application/x-www-form-urlencoded");
        //组装模拟form表单提交数据，内部元素相当于form表单的input框
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id","10");
        map.add("userName","itheima");
        map.add("address","shanghai");
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        /*
            参数1：请求url地址
            参数2：请求方式 POST
            参数3：请求体对象，携带了请求头和请求体相关的参数
            参数4：响应数据类型
         */
        ResponseEntity<Account> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Account.class);
        Account body = exchange.getBody();
        System.out.println(body);
    }
    @Test
    public void  fun7()
    {
        String url="http://localhost:6766/account/getCookie";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        //获取cookie
        List<String> cookies = result.getHeaders().get("Set-Cookie");
        //获取响应数据
        String resStr = result.getBody();
        System.out.println(resStr);
        System.out.println(cookies);
    }

}
