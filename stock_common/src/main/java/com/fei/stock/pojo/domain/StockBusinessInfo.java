package com.fei.stock.pojo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 个股主营业务类
 */
@ApiModel(description = "个股主营业务类")
@Data
public class StockBusinessInfo {
    @ApiModelProperty("股票编码")
    private String code; // 股票编码
    @ApiModelProperty("行业，也就是行业板块名称")
    private String trade; // 行业，也就是行业板块名称
    @ApiModelProperty("公司主营业务")
    private String business; // 公司主营业务
    @ApiModelProperty("公司名称")
    private String name; // 公司名称
}