package com.hardrock.sample.model;

import com.hardrock.mongo.annotation.PrimaryKey;

@PrimaryKey (PK = {"id"})
public class Acct extends SampleMongoObject{
	private int id;
	private String code;
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
