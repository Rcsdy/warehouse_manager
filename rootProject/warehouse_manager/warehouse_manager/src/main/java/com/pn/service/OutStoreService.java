package com.pn.service;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.page.Page;

public interface OutStoreService {

    public Result insertOutStore(OutStore outStore);

    public Page queryOutStoreByPage(OutStore outStore, Page page);

    public Result updateIsOutByOid(OutStore outStore);
}
