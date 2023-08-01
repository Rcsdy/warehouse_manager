package com.pn.mapper;

import com.pn.entity.OutStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutStoreMapper {

    public int insertOutStore(OutStore outStore);

    public Integer findOutStoreRowCount(OutStore outStore);

    public List<OutStore> findOutStoreByPage(@Param("outStore") OutStore outStore, @Param("page") Page page);

    public Integer setIsOutByOid(Integer outStoreId);

}
