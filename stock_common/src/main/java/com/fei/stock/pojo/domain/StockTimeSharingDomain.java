package com.fei.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StockTimeSharingDomain {

    private Long tradeAmt; // 最新交易量
    private Double preClosePrice; // 前收盘价格
    private Double lowPrice; // 最低价
    private Double highPrice; // 最高价
    private Double openPrice; // 开盘价
    private Long tradeVol; // 交易金额
    private Double tradePrice; // 当前价格
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate; // 当前日期


}
