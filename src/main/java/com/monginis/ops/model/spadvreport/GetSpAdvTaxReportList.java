package com.monginis.ops.model.spadvreport;

import java.util.List;

import com.monginis.ops.model.Info;

public class GetSpAdvTaxReportList {
	
	List<GetSpAdvTaxReport> spAdvTaxReport;
	
	Info info;

	public List<GetSpAdvTaxReport> getSpAdvTaxReport() {
		return spAdvTaxReport;
	}

	public void setSpAdvTaxReport(List<GetSpAdvTaxReport> spAdvTaxReport) {
		this.spAdvTaxReport = spAdvTaxReport;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "GetSpAdvTaxReportList [spAdvTaxReport=" + spAdvTaxReport + ", info=" + info + "]";
	}
	

}
