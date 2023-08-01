package com.pn.controller;


import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/instore")
@RestController
public class InStoreController {

    @Autowired
    private InStoreService inStoreService;
    @Autowired
    private StoreService storeService;

    //    /instore/store-list
    @RequestMapping("/store-list")
    public Result storeList() {
        List<Store> stores = storeService.queryAllStore();

        return Result.ok(stores);
    }

    //    /instore/instore-page-list
    @RequestMapping("/instore-page-list")
    public Result inStorePageList(Page page, InStore inStore) {

        page = inStoreService.queryInStoreByPage(inStore, page);

        return Result.ok(page);
    }

//    /instore/instore-confirm
    @RequestMapping("/instore-confirm")
    public Result inStoreConfirm(@RequestBody InStore inStore){
        return inStoreService.inStoreConfirm(inStore);
    }

}
