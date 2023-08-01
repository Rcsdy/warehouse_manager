package com.pn.controller;


import com.pn.entity.Result;
import com.pn.service.StoreService;
import com.pn.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    @Autowired
    private StoreService storeService;

//    /statistics/store-invent
    @RequestMapping("/store-invent")
    public Result storeInvent(){
        List<StoreCountVo> storeList = storeService.queryStoreCount();

        return Result.ok(storeList);
    }
}
