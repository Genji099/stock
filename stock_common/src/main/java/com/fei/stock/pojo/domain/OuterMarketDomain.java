package com.fei.stock.pojo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class OuterMarketDomain
{
    @ApiModelProperty("大盘名称")
    private String name; // 大盘名称
    @ApiModelProperty("当前大盘点")
    private Double curPoint; // 当前大盘点
    @ApiModelProperty("涨跌值")
    private Double upDown; // 涨跌值
    @ApiModelProperty("涨幅")
    private Double rose; // 涨幅
    @ApiModelProperty("当前时间")
    private String curTime; // 当前时间
}