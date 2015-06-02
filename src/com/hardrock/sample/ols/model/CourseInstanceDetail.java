package com.hardrock.sample.ols.model;

import java.util.Date;

import com.hardrock.mongo.annotation.ForeignKey;

public class CourseInstanceDetail  extends OlsMongoObject{
	private int id;
	@ForeignKey (foreignClass = CourseInstance.class, foreignKey = "id")
	private int courseInstanceId;
	private String location;
	@ForeignKey (foreignClass = Teacher.class, foreignKey = "id")
	private int teacherId;
	private Date beginTime;
	private Date endTime;
	public int getCourseInstanceId() {
		return courseInstanceId;
	}
	public void setCourseInstanceId(int courseInstanceId) {
		this.courseInstanceId = courseInstanceId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
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
