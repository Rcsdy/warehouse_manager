package com.pn.controller;


import com.pn.entity.CurrentUser;
import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.OutStoreService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/outstore")
@RestController
public class OutStoreController {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private OutStoreService outStoreService;
    @Autowired
    private StoreService storeService;

//    /outstore/outstore-add
    @RequestMapping("/outstore-add")
    public Result outStoreAdd(@RequestBody OutStore outStore, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        outStore.setCreateBy(createBy);

        return outStoreService.insertOutStore(outStore);
    }

//    /outstore/store-list
    @RequestMapping("/store-list")
    public Result storeList(){
        List<Store> stores = storeService.queryAllStore();

        return Result.ok(stores);
    }


//    /outstore/outstore-page-list
    @RequestMapping("/outstore-page-list")
    public Result outStorePageList(Page page, OutStore outStore){
        page = outStoreService.queryOutStoreByPage(outStore, page);

        return Result.ok(page);
    }

//    /outstore/outstore-confirm
    @RequestMapping("/outstore-confirm")
    public Result outStoreConfirm(@RequestBody OutStore outStore){
        return outStoreService.updateIsOutByOid(outStore);
    }
}
