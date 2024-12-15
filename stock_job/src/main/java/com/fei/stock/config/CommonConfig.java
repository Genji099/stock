package com.fei.stock.config;

import com.fei.stock.pojo.vo.StockInfoConfig;
import com.fei.stock.utils.IdWorker;
import com.fei.stock.utils.ParserStockInfoUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({StockInfoConfig.class})
@Configuration
public class CommonConfig
{
    /**
     * 配置雪花算法类
     * @return
     */
    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1L, 2L);
    }
    @Bean
    public ParserStockInfoUtil parserStockInfoUtil(IdWorker idWorker){
        return new ParserStockInfoUtil(idWorker);
    }





}
