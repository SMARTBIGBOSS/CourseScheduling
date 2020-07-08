package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anqili.application.bean.Course;
import com.anqili.application.dao.CourseDao;

@Repository
public class CourseDaoImpl implements CourseDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<Course> selectAllCourse() {
		return sqlSession.selectList("selectAllCourse");
	}

	@Override
	public Course selectCourseById(int courseId) {
		return sqlSession.selectOne("selectCourseById",courseId);
	}

	@Override
	public List<Course> selectCourseBySubject(int subjectId) {
		return sqlSession.selectList("selectCourseBySubject", subjectId);
	}

	@Override
	public List<Course> selectCourseByYear(int year) {
		return sqlSession.selectList("selectCourseByYear", year);
	}

	@Override
	@Transactional
	public boolean updateCourse(Course course) {
		sqlSession.update("updateCourse", course);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteOneCourse(int courseId) {
		sqlSession.delete("deleteOneCourse", courseId);
		return true;
	}

	@Override
	@Transactional
	public boolean insertCourse(Course course) {
		sqlSession.insert("insertCourse", course);
		return true;
	}

}
