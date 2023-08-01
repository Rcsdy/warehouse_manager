package com.pn.controller;


import com.pn.entity.*;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/purchase")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private InStoreService inStoreService;
    @Autowired
    private TokenUtils tokenUtils;

//    /purchase/purchase-add
    @RequestMapping("/purchase-add")
    public Result purchaseAdd(@RequestBody Purchase purchase){
        return purchaseService.insertPurchase(purchase);
    }

//    /purchase/store-list
    @RequestMapping("/store-list")
    public Result storeList(){
        List<Store> stores = storeService.queryAllStore();
        return Result.ok(stores);
    }

//    /purchase/purchase-page-list
    @RequestMapping("/purchase-page-list")
    public Result purchasePageList(Purchase purchase, Page page){
        page = purchaseService.queryPurchaseByPage(purchase, page);

        return Result.ok(page);
    }

//    /purchase/purchase-delete/8
    @RequestMapping("/purchase-delete/{purchaseId}")
    public Result purchaseDelete(@PathVariable Integer purchaseId){
        return purchaseService.removePurchaseByPid(purchaseId);
    }

//    /purchase/purchase-update
    @RequestMapping("/purchase-update")
    public Result purchaseUpdate(@RequestBody Purchase purchase){
        return purchaseService.updatePurchaseByBuyNum(purchase);
    }

//    /purchase/in-warehouse-record-add
    @RequestMapping("/in-warehouse-record-add")
    public Result inWareHouseRecordAdd(@RequestBody Purchase purchase, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        InStore inStore = new InStore();
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());
        inStore.setCreateBy(createBy);

        return inStoreService.addInStore(inStore, purchase.getBuyId());
    }
}
