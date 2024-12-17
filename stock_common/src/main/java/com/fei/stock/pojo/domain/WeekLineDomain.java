package com.fei.stock.pojo.domain;

import lombok.Data;

import java.util.Date;

/**
 * 一周内的股票行情数据
 */
@Data
public class WeekLineDomain {
    /**
     * 一周内平均价
     */
    private Double avgPrice;

    /**
     * 一周内最低价
     */
    private Double minPrice;

    /**
     * 周一开盘价
     */
    private Double openPrice;

    /**
     * 一周内最高价
     */
    private Double maxPrice;

    /**
     * 周五收盘价（如果当前日期不到周五，则显示最新价格）
     */
    private Double closePrice;

    /**
     * 一周内最大时间
     */
    private Date mxTime;

    /**
     * 股票编码
     */
    private String stockCode;
}