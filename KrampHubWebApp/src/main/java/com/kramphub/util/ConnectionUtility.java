package com.kramphub.util;

import org.springframework.web.client.RestTemplate;

public final class ConnectionUtility {
	public static String connectApis(String uri) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		String result = null;
		// Making request to upstream service to fetch requested data
		result = restTemplate.getForObject(uri, String.class);
		return result;	
	}
}
