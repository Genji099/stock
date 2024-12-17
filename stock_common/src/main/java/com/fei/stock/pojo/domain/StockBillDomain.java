package com.fei.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StockBillDomain {


    //private Long id; // 主键ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date; // 当前时间，精确到分
    private Long tradeAmt; // 交易量
    private Long tradeVol; // 交易金额
    private Double tradePrice; // 交易价格

    // 如果需要，可以添加其他字段或方法
}