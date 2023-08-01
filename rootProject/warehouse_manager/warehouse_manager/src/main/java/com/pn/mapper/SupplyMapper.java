package com.pn.mapper;

import com.pn.entity.Supply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplyMapper {

    public List<Supply> findAllSupply();
}
