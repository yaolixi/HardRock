package com.hardrock.sample.ols.model;

import com.hardrock.mongo.annotation.PrimaryKey;

@PrimaryKey (PK = {"id"})
public class Teacher extends OlsMongoObject{
	private int id;
	private String code;
	private String name;
	private String title;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
