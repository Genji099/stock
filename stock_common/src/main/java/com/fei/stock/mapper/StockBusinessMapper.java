package com.fei.stock.mapper;

import com.fei.stock.pojo.entity.StockBusiness;

import java.util.List;

/**
* @author 59747
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    /**
     * 查询股票编码集合
     * @return
     */
    List<String> getAllSockCode();

}
