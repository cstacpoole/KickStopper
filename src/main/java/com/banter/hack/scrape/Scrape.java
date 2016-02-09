package com.banter.hack.scrape;

import java.util.List;

public class Scrape {
	
	private final String AJAX_SEARCH = "https://www.kickstarter.com/projects/search.json?search=&term=";

	public List<Project> getKickstarterScrape(String searchTerm){
		String ajaxSearch = createAJAX(searchTerm);
		String JSON = getJSON(ajaxSearch);
		List<Project> projectList = parseJSON(JSON);		
		return projectList;
	}
	
	public List<Project> parseJSON(String JSON){
		List<Project> projectList = new JSONParser().parseJSONToProjectList(JSON);
		return projectList;
	}
	
	public String createAJAX(String searchTerm){
		String result = "";
		result = AJAX_SEARCH+searchTerm;
		return result;
	}
	
	public String getJSON(String AJAX){
		String JSON = new ScrapeURL().getURL(AJAX);
		return JSON;
	}
}