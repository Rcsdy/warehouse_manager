package com.pn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCountVo {
    private Integer storeId;//仓库Id

    private String storeName;//仓库名字

    private Integer totalInvent;//仓库商品数量
}
