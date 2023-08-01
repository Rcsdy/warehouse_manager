package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.vo.StoreCountVo;

import java.util.List;

public interface StoreService {

    public List<Store> queryAllStore();

    public List<StoreCountVo> queryStoreCount();

    public Page queryStoreByPage(Store store,Page page);

    public Result checkStoreByNum(String storeNum);

    public Result addStore(Store store);

    public Result updateStoreBySid(Store store);

    public Result deleteStoreBySid(Integer storeId);
}
