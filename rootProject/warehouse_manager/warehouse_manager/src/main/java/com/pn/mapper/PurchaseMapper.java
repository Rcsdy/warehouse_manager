package com.pn.mapper;

import com.pn.entity.ProductType;
import com.pn.entity.Purchase;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    public int insertPurchase(Purchase purchase);

    public Integer findPurchaseRowCount(Purchase purchase);

    public List<Purchase> findPurchaseByPage(@Param("purchase") Purchase purchase,@Param("page") Page page);

    public Integer removePurchaseByPid(Integer purchaseId);

    public Integer updatePurchaseByBuyNum(Purchase purchase);

    public Integer setIsInByPid(Integer buyId);
}
