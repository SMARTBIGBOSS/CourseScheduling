package com.anqili.application.bean;

public class Course {
	private int courId;
	private String courName;
	private String courCode;
	private int courSize;
	private int year;
	
	public int getCourId() {
		return courId;
	}
	
	public void setCourId(int courId) {
		this.courId = courId;
	}

	public String getCourName() {
		return courName;
	}
	public void setCourName(String courName) {
		this.courName = courName;
	}
	public String getCourCode() {
		return courCode;
	}
	public void setCourCode(String courCode) {
		this.courCode = courCode;
	}
	public int getCourSize() {
		return courSize;
	}
	public void setCourSize(int courSize) {
		this.courSize = courSize;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Course [courId=" + courId + ", courName=" + courName + ", courCode=" + courCode + ", courSize="
				+ courSize + ", year=" + year + "]";
	}
	
}
