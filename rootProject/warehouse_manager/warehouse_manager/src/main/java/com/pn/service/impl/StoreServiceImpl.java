package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.mapper.StoreMapper;
import com.pn.page.Page;
import com.pn.service.StoreService;
import com.pn.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.StoreServiceImpl")
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

//    @Cacheable(key = "'all:store'")
    @Override
    public List<Store> queryAllStore() {
        List<Store> allStore = storeMapper.findAllStore();
        return allStore;
    }

    @Override
    public List<StoreCountVo> queryStoreCount() {
        return storeMapper.findStoreCount();
    }

    @Override
    public Page queryStoreByPage(Store store, Page page) {

        Integer count = storeMapper.findStoreRowCount(store);
        List<Store> storeList = storeMapper.findStoreByPage(store, page);

        page.setTotalNum(count);
        page.setResultList(storeList);

        return page;
    }

    @Override
    public Result checkStoreByNum(String storeNum) {

        Store store = storeMapper.findStoreByStoreNum(storeNum);
        System.out.println(store);

        return Result.ok(store == null);
    }

    @Override
    public Result addStore(Store store) {

        Integer integer = storeMapper.insertStore(store);

        if(integer > 0) return Result.ok("添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
    }

    @Override
    public Result updateStoreBySid(Store store) {

        Integer integer = storeMapper.updateStoreBySid(store);

        if(integer > 0) return Result.ok("修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }

    @Override
    public Result deleteStoreBySid(Integer storeId) {

        Integer integer = storeMapper.deleteStoreBySid(storeId);

        if (integer > 0) return Result.ok("删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }
}
