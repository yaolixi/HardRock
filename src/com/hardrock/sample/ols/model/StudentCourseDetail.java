package com.hardrock.sample.ols.model;

import com.hardrock.mongo.annotation.ForeignKey;

public class StudentCourseDetail extends OlsMongoObject {
	@ForeignKey (foreignClass = Student.class, foreignKey = "id")
	private int studentId;
	@ForeignKey (foreignClass = CourseInstanceDetail.class, foreignKey = "id")
	private int courseInstanceDetailId;
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCourseInstanceDetailId() {
		return courseInstanceDetailId;
	}
	public void setCourseInstanceDetailId(int courseInstanceDetailId) {
		this.courseInstanceDetailId = courseInstanceDetailId;
	}
}
