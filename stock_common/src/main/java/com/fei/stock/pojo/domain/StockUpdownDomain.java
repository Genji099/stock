package com.fei.stock.pojo.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author by itheima
 * @Date 2022/2/28
 * @Description 股票涨跌信息
 */
@ApiModel(description = "股票涨跌信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdownDomain {

    /**
     * 股票编码
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","股票编码"},index = 0)
    @ApiModelProperty("股票编码")
    private String code;
    /**
     * 股票名称
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","股票名称"},index = 1)
    @ApiModelProperty("股票名称")
    private String name;
    /**
     * 前收盘价格
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","前收盘价格"},index = 2)
    @ApiModelProperty("前收盘价格")
    private BigDecimal preClosePrice;
    /**
     * 当前价格
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","当前价格"},index= 3)
    @ApiModelProperty("当前价格")
    private BigDecimal tradePrice;
    /**
     * 涨跌
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","涨跌"},index= 4)
    @ApiModelProperty("涨跌")
    private BigDecimal increase;
    /**
     * 涨幅
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","涨幅"},index= 5)
    @ApiModelProperty("涨幅")
    private BigDecimal upDown;
    /**
     * 振幅
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","振幅"},index= 6)
    @ApiModelProperty("振幅")
    private BigDecimal amplitude;
    /**
     * 交易量
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","交易总量"},index = 7)
    @ApiModelProperty("交易量")
    private Long tradeAmt;
    /**
     * 交易金额
     */
    @ExcelProperty(value = {"股票涨幅信息统计表","交易总金额"},index = 8)
    @ApiModelProperty("交易金额")
    private BigDecimal tradeVol;

    /**
     * 日期
     */
    @DateTimeFormat("yyy-MM-dd HH:mm")//easyExcel的注解-》excel
    @ExcelProperty(value = {"股票涨幅信息统计表","日期"},index = 9)
    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate;
}