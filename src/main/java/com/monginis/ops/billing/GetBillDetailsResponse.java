
package com.monginis.ops.billing;

import java.util.List;



public class GetBillDetailsResponse {

    private List<GetBillDetail> getBillDetails = null;
    private Info info;

    public List<GetBillDetail> getGetBillDetails() {
        return getBillDetails;
    }

    public void setGetBillDetails(List<GetBillDetail> getBillDetails) {
        this.getBillDetails = getBillDetails;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
