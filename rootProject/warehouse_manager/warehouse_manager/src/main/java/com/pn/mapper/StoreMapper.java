package com.pn.mapper;

import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.vo.StoreCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper {

    //查询所有仓库
    public List<Store> findAllStore();

    public List<StoreCountVo> findStoreCount();

    public Integer findStoreRowCount(Store store);

    public List<Store> findStoreByPage(@Param("store") Store store, @Param("page") Page page);

    public Store findStoreByStoreNum(String storeNum);

    public Integer insertStore(Store store);

    public Integer updateStoreBySid(Store store);

    public Integer deleteStoreBySid(Integer storeId);
}
