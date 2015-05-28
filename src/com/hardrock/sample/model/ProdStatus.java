package com.hardrock.sample.model;

import java.util.Date;

import com.hardrock.mongo.annotation.ForeignKey;
import com.hardrock.mongo.annotation.PrimaryKey;
import com.hardrock.mongo.annotation.RequiredField;

@PrimaryKey (PK = {"prodId", "status", "statusBeginDate", "statusEndDate"})
public class ProdStatus extends SampleMongoObject{
	@RequiredField
	@ForeignKey (foreignClass = Prod.class, foreignKey = "id")
	private int prodId;
	@RequiredField
	private String status;
	private Date statusBeginDate;
	private Date statusEndDate;
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStatusBeginDate() {
		return statusBeginDate;
	}
	public void setStatusBeginDate(Date statusBeginDate) {
		this.statusBeginDate = statusBeginDate;
	}
	public Date getStatusEndDate() {
		return statusEndDate;
	}
	public void setStatusEndDate(Date statusEndDate) {
		this.statusEndDate = statusEndDate;
	}
}
