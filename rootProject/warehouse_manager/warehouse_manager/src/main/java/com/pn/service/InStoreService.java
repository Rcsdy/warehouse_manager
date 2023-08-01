package com.pn.service;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.page.Page;

public interface InStoreService {

    public Result addInStore(InStore inStore, Integer buyId);

    public Page queryInStoreByPage(InStore inStore, Page page);

    public Result inStoreConfirm(InStore inStore);
}
