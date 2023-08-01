package com.pn.mapper;

import com.pn.entity.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    public List<Brand> findAllBrand();
}
