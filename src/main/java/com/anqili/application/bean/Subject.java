package com.anqili.application.bean;

public class Subject {
	private int subjId;
	private String subjName;
	private int classTime;
	private int labTime;
	private int teachId;
	private Teacher teacher;
	
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
	public int getTeachId() {
		return teachId;
	}
	public void setTeachId(int teachId) {
		this.teachId = teachId;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@Override
	public String toString() {
		return "Subject [subjId=" + subjId + ", subjName=" + subjName + ", classTime=" + classTime + ", labTime="
				+ labTime + ", teachId=" + teachId + ", teacher=" + teacher + "]";
	}
	
}
