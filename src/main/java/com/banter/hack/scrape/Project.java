package com.banter.hack.scrape;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Project {

	private String title = "";
	
	private String picture = "";
	
	private String description = "";
	
	private String author = "";
	
	private Integer backers = -1;
	
	private Integer pledge = -1;
	
	private Double goal = -1.0;
	
	private Integer deadline = -1;
		
	private Integer id = -1;
	
	private Integer antiBackers = -1;
	
	private Integer antiPledge = -1;
	
	private Integer antiGoal = -1;
	
	public Integer getAntiBackers() {
		return antiBackers;
	}
	
	public void setAntiBackers(Integer antiBackers) {
		this.antiBackers = antiBackers;
	}
	
	public Integer getAntiPledge() {
		return antiPledge;
	}
	
	public void setAntiPledge(Integer antiPledge) {
		this.antiPledge = antiPledge;
	}
	
	public Integer getAntiGoal() {
		return antiGoal;
	}
	
	public void setAntiGoal(Integer antiGoal) {
		this.antiGoal = antiGoal;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getBackers() {
		return backers;
	}

	public void setBackers(Integer backers) {
		this.backers = backers;
	}

	public Integer getPledge() {
		return pledge;
	}

	public void setPledge(Integer pledge) {
		this.pledge = pledge;
	}

	public Double getGoal() {
		return goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}
	
	public void setPicture(String picture){
		this.picture=picture;
	}
	
	public String getPicture(){
		return picture;
	}
	
	public String getDeadline() {
		String dateDeadline = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date now = Calendar.getInstance().getTime();
		
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(deadline);
		
		long finalinmilli = now.getTime()+end.getTimeInMillis();
		
		end.setTimeInMillis(finalinmilli);
		
		dateDeadline = sdf.format(end.getTime());
		return dateDeadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<String> getKickStarter(){
		List<String> result = new ArrayList<String>();
		result.add(id.toString());
		result.add(title);
		result.add(description);
		result.add(author);
		result.add(backers.toString());
		result.add(pledge.toString());
		result.add(goal.toString());
		result.add(deadline.toString());
		return result;
	}
	
	public List<String> getKickStopper(){
		List<String> result = new ArrayList<String>();
		result.add(antiBackers.toString());
		result.add(antiPledge.toString());
		result.add(antiGoal.toString());
		return result;
	}
}
