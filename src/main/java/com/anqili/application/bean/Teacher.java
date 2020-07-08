package com.anqili.application.bean;

public class Teacher {
	private int teachId;
	private String teachNum;
	private String teachName;
	private String department;
	private int status;
	private Subject subject;
	
	public int getTeachId() {
		return teachId;
	}
	public void setTeachId(int teachId) {
		this.teachId = teachId;
	}
	public String getTeachNum() {
		return teachNum;
	}
	public void setTeachNum(String teachNum) {
		this.teachNum = teachNum;
	}
	public String getTeachName() {
		return teachName;
	}
	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "teacher [teachId=" + teachId + ", teachNum=" + teachNum + ", teachName=" + teachName + ", deptmant=" + department
				+ ", status=" + status + "]";
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
}
