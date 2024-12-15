package com.fei.stock.mq;

import com.fei.stock.service.StockService;
import com.fei.stock.service.impl.StockServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class StockMQMsgListener
{
    @Autowired
    private Cache<String,Object> caffeineCache;

    @Autowired
    private StockService stockService;
    @RabbitListener(queues = "innerMarketQueue")
    public void refreshInnerMarketInfo(Date startTime)
    {
        //计算接受与发生时间的差值,如果超过一分钟则告警
        //获取时间毫秒差值
        long diffTime= DateTime.now().getMillis()-new DateTime(startTime).getMillis();
        if (diffTime>60000) {
            log.error("采集国内大盘时间点：{},同步超时：{}ms",new DateTime(startTime).toString("yyyy-MM-dd HH:mm:ss"),diffTime);
        }
        //刷新缓存
        caffeineCache.invalidate("InnerMarketKey");
        stockService.getInnerMarketInfo();

    }
}
