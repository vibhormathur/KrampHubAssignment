package com.kramphub.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * System controller that processes the request control.
 * @author vmathu0
 *
 */
@Controller
public class BaseController {
	private static final String VIEW_INDEX = "index";
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * Method to fetch data for albums and books corresponding to input parametes
	 * @param term (Required)
	 * @param limit (Optional. Default = 5)
	 * @param model
	 * @return String: json string
	 *
	 */
	@RequestMapping(value="getData", method = RequestMethod.GET, produces = "application/json")
	public String getData(@RequestParam(value="search") String term, @RequestParam(value="limit",required = false) Integer limit, ModelMap model) {
		long startTime = System.currentTimeMillis();
		long elapsedTime = startTime;
		logger.debug("[Controller] term : {}", term);
		logger.debug("[Controller] limit : {}", limit);
		// If search limit is not provided in input then default it to 5
		if(null == limit)
			limit = 5;
		
		String jsonInString = "No result";
		ControllerHelper helper = new ControllerHelper();
		try {
			// Using helper call to call components and get data from corresponding services
			jsonInString = helper.callComponents(term, limit);
		}catch (JsonProcessingException e) {
			logger.debug("[Controller] Exception while writeValueAsString");
			model.addAttribute("message", "Something went wrong! Please try again.");
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("[Controller] Total elapsed time in milliseconds: " + elapsedTime);
			// Return back index.jsp
			return VIEW_INDEX; 
		}

		model.addAttribute("message", jsonInString);
		logger.debug("[Controller] jsonInString : {}", jsonInString);
		elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("[Controller] Total elapsed time in milliseconds: " + elapsedTime);
		// Return back index.jsp
		return VIEW_INDEX;
	}

	/**
	 * Method to check server resource status
	 * @param locale
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = { "/", "/health", }, method = RequestMethod.GET)
	@ResponseBody 
	public ResponseEntity<String> healthCheck(Locale locale) {
		logger.info("Health Check for resources: " + locale.toString());
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(new Date());
		return new ResponseEntity<String>("Project server is running. The time on the server is: "
				+ formattedDate, HttpStatus.OK);
	}
}