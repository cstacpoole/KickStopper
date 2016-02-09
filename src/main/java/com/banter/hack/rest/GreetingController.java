package com.banter.hack.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

	/*
	 *  redirects unused URLS to index.html file
	 */
    @RequestMapping(value={"/", "*"})
    public String greeting() {
        return "index";
    }
    
}
