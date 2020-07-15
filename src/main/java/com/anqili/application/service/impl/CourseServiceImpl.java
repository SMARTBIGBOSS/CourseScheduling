package com.anqili.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.Course;
import com.anqili.application.dao.CourseDao;
import com.anqili.application.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	@Resource
	private CourseDao courseDao;
	
	@Override
	public List<Course> getAllCourse() {
		return courseDao.selectAllCourse();
	}

	@Override
	public Course getCourseById(int courseId) {
		return courseDao.selectCourseById(courseId);
	}

	@Override
	public List<Course> getCourseByYear(int year) {
		return courseDao.selectCourseByYear(year);
	}

	@Override
	public boolean updateCourse(int courseId, Course course) {
		Course oldCourse = courseDao.selectCourseById(courseId);
		oldCourse.setCourName(course.getCourName());
		oldCourse.setCourCode(course.getCourCode());
		oldCourse.setCourSize(course.getCourSize());
		oldCourse.setYear(course.getYear());
		return courseDao.updateCourse(oldCourse);
	}

	@Override
	public boolean deleteOneCourse(int courseId) {
		return courseDao.deleteOneCourse(courseId);
	}

	@Override
	public boolean deleteManyCourse(List<Integer> courseIDs) {
		while(!courseIDs.isEmpty()) {
			deleteOneCourse(courseIDs.remove(0));
		}
		return true;
	}

	@Override
	public boolean insertCourse(Course course) {
		return courseDao.insertCourse(course);
	}

}
