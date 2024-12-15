package com.fei.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.fei.stock.mapper.StockBlockRtInfoMapper;
import com.fei.stock.mapper.StockBusinessMapper;
import com.fei.stock.mapper.StockMarketIndexInfoMapper;
import com.fei.stock.mapper.StockRtInfoMapper;
import com.fei.stock.pojo.entity.StockBlockRtInfo;
import com.fei.stock.pojo.entity.StockMarketIndexInfo;
import com.fei.stock.pojo.entity.StockRtInfo;
import com.fei.stock.pojo.vo.StockInfoConfig;
import com.fei.stock.service.StockTimerTaskService;
import com.fei.stock.utils.DateTimeUtil;
import com.fei.stock.utils.IdWorker;
import com.fei.stock.utils.ParseType;
import com.fei.stock.utils.ParserStockInfoUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockTimerTaskServiceImpl implements StockTimerTaskService
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    private HttpEntity<Object> httpEntity;

    @PostConstruct
    public void initHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("referer", "https://finance.sina.com.cn/stock/");
        httpHeaders.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0");
        this.httpEntity = new HttpEntity<>(httpHeaders);
    }
    @Override
    public void getInnerMarketInfo()
    {
        //采集原始数据
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("referer", "https://finance.sina.com.cn/stock/");
//        httpHeaders.add("user-agent",
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0");
//        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        //发送请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int responseStatusCode = response.getStatusCodeValue();
        if (responseStatusCode != 200)
        {
            //请求失败
            log.error("{}:请求新浪股票数据失败:http状态码: {}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), responseStatusCode);
        }
        //获取js格式数据
        String responseBody = response.getBody();
        log.info("{}:请求新浪股票 数据:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), responseBody);
        //通过正则表达式处理字符串
        String reg = "var hq_str_(.+)=\"(.+)\";";
        //编译表达式,获取编译对象
        Pattern pattern = Pattern.compile(reg);
        //匹配字符串
        Matcher matcher = pattern.matcher(responseBody);
        ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
        //判断是否有匹配的数值
        while (matcher.find())
        {
            //获取大盘的code
            String marketCode = matcher.group(1);
            //获取其它信息，字符串以逗号间隔
            String otherInfo = matcher.group(2);
            //以逗号切割字符串，形成数组
            String[] splitArr = otherInfo.split(",");
            //大盘名称
            String marketName = splitArr[0];
            //获取当前大盘的开盘点数
            BigDecimal openPoint = new BigDecimal(splitArr[1]);
            //前收盘点
            BigDecimal preClosePoint = new BigDecimal(splitArr[2]);
            //获取大盘的当前点数
            BigDecimal curPoint = new BigDecimal(splitArr[3]);
            //获取大盘最高点
            BigDecimal maxPoint = new BigDecimal(splitArr[4]);
            //获取大盘的最低点
            BigDecimal minPoint = new BigDecimal(splitArr[5]);
            //获取成交量
            Long tradeAmt = Long.valueOf(splitArr[8]);
            //获取成交金额
            BigDecimal tradeVol = new BigDecimal(splitArr[9]);
            //时间
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
            //组装entity对象
            StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(marketCode)
                    .marketName(marketName)
                    .curPoint(curPoint)
                    .openPoint(openPoint)
                    .preClosePoint(preClosePoint)
                    .maxPoint(maxPoint)
                    .minPoint(minPoint)
                    .tradeVolume(tradeVol)
                    .tradeAmount(tradeAmt)
                    .curTime(curTime)
                    .build();
            //收集封装的对象，方便批量插入
            list.add(info);
        }
        //    log.info("");
        //将数据插入mybatis数据库
        int count = stockMarketIndexInfoMapper.insertByList(list);
        if (count > 0)
        {
            log.info("{}: 插入数据成功,数据:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), list);
            //大盘采集完成后通知backend刷新缓存
            //发送日期对象,接收方通过日期对比判断延迟时长
            rabbitTemplate.convertAndSend("stockExchange","inner.market",new Date());

        } else
        {
            log.error("{}: 插入数据失败:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), list);
        }
    }

    @Override
    public void getStockRtIndex()
    {
        //获取所有个股集合
        List<String> allSockCode = stockBusinessMapper.getAllSockCode();
        System.out.println(allSockCode);
        allSockCode = allSockCode.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code)
                .collect(Collectors.toList());

        Lists.partition(allSockCode, 10).forEach(codes->{
//            String url=stockInfoConfig.getMarketUrl()+String.join(",",codes);
////            HttpHeaders httpHeaders = new HttpHeaders();
////            httpHeaders.add("referer", "https://finance.sina.com.cn/stock/");
////            httpHeaders.add("user-agent",
////                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0");
////            HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//            int responseStatusCode = response.getStatusCodeValue();
//            if (responseStatusCode != 200)
//            {
//                //请求失败
//                log.error("{}:请求新浪个股数据失败:http状态码: {}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), responseStatusCode);
//            }
//            String responseBody = response.getBody();
//            List<StockRtInfo> stockRtList = parserStockInfoUtil.parser4StockOrMarketInfo(responseBody, ParseType.ASHARE);
//            log.info("采集个股数据: {}",stockRtList);
//            int count=stockRtInfoMapper.insertByList(stockRtList);
//            if (count > 0)
//            {
//                log.info("{}: 插入个股数据成功,数据:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockRtList);
//            } else
//            {
//                log.error("{}: 插入数据失败:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockRtList);
//            }
            //引入线程池
            threadPoolTaskExecutor.execute(()->{
                String url=stockInfoConfig.getMarketUrl()+String.join(",",codes);

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                int responseStatusCode = response.getStatusCodeValue();
                if (responseStatusCode != 200)
                {
                    //请求失败
                    log.error("{}:请求新浪个股数据失败:http状态码: {}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), responseStatusCode);
                }
                String responseBody = response.getBody();
                List<StockRtInfo> stockRtList = parserStockInfoUtil.parser4StockOrMarketInfo(responseBody, ParseType.ASHARE);
                log.info("采集个股数据: {}",stockRtList);
                int count=stockRtInfoMapper.insertByList(stockRtList);
                if (count > 0)
                {
                    log.info("{}: 插入个股数据成功,数据:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockRtList);
                } else
                {
                    log.error("{}: 插入数据失败:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockRtList);
                }
            });

        });

    }

    /**
     * 采集板块信息
     */
    @Override
    public void getStockBlockInfo()
    {
        String url="https://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int responseStatusCode = response.getStatusCodeValue();
        if (responseStatusCode != 200)
        {
            //请求失败
            log.error("{}:请求新浪板块数据失败:http状态码: {}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), responseStatusCode);
        }
        String responseBody = response.getBody();
        List<StockBlockRtInfo> stockBlockRtInfos = parserStockInfoUtil.parse4StockBlock(responseBody);
        int count=stockBlockRtInfoMapper.insertByList(stockBlockRtInfos);
        if (count > 0)
        {
            log.info("{}: 插入大盘数据成功,数据:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockBlockRtInfos);
        } else
        {
            log.error("{}: 插入数据失败:{}", DateTime.now().toString("yyyy-MM-dd: HH:mm:ss"), stockBlockRtInfos);
        }


    }


}
