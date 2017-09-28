package com.rupeng.elec.domain;

import java.util.Date;

/**
 * create table electext( textId varchar(100) primary key, textName
 * varchar(100), textDate date, textComment text );
 * 
 * @author LeeGossHK
 * 
 */
public class ElecText {

	private String textId;
	private String textName;
	private Date textDate;
	private String textComment;

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public Date getTextDate() {
		return textDate;
	}

	public void setTextDate(Date textDate) {
		this.textDate = textDate;
	}

	public String getTextComment() {
		return textComment;
	}

	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	@Override
	public String toString() {
		return "ElecText [textId=" + textId + ", textName=" + textName
				+ ", textDate=" + textDate + ", textComment=" + textComment
				+ "]";
	}

}
