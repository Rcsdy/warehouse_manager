package com.pn.service;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.page.Page;

public interface PurchaseService {

    public Result insertPurchase(Purchase purchase);

    public Page queryPurchaseByPage(Purchase purchase,Page page);

    public Result removePurchaseByPid(Integer purchaseId);

    public Result updatePurchaseByBuyNum(Purchase purchase);
}
