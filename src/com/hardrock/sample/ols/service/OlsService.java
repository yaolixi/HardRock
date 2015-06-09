package com.hardrock.sample.ols.service;

import java.util.Collection;

import com.hardrock.mongo.criteria.Criteria;
import com.hardrock.mongo.criteria.Restrictions;
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
		
		CourseInstanceDetail cid = MongoObjectProxy.create(CourseInstanceDetail.class);
		cid.bindForeignKey("id", scd, "courseInstanceDetailId");
		
		Teacher teacher = MongoObjectProxy.create(Teacher.class); 
		teacher.bindForeignKey("id", cid, "teacherId");
		
		System.out.println(teacher.find());
	}
	
	public static void getTeachersByStudentCode2(String code){
		Student student = MongoObjectProxy.create(Student.class);
		student.setCode(code);
		
		Teacher teacher = student.bind("id", StudentCourseDetail.class, "studentId")
				.bind("courseInstanceDetailId", CourseInstanceDetail.class, "id")
				.bind("teacherId", Teacher.class, "id");
		teacher.setId(1);
		System.out.println(teacher.sortDesc("id").find());
	}
	
	public static void main(String[] args) throws Exception {
		Teacher teacher = MongoObjectProxy.create(Teacher.class);
		teacher.addCriteria(new Criteria().add(Restrictions.gte("id", 2)).add(Restrictions.lte("id", 3)));
//		teacher.buildCriteria().append("id", new BasicDBObject("$gte", 2).append("$lte", 3));
//		teacher.buildCriteria().append("id", new BasicDBObject("$gte", 2)).append("id", new BasicDBObject("$lte", 2));
		
		System.out.println(teacher.find());
	}
}
