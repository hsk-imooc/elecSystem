package com.rupeng.elec.domain;

import java.util.Date;

/**
 * 代办事宜javaBean
 * 
 * @author LeeGossHK
 * 
 */
public class ElecMatter {

	private String matterId;
	private String stationRunStatus;
	private String devRunStatus;
	private Date createDate;

	public String getMatterId() {
		return matterId;
	}

	public void setMatterId(String matterId) {
		this.matterId = matterId;
	}

	public String getStationRunStatus() {
		return stationRunStatus;
	}

	public void setStationRunStatus(String stationRunStatus) {
		this.stationRunStatus = stationRunStatus;
	}

	public String getDevRunStatus() {
		return devRunStatus;
	}

	public void setDevRunStatus(String devRunStatus) {
		this.devRunStatus = devRunStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
