package com.banter.hack.rest.processor;

import org.springframework.http.HttpStatus;

public interface BantInterface {
	
	
	/*
	 * @method getListOfProjects - Retrieve JSON String of project data
	 * @param projectId - to retrieve projects by projectId
	 */
	public String getProject(String projectId);

	/*
	 * @method getListOfProjects - Retrieve JSON list of projects with selected keyword 
	 * @param searchTerm - to retrieve projects by keyword
	 */
	public String getListOfProjects(String searchTerm);
	
	/*
	 * @method processProjectContribution - updates project with new (anti) contribution  
	 * @param projectId - the project to be updated
	 * @param contribution - the monetary contribution ( e.g 100 = 1.00 pounds)
	 * @return HttpStatus - Status determining whether successful
	 */
	public HttpStatus processProjectContribution(String projectId, String contribution);
	
}
