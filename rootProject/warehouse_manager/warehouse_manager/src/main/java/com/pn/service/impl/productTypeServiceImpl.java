package com.pn.service.impl;

import com.pn.entity.Auth;
import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.mapper.ProductTypeMapper;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
//@CacheConfig(cacheNames = "com.pn.service.impl.productTypeServiceImpl")
@Service
public class productTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

//    @Cacheable(key = "'all:type'")
    @Override
    public List<ProductType> queryProductTypeTree() {
        List<ProductType> allProductType = productTypeMapper.findAllProductType();
        allProductType = allProductTypeToProductTypeTree(allProductType,0);
        return allProductType;
    }

    @Override
    public Result checkTypeCode(String typeCode) {

        ProductType productType = productTypeMapper.findProductTypeByCode(typeCode);

        return Result.ok(productType == null);
    }

//    @CacheEvict(key = "'all:type'")
    @Override
    public Result insertType(ProductType productType) {

        ProductType typeByName = productTypeMapper.findProductTypeByName(productType.getTypeName());
        if(typeByName != null) return Result.err(Result.CODE_ERR_BUSINESS, "商品名称已存在");

        Integer integer = productTypeMapper.insertType(productType);

        if(integer > 0) return Result.ok("添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
    }

//    @CacheEvict(key = "'alL:type'")
    @Override
    public Result removeTypeByTid(Integer typeId) {

        Integer integer = productTypeMapper.removeTypeByTid(typeId);

        if(integer > 0) return Result.ok("删除成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }

    @Override
    public Result updateTypeByTid(ProductType productType) {

        ProductType typeByName = productTypeMapper.findProductTypeByName(productType.getTypeName());
        if(typeByName != null && typeByName.getTypeCode().equals(productType.getTypeCode()))
            return Result.err(Result.CODE_ERR_BUSINESS, "商品种类已存在");

        Integer integer = productTypeMapper.updateTypeByTid(productType);

        if (integer > 0) return Result.ok("修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败");
    }

    private List<ProductType> allProductTypeToProductTypeTree(List<ProductType> allProductType, Integer pid){
        List<ProductType> firstLevelAuthList = new ArrayList<>();
        for (ProductType productType:allProductType) {
            if(productType.getParentId() == pid){
                firstLevelAuthList.add(productType);
            }
        }

        for (ProductType firstProductType:firstLevelAuthList) {
            List<ProductType> secondLevelAuthList = allProductTypeToProductTypeTree(allProductType, firstProductType.getTypeId());
            firstProductType.setChildProductCategory(secondLevelAuthList);
        }

        return firstLevelAuthList;
    }
}
