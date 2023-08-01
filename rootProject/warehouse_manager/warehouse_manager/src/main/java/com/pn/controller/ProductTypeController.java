package com.pn.controller;


import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/productCategory")
@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    //    /productCategory/product-category-tree
    @RequestMapping("/product-category-tree")
    public Result productCategoryTree() {

        List<ProductType> productTypes = productTypeService.queryProductTypeTree();

        return Result.ok(productTypes);
    }

    //    /productCategory/verify-type-code?typeCode=1
    @RequestMapping("/verify-type-code")
    public Result verifyTypeCode(String typeCode) {

        return productTypeService.checkTypeCode(typeCode);
    }

//    /productCategory/type-add
    @RequestMapping("/type-add")
    public Result typeAdd(@RequestBody ProductType productType){

        return productTypeService.insertType(productType);

    }

//    /productCategory/type-delete/16
    @RequestMapping("/type-delete/{typeId}")
    public Result typeDelete(@PathVariable Integer typeId){
        return productTypeService.removeTypeByTid(typeId);
    }

//    /productCategory/type-update
    @RequestMapping("/type-update")
    public Result typeUpdate(@RequestBody ProductType productType){
        return productTypeService.updateTypeByTid(productType);
    }
}
