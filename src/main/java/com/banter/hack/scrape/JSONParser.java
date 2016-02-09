package com.banter.hack.scrape;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class JSONParser {
	
	public List<Project> parseJSONToProjectList(String JSON){
		List<Project> projectList = new ArrayList<Project>();
		JSONObject jsonMasterObj = new JSONObject(JSON);
		JSONArray arr = jsonMasterObj.getJSONArray(ProjectConstants.PROJECTS);
		
		for (int i = 0; i < arr.length(); i++) {
			Project project = new Project();
			JSONObject jsonObject = arr.getJSONObject(i);
			String title = jsonObject.getString(ProjectConstants.NAME);
			String description = jsonObject.getString(ProjectConstants.BLURB);
			Integer backers = jsonObject.getInt(ProjectConstants.BACKERS_COUNT);
			Integer pledge = jsonObject.getInt(ProjectConstants.PLEDGED);
			Double goal = jsonObject.getDouble(ProjectConstants.GOAL);
			Integer deadline = jsonObject.getInt(ProjectConstants.DEADLINE);
			Integer id = jsonObject.getInt(ProjectConstants.ID);
			String author = jsonObject.getJSONObject(ProjectConstants.CREATOR).getString(ProjectConstants.NAME);
			String picture = jsonObject.getJSONObject(ProjectConstants.PICTURE_P).getString(ProjectConstants.PICTURE_C);;
			
			project.setAuthor(cleanString(author));
			project.setBackers(backers);
			project.setDeadline(deadline);
			project.setDescription(cleanString(description));
			project.setGoal(goal);
			project.setId(id);
			project.setPledge(pledge);
			project.setTitle(cleanString(title));
			project.setPicture(picture);
			projectList.add(project);
		}
		return projectList;
	}
	
	public String parseProjectListToJSON(List<Project> projectList){
		String split = ":";
		String result = "";
		String jsonBegin = "{"+containQuotes(ProjectConstants.PROJECTS)+split+"[";
		String jsonEnd = "]}";
		
		result = jsonBegin;
		for(int i=0;i<projectList.size();i++){
			Project project = projectList.get(i);
			String id = containQuotes(ProjectConstants.ID)+split+project.getId();
			String name = containQuotes(ProjectConstants.NAME)+split+containQuotes(project.getTitle());
			String desc = containQuotes(ProjectConstants.BLURB)+split+containQuotes(project.getDescription());
			String author = containQuotes(ProjectConstants.AUTHOR)+split+containQuotes(project.getAuthor());
			String backers = containQuotes(ProjectConstants.BACKERS_COUNT)+split+project.getBackers();
			String pledge = containQuotes(ProjectConstants.PLEDGED)+split+project.getPledge();
			String goal = containQuotes(ProjectConstants.GOAL)+split+project.getGoal();
			String deadline = containQuotes(ProjectConstants.DEADLINE)+split+containQuotes(project.getDeadline());
			String antiBackers = containQuotes(ProjectConstants.ANTIBACKERS)+split+project.getAntiBackers();
			String antiPledge = containQuotes(ProjectConstants.ANTIPLEDGE)+split+project.getAntiPledge();
			String antiGoal = containQuotes(ProjectConstants.ANTIGOAL)+split+project.getAntiGoal();
			String picture = containQuotes(ProjectConstants.PICTURE_P)+split+containQuotes(project.getPicture());
			
			String[] startNode = {id,name,desc,author,backers,pledge,goal,deadline,picture};
			String[] stopNode = {antiBackers,antiGoal,antiPledge};
			
			String kickstartNode = createKickNode(ProjectConstants.KICKSTARTER,startNode,split);
			String kickstopNode = createKickNode(ProjectConstants.KICKSTOPPER,stopNode,split);
			String finalNode = kickstartNode+","+kickstopNode;
			finalNode = encaseNode(finalNode);
			
			if(i!=(projectList.size()-1)){
				finalNode=finalNode+",";
			}
			result=result+finalNode;
		}
		result=result+jsonEnd;
		return result;
	}
	
	private String cleanString(String data){
		return data.replaceAll("\"", "");
	}
	
	private String encaseNode(String data){
		String result = "";
		result = "{"+data+"}";
		return result;
	}
	
	private String createKickNode(String parent, String[] child, String split){
		String result = "";
		String dataParent = containQuotes(parent)+split;
		String dataChild = "";
		for(int i=0;i<child.length;i++){
			dataChild=dataChild+child[i];
			if(i!=(child.length-1)){
				dataChild=dataChild+",";
			}
		}
		dataChild = encaseNode(dataChild);
		result = dataParent+dataChild;
		return result;
	}
	
	private String containQuotes(String tag){
		String result = "\""+tag+"\"";
		return result;
	}
}
