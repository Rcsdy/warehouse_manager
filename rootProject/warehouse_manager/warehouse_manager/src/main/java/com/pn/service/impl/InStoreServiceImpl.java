package com.pn.service.impl;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.mapper.InStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {

    //注入InStoreMapper
    @Autowired
    private InStoreMapper inStoreMapper;

    //注入PurchaseMapper
    @Autowired
    private PurchaseMapper purchaseMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;


    @Transactional
    @Override
    public Result addInStore(InStore inStore, Integer buyId) {

        Integer integer = inStoreMapper.insertInStore(inStore);

        if(integer > 0){
            Integer integer1 = purchaseMapper.setIsInByPid(buyId);

            if(integer1 > 0) return Result.ok("入库成功");
            return Result.err(Result.CODE_ERR_BUSINESS, "入库失败");
        }


        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败");
    }

    @Override
    public Page queryInStoreByPage(InStore inStore, Page page) {

        Integer count = inStoreMapper.findInStoreRowCount(inStore);
        List<InStore> inStoreList = inStoreMapper.findInStoreByPage(inStore, page);

        page.setTotalNum(count);
        page.setResultList(inStoreList);

        return page;
    }

    @Transactional
    @Override
    public Result inStoreConfirm(InStore inStore) {

        Integer integer = inStoreMapper.setIsInById(inStore.getInsId());
        if(integer > 0) {
            Integer integer1 = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if(integer1 > 0) return Result.ok("入库成功");
        }

        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败");
    }
}
