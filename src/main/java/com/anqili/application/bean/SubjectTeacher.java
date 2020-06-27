package com.anqili.application.bean;

public class SubjectTeacher {
	private int subjId;
	private String subjName;
	private int classTime;
	private int labTime;
	private int teacherId;
	private String teacherName;
	private String teacherNumber;
	private String teacherDepartment;
	public int getSubjId() {
		return subjId;
	}
	public void setSubjId(int subjId) {
		this.subjId = subjId;
	}
	public String getSubjName() {
		return subjName;
	}
	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}
	public int getClassTime() {
		return classTime;
	}
	public void setClassTime(int classTime) {
		this.classTime = classTime;
	}
	public int getLabTime() {
		return labTime;
	}
	public void setLabTime(int labTime) {
		this.labTime = labTime;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherNumber() {
		return teacherNumber;
	}
	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}
	public String getTeacherDepartment() {
		return teacherDepartment;
	}
	public void setTeacherDepartment(String teacherDepartment) {
		this.teacherDepartment = teacherDepartment;
	}
	@Override
	public String toString() {
		return "SubjectTeacher [subjId=" + subjId + ", subjName=" + subjName + ", classTime=" + classTime + ", labTime="
				+ labTime + ", teacherId=" + teacherId + ", teacherName=" + teacherName + ", teacherNumber="
				+ teacherNumber + ", teacherDepartment=" + teacherDepartment + "]";
	}
	
}
