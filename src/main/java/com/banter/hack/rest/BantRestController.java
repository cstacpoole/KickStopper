package com.banter.hack.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.banter.hack.rest.processor.BantProcessor;

@RestController
@RequestMapping("/api/projects")
public class BantRestController {

	/*
	 * GET request for a single project id
	 * @param Unique single project ID to retrieve data for
	 * @return JSON representation of the kickstarter/kickstopper values
	 */
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getSingleProject(@PathVariable(value = "id") String id) {
		return new BantProcessor().getProject(id);
	}


	/*
	 * POST request if a user pledges a contribution
	 * @param projects id and contribution amount
	 * @returns HTTP status OK if succeeds else error
	 * changed money to path variable for last min fix
	 */
	@CrossOrigin
	@RequestMapping(value = { "/{id}/contribute/{money}" }, method = RequestMethod.POST)
	public HttpStatus addProjectContribution(@PathVariable(value = "id") String id,
			@PathVariable(value = "money") String contribution) {
		return new BantProcessor().processProjectContribution(id, contribution);
	}


	/*
	 * GET method for searching projects
	 * @param search term for projects
	 * @return JSON list of projects - just proxies kickstarters JSON response
	 */
	@CrossOrigin
	@RequestMapping(value = { "/search/{searchterm}" }, method = RequestMethod.GET)
	public String searchProject(@PathVariable(value = "searchterm") String searchTerm) {
		return new BantProcessor().getListOfProjects(searchTerm);
	}


}
