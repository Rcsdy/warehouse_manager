package com.pn.controller;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/store")
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

//    /store/store-page-list
    @RequestMapping("/store-page-list")
    public Result storePageList(Store store, Page page){

        page = storeService.queryStoreByPage(store, page);

        return Result.ok(page);
    }

//    /store/store-num-check
    @RequestMapping("/store-num-check")
    public Result storeNumCheck(String storeNum){
        return storeService.checkStoreByNum(storeNum);
    }

//    /store/store-add
    @RequestMapping("/store-add")
    public Result storeAdd(@RequestBody Store store){
        return storeService.addStore(store);
    }

//    /store/store-update
    @RequestMapping("/store-update")
    public Result storeUpdate(@RequestBody Store store){
        return storeService.updateStoreBySid(store);
    }

//    /store/store-delete/4
    @RequestMapping("/store-delete/{storeId}")
    public Result storeDelete(@PathVariable Integer storeId){
        return storeService.deleteStoreBySid(storeId);
    }

}
