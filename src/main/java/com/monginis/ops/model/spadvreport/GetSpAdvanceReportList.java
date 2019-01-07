package com.monginis.ops.model.spadvreport;

import java.util.List;

import com.monginis.ops.model.Info;

public class GetSpAdvanceReportList {

	List<GetSpAdvanceReport> spAdvanceReport;

	Info info;

	public List<GetSpAdvanceReport> getSpAdvanceReport() {
		return spAdvanceReport;
	}

	public void setSpAdvanceReport(List<GetSpAdvanceReport> spAdvanceReport) {
		this.spAdvanceReport = spAdvanceReport;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "GetSpAdvanceReportList [spAdvanceReport=" + spAdvanceReport + ", info=" + info + "]";
	}

}
