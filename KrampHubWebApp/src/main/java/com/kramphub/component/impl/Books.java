package com.kramphub.component.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.component.Component;
import com.kramphub.entity.RespEntity;
import com.kramphub.util.ConnectionUtility;

/**
 * Implementation of component for processing search for books data
 * @author vmathu0
 *
 */
public class Books implements Component{
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Books.class);
	private final static String GOOGLEBOOKS_URI = "https://www.googleapis.com/books/v1/volumes?printType=books&projection=lite";

	@Override
	public List<RespEntity> execute(String term, Integer limit) {
		List<RespEntity> bookList = new ArrayList<RespEntity>();
		String uri = GOOGLEBOOKS_URI + "&q="+term+"&maxResults="+Integer.toString(limit);
		logger.debug("[Books] uri : {}", uri);
		String result = null;
		long startTime = System.currentTimeMillis();
		long elapsedTime = startTime;
		try{
			// Using utilty to request to upstream service to fetch requested data
			result = ConnectionUtility.connectApis(uri);
			elapsedTime = System.currentTimeMillis() - startTime;
		}catch (Exception e) {
			logger.debug("[Books] Exception while getForObject");
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("[Books] Total elapsed upstream time in milliseconds: " + elapsedTime);
			return bookList;
		}	
		System.out.println("[Books] Total elapsed upstream time in milliseconds: " + elapsedTime);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			// Reading data node values from response jsonstring
			root = mapper.readValue(result.getBytes("UTF-8"), JsonNode.class); 
		} catch (JsonProcessingException e) {
			logger.debug("[Books] JsonProcessingException while readValue");
			return bookList;
		} catch (IOException e) {
			logger.debug("[Books] IOException while readValue");
			return bookList;
		} catch (Exception e) {
			logger.debug("[Books] Exception while getForObject");
			return bookList;
		}

		if(null == root)
			return bookList;
		JsonNode items = root.get("items");
		int idx = 0;
		JsonNode oneResult = null;
		JsonNode artistResult = null;

		if (items != null)
		{
			// Iterating over the Json nodes to fetch the required data nodes
			// and creating response entities.
			while ((oneResult = items.get(idx)) != null)
			{
				JsonNode volumeNode = oneResult.get("volumeInfo");
				JsonNode authorNode = volumeNode.get("authors");
				String title = volumeNode.get("title").asText();

				int artistIndex = 0;
				List<String> artist = new ArrayList<String>();
				// Iterating over author names
				if (authorNode != null)
				{			
					while ((artistResult = authorNode.get(artistIndex)) != null)
					{
						String artistName = artistResult.asText();
						artist.add(artistName);
						artistIndex++;
					}
				}else{
					artist.add("Author details not available");
				}
				RespEntity entity = new RespEntity();
				entity.setArtist(artist);
				entity.setTitle(title);
				entity.setKind("Book");
				bookList.add(entity);
				idx++;
			}
		}
		logger.debug("[Books] bookList size : {}", bookList.size());
		return bookList;
	}

}
