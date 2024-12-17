package com.fei.stock.mapper;

import com.fei.stock.pojo.domain.StockBusinessInfo;
import com.fei.stock.pojo.entity.StockBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据股票部分代码进行模糊查询
     * @param searchStr
     * @return
     */
    List<Map> searchByCode(@Param("searchStr") String searchStr);

    /**
     * 个股主营业务查询接口
     * @param code
     * @return
     */
    StockBusinessInfo getStockBusinessInfo(@Param("code") String code);
}
