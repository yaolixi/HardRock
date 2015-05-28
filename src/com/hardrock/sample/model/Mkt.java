package com.hardrock.sample.model;

import com.hardrock.mongo.annotation.PrimaryKey;
import com.hardrock.mongo.annotation.RequiredField;

@PrimaryKey (PK = {"id"})
public class Mkt extends SampleMongoObject{
	@RequiredField
	private int id;
	@RequiredField
	private String code;
	@RequiredField
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
