package com.sgsg.dra.domain;

import java.util.List;

public class Promotion {
	private long event_num;
	private String title;
	private String content;
	private String created_date;
	private String startDate;
	private String sday;
	private String stime;
	private String endDate;
	private String eday;
	private String etime;
	private String winningDate;
	private String wday;
	private String wtime;
	private int winnerNumber;
	private int showEvent;
	private String userId;
	private String userName;
	private String firstImage;
	
	
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	
	private int rank;
	
	private int applyCount;
	private int winnerCount;
	
	private int winEvent;
	private List<Integer> rankNum;
	private List<Integer> rankCount;
	public long getEvent_num() {
		return event_num;
	}
	public void setEvent_num(long event_num) {
		this.event_num = event_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSday() {
		return sday;
	}
	public void setSday(String sday) {
		this.sday = sday;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEday() {
		return eday;
	}
	public void setEday(String eday) {
		this.eday = eday;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getWinningDate() {
		return winningDate;
	}
	public void setWinningDate(String winningDate) {
		this.winningDate = winningDate;
	}
	public String getWday() {
		return wday;
	}
	public void setWday(String wday) {
		this.wday = wday;
	}
	public String getWtime() {
		return wtime;
	}
	public void setWtime(String wtime) {
		this.wtime = wtime;
	}
	public int getWinnerNumber() {
		return winnerNumber;
	}
	public void setWinnerNumber(int winnerNumber) {
		this.winnerNumber = winnerNumber;
	}
	public int getShowEvent() {
		return showEvent;
	}
	public void setShowEvent(int showEvent) {
		this.showEvent = showEvent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getApplyCount() {
		return applyCount;
	}
	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}
	public int getWinnerCount() {
		return winnerCount;
	}
	public void setWinnerCount(int winnerCount) {
		this.winnerCount = winnerCount;
	}
	public int getWinEvent() {
		return winEvent;
	}
	public void setWinEvent(int winEvent) {
		this.winEvent = winEvent;
	}
	public List<Integer> getRankNum() {
		return rankNum;
	}
	public void setRankNum(List<Integer> rankNum) {
		this.rankNum = rankNum;
	}
	public List<Integer> getRankCount() {
		return rankCount;
	}
	public void setRankCount(List<Integer> rankCount) {
		this.rankCount = rankCount;
	}
	

}
