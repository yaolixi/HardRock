package com.hardrock.sample.ols.service;

import java.util.Collection;

import com.hardrock.mongo.object.MongoObjectProxy;
import com.hardrock.sample.ols.OlsMongoQuery;
import com.hardrock.sample.ols.model.Course;
import com.hardrock.sample.ols.model.CourseInstanceDetail;
import com.hardrock.sample.ols.model.Student;
import com.hardrock.sample.ols.model.StudentCourseDetail;
import com.hardrock.sample.ols.model.Teacher;

public class OlsService {
	public static Collection<Student> getAllStudents(){
		return new OlsMongoQuery<Student>(Student.class).findAll();
	}
	
	public static Student getStudentByCode(String code){
		Student student = MongoObjectProxy.create(Student.class);
		student.setCode(code);
		return (Student) student.findOne();
	}
	
	public static Collection<Course> getAllCourses(){
		return new OlsMongoQuery<Course>(Course.class).findAll();
	}
	
	public static Collection<Course> getCoursesByName(String name){
		Course course = new Course();
		return (Collection<Course>) course.find();
	}
	
	public static void getTeachersByStudentCode(String code){
		Student student = MongoObjectProxy.create(Student.class);
		student.setCode(code);
		
		StudentCourseDetail scd = MongoObjectProxy.create(StudentCourseDetail.class);
		scd.bindForeignKey("studentId", student);
//		scd.bindForeignKey("studentId", student, "id");
		
//		System.out.println(scd.find());
		
		CourseInstanceDetail cid = MongoObjectProxy.create(CourseInstanceDetail.class);
		cid.bindForeignKey("id", scd, "courseInstanceDetailId");
		
//		System.out.println(cid.find());
		
		Teacher teacher = MongoObjectProxy.create(Teacher.class); 
		teacher.bindForeignKey("id", cid, "teacherId");
		
		System.out.println(teacher.find());
	}
	
	public static void getTeachersByStudentCode2(String code){
		Student student = MongoObjectProxy.create(Student.class);
		student.setCode(code);
		
		StudentCourseDetail scd = new StudentCourseDetail();
		scd.bindForeignKey("studentId", student, "id");
		
		student.bind("id", StudentCourseDetail.class, "studentId");
		
		System.out.println(scd.find());
		
//		CourseInstanceDetail cid = new CourseInstanceDetail();
//		cid.bindForeignKey("id", scd, "courseInstanceDetailId");
//		
//		Teacher teacher = new Teacher();
//		teacher.bindForeignKey("id", cid, "teacherId");
//		
//		System.out.println(teacher.find());
	}
	
	public static void main(String[] args) {
		OlsService.getTeachersByStudentCode2("000001");
	}
}
