package com.bean;

import java.sql.Date;

public class Rate {
	private int userid;
	private int movieid;
	private int rating;
	private Date timestamp;
	private int fancy;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMovieid() {
		return movieid;
	}
	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getFancy() {
		return fancy;
	}
	public void setFancy(int fancy) {
		this.fancy = fancy;
	}
	
}
