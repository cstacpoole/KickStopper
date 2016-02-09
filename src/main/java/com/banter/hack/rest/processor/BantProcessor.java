package com.banter.hack.rest.processor;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.banter.hack.jdbc.JDBCPreparedStatementInsert;
import com.banter.hack.jdbc.JDBCPreparedStatementSelect;
import com.banter.hack.scrape.JSONParser;
import com.banter.hack.scrape.Project;
import com.banter.hack.scrape.Scrape;

public final class BantProcessor implements BantInterface {

	/*
	 * Get Project
	 * @param project id
	 * @return JSON representation of the kickstarter/kickstopper values
	 */
	@Override
	public String getProject(String projectId) {
		System.out.println("GET request : get project: " + projectId);
		return cleanString(getJsonFromProject(projectId));
	}

	/*
	 * Get JSON String of Project
	 * @param project id
	 * @return JSON representation of the kickstarter/kickstopper values
	 */
	private String getJsonFromProject(String id) {
		Scrape scrape = new Scrape();
		String ajaxSearch = scrape.createAJAX(id);
		List<Project> kickstarterScrape = scrape.getKickstarterScrape(ajaxSearch);
		
		System.out.println("ajax = " + ajaxSearch);
		System.out.println("scrape size = " + kickstarterScrape.size());
		if(!kickstarterScrape.isEmpty()) {
			System.out.println(id);
			
			//This is bad - basically kickstarters API we used just returns a list of 
			//projects using a search term, so when narrowing it to 1 result we had to 
			//try and find the project we wanted in the list - could have been fixed by using the
			//actual ID instead of the name then looking in the JSON for the ID
			
			kickstarterScrape.removeIf(r->r.getTitle().contains(id));
			if(!kickstarterScrape.isEmpty()) {
			
				Project project = kickstarterScrape.get(0);
				
				try {
					JDBCPreparedStatementSelect.loadProjectAttFromOurTable(project);
				} catch (SQLException e) {
					project.setAntiBackers(0);
					project.setAntiPledge(0);
				}
				
				if(project.getGoal() > 0) {
					project.setAntiGoal((int) (project.getGoal() * 0.1));
				}
				try {
				kickstarterScrape.removeIf(r->r.getId() == Integer.parseInt(id));
				} catch (NumberFormatException nf) {
				}
			}
				return  cleanString(new JSONParser().parseProjectListToJSON(kickstarterScrape));
		}
		
		return "";
	}

	/*
	 * Get search JSON
	 * @param search term for projects
	 * @return JSON list of projects - just rips kickstarters one
	 */
	@Override
	public String getListOfProjects(String searchTerm) {

		System.out.println("GET list : search term: " + searchTerm);
		Scrape scrape = new Scrape();
		String ajaxSearch = scrape.createAJAX(searchTerm);
		return cleanString(scrape.getJSON(ajaxSearch));
	}

	/*
	 * Processes the project contribution
	 * @returns HttpStatus OK or HTTPStatus service_unavailable if failed
	 */
	@Override
	public HttpStatus processProjectContribution(String projectId, String contribution) {
		System.out.println("POST request : add contribution project :" + projectId  + " contribution " + contribution);
		return updateProjectContribution(projectId, contribution) ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
	}

	/*
	 * Updates projects table with +1 "backer" and increased money value
	 * @param project Id and contribution amount
	 * @returns true if successful
	 */
	private boolean updateProjectContribution(String projectId, String contribution) {
		try {
			
			JDBCPreparedStatementInsert.insertUserPledge(projectId ,  contribution);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}

	private String cleanString(String value) {
		return value.replaceAll("\n", "");
	}
}
