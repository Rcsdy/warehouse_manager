package com.pn.controller;


import com.pn.entity.*;
import com.pn.page.Page;
import com.pn.service.*;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private SupplyService supplyService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private TokenUtils tokenUtils;

    //    /product/store-list
    @RequestMapping("/store-list")
    public Result storeList() {
        List<Store> stores = storeService.queryAllStore();
        return Result.ok(stores);
    }

    //    /product/brand-list
    @RequestMapping("/brand-list")
    public Result brandList() {
        List<Brand> brandList = brandService.queryAllBrand();
        return Result.ok(brandList);
    }

    //    /product/category-tree
    @RequestMapping("/category-tree")
    public Result categoryTree() {
        List<ProductType> productTypes = productTypeService.queryProductTypeTree();

        return Result.ok(productTypes);
    }

    //    /product/supply-list
    @RequestMapping("/supply-list")
    public Result supplyList() {
        List<Supply> supplyList = supplyService.queryAllSupply();
        return Result.ok(supplyList);
    }

    //    /product/place-list
    @RequestMapping("/place-list")
    public Result placeList() {
        List<Place> placeList = placeService.queryAllPlace();
        return Result.ok(placeList);
    }

    //    /product/unit-list
    @RequestMapping("/unit-list")
    public Result unitList() {
        List<Unit> unitList = unitService.queryAllUnit();
        return Result.ok(unitList);
    }

    //    /product/product-page-list
    @RequestMapping("/product-page-list")
    public Result productPageList(Product product, Page page) {
        page = productService.queryProductPage(page, product);

        return Result.ok(page);
    }

    @Value("${file.upload-path}")
    private String uploadPath;

    @CrossOrigin
    @RequestMapping("/img-upload")
    public Result imgUpload(MultipartFile file){
        try {
            //解析类路径并拿到封装类路径的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传的目录路径的自盘路径
            File uploadDirPath = uploadDirFile.getAbsoluteFile();
            //拿到图片上传的名字
            String filename = file.getOriginalFilename();
            //拿到上传文件需要保存到的磁盘文件的路径
            String uploadFilePath = uploadDirPath + "\\" + filename;
            //上传图片
            file.transferTo(new File(uploadFilePath));

            return Result.ok("上传成功");

        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS, "上传失败");
        }
    }

    @Value("${file.access-path}")
    private String accessPath;

//    /product/product-add
    @RequestMapping("/product-add")
    public Result productAdd(@RequestBody Product product, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        product.setCreateBy(createBy);
        String imgs = accessPath + "/" + product.getImgs();
        product.setImgs(imgs);

        Result result = productService.insertProduct(product);
        return Result.ok(result);
    }

//    /product/state-change
    @RequestMapping("/state-change")
    public Result stateChange(@RequestBody Product product){

        return productService.updateProductStateByPid(product);
    }

//    /product/product-delete/22
    @RequestMapping("/product-delete/{productId}")
    public Result productDelete(@PathVariable Integer productId){
        List<Integer> productIdList = new ArrayList<>();
        productIdList.add(productId);

        return productService.deleteProductByPid(productIdList);
    }

//    /product/product-list-delete
    @RequestMapping("/product-list-delete")
    public Result productListDelete(@RequestBody List<Integer> productIdList){
        return productService.deleteProductByPid(productIdList);
    }

//    /product/product-update
    @RequestMapping("/product-update")
    public Result productUpdate(@RequestBody Product product, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);

        return productService.updateProductByPid(product);
    }
}
