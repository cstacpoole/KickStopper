package com.banter.hack.scrape;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ScrapeURL {

	public String getURL(String uRL){
		String result = "";
		try {
			URL uRLObject = new URL(uRL);
	        BufferedReader in = new BufferedReader(new InputStreamReader(uRLObject.openStream()));
	        String inputLine = "";
			try{
		        while ((inputLine = in.readLine()) != null) {
		        	result = result+inputLine;
		        }
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
