package com.pn.mapper;

import com.pn.entity.InStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InStoreMapper {

    public Integer insertInStore(InStore inStore);

    public Integer findInStoreRowCount(InStore inStore);

    public List<InStore> findInStoreByPage(@Param("inStore") InStore inStore, @Param("page") Page page);

    public Integer setIsInById(Integer inStoreId);
}
