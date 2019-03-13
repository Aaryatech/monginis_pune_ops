package com.monginis.ops.model.creditnote;

import java.util.List;


public class GetCreditNoteHeadersList {
	
	List<GetCreditNoteHeaders> creditNoteHeaders;

	public List<GetCreditNoteHeaders> getCreditNoteHeaders() {
		return creditNoteHeaders;
	}

	public void setCreditNoteHeaders(List<GetCreditNoteHeaders> creditNoteHeaders) {
		this.creditNoteHeaders = creditNoteHeaders;
	}

	@Override
	public String toString() {
		return "GetCreditNoteHeadersList [creditNoteHeaders=" + creditNoteHeaders + "]";
	}
	
	
}
