package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anqili.application.bean.SubjectCourse;
import com.anqili.application.dao.SubjectCourseDao;

@Repository
public class SubjectCourseDaoImpl implements SubjectCourseDao {
	
	@Autowired
    private SqlSession sqlSession;
	
	@Override
	public List<SubjectCourse> selectSubjectByCourse(int courseId) {
		return sqlSession.selectList("selectSubjectByCourse", courseId);
	}
	
	@Override
	public List<SubjectCourse> selectCourseBySubject(int subjectId) {
		return sqlSession.selectList("selectCourseBySubject", subjectId);
	}
	
	@Override
	public List<SubjectCourse> selecctAllCourseWithSubject() {
		return sqlSession.selectList("selecctAllCourseWithSubject");

	}
	
	@Override
	public boolean insertSubjectToCourse(SubjectCourse subjectcourse) {
		int affected = sqlSession.insert("insertSubjectToCourse", subjectcourse);
		if(affected > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteSubjectFromCourse(int sub_courId) {
		int affected = sqlSession.insert("deleteSubjectFromCourse", sub_courId);
		if(affected > 0)
			return true;
		return false;
	}
	
}
