package com.chinasofti.hrsys.entity;

import java.sql.Timestamp;

public class WorkTime {
	private int id;
	private int userId;
	private Timestamp faceTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getFaceTime() {
		return faceTime;
	}
	public void setFaceTime(Timestamp faceTime) {
		this.faceTime = faceTime;
	}
}
