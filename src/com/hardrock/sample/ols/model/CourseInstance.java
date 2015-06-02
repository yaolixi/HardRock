package com.hardrock.sample.ols.model;

import java.util.Date;

import com.hardrock.mongo.annotation.ForeignKey;

public class CourseInstance extends OlsMongoObject {
	private int id;
	@ForeignKey (foreignClass = Course.class, foreignKey = "id")
	private int courseId;
	private Date beginTime;
	private Date endTime;
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
