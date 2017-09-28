package com.rupeng.elec.util;

import java.util.List;

public class DataTablesPageBean<T> {

	/**
	 * 
	 "sEcho":1, "iTotalRecords":26, //总数据条数 "iTotalDisplayRecords":26,
	 * "aaData":[ //一页数据
	 * {"userId":"001","account":"dandan1","username":"dandan1"
	 * ,"gender":"male"},
	 * {"userId":"002","account":"dandan2","username":"dandan2"
	 * ,"gender":"male"},
	 * {"userId":"003","account":"dandan3","username":"dandan3","gender":"male"}
	 * ]
	 */
	/**
	 * 服务器需要返回的数据
	 */
	private String sEcho;
	private int iTotalRecords;// 总数据条数
	private int iTotalDisplayRecords;
	private List<T> data;// 当前页的数据

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
