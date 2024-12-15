package com.fei.stock.config;

import com.fei.stock.pojo.vo.TaskThreadPoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 定义线程池
 */
@Configuration
public class TaskExecutePoolConfig
{
    @Autowired
    private TaskThreadPoolInfo taskThreadPoolInfo;

    @Bean(name = "threadPoolTaskExecutor" ,destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(taskThreadPoolInfo.getCorePoolSize());
        threadPool.setMaxPoolSize(taskThreadPoolInfo.getMaxPoolSize());
        threadPool.setKeepAliveSeconds(taskThreadPoolInfo.getKeepAliveSeconds());
        threadPool.setQueueCapacity(taskThreadPoolInfo.getQueueCapacity());
        //threadPool.initialize();
        return  threadPool;
    }



}
