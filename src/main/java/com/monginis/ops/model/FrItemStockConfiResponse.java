
package com.monginis.ops.model;

import java.util.List;


public class FrItemStockConfiResponse {

    private List<FrItemStockConfigure> frItemStockConfigure ;
    private Info info;

    public List<FrItemStockConfigure> getFrItemStockConfigure() {
        return frItemStockConfigure;
    }

    public void setFrItemStockConfigure(List<FrItemStockConfigure> frItemStockConfigure) {
        this.frItemStockConfigure = frItemStockConfigure;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
