package com.pn.service.impl;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public Result insertPurchase(Purchase purchase) {
//        purchase.setFactBuyNum(purchase.getBuyNum());

        int i = purchaseMapper.insertPurchase(purchase);

        if(i > 0) return Result.ok("添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
    }

    @Override
    public Page queryPurchaseByPage(Purchase purchase, Page page) {

        Integer count = purchaseMapper.findPurchaseRowCount(purchase);
        List<Purchase> purchaseByPage = purchaseMapper.findPurchaseByPage(purchase, page);

        page.setTotalNum(count);
        page.setResultList(purchaseByPage);

        return page;
    }

    @Override
    public Result removePurchaseByPid(Integer purchaseId) {

        Integer integer = purchaseMapper.removePurchaseByPid(purchaseId);

        if(integer > 0) return Result.ok("删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result updatePurchaseByBuyNum(Purchase purchase) {

        Integer integer = purchaseMapper.updatePurchaseByBuyNum(purchase);

        if(integer > 0) return Result.ok("修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }
}
