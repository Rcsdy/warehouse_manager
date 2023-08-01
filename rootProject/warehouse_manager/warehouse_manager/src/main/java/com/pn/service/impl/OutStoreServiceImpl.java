package com.pn.service.impl;

import com.pn.entity.OutStore;
import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.OutStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService {

    //注入OutStoreMapper
    @Autowired
    private OutStoreMapper outStoreMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;


    @Override
    public Result insertOutStore(OutStore outStore) {

        outStore.setOutPrice(outStore.getOutPrice());

        int i = outStoreMapper.insertOutStore(outStore);

        if(i > 0) return  Result.ok("添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
    }

    @Override
    public Page queryOutStoreByPage(OutStore outStore, Page page) {

        Integer count = outStoreMapper.findOutStoreRowCount(outStore);
        List<OutStore> outStoreList = outStoreMapper.findOutStoreByPage(outStore, page);

        page.setTotalNum(count);
        page.setResultList(outStoreList);

        return page;
    }

    @Transactional
    @Override
    public Result updateIsOutByOid(OutStore outStore) {

        Integer invent = productMapper.findInventByProductId(outStore.getProductId());
        if(invent < outStore.getOutNum()) return Result.err(Result.CODE_ERR_BUSINESS, "商品库存不足");

        Integer integer = outStoreMapper.setIsOutByOid(outStore.getOutsId());
        if(integer > 0 ){
            Integer integer1 = productMapper.setInventById(outStore.getProductId(), -outStore.getOutNum());
            if(integer1 > 0) return Result.ok("出库成功");
        }

        return Result.err(Result.CODE_ERR_BUSINESS, "出库失败");
    }
}
