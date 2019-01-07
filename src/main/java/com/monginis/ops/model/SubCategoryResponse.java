
package com.monginis.ops.model;

import java.util.List;

public class SubCategoryResponse {

    private List<MCategory> mCategoryList;
    private ErrorMessage errorMessage;

    

   
	public List<MCategory> getmCategoryList() {
		return mCategoryList;
	}

	public void setmCategoryList(List<MCategory> mCategoryList) {
		this.mCategoryList = mCategoryList;
	}

	public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
    

}
