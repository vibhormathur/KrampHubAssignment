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
 * Implementation of component for processing search for album data
 * @author vmathu0
 *
 */
public class Albums implements Component{
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Albums.class);
	private final static String ITUNES_URI = "https://itunes.apple.com/search?media=music&entity=album";

	@Override
	public List<RespEntity> execute(String term, Integer limit) {
		List<RespEntity> albumList = new ArrayList<RespEntity>();
		String uri = ITUNES_URI + "&term="+term+"&limit="+Integer.toString(limit);
		logger.debug("[Albums] uri : {}", uri);
		String result = null;
		long startTime = System.currentTimeMillis();
		long elapsedTime = startTime;
		try{
			// Using utilty to request to upstream service to fetch requested data
			result = ConnectionUtility.connectApis(uri);
			elapsedTime = System.currentTimeMillis() - startTime;
		}catch (Exception e) {
			logger.debug("[Albums] Exception while getForObject");
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("[Albums] Total elapsed upstream time in milliseconds: " + elapsedTime);
			return albumList;
		}	
		System.out.println("[Albums] Total elapsed upstream time in milliseconds: " + elapsedTime);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			// Reading data node values from response jsonstring
			root = mapper.readValue(result.getBytes("UTF-8"), JsonNode.class); 
		} catch (JsonProcessingException e) {
			logger.debug("[Albums] JsonProcessingException while readValue");
			return albumList;
		} catch (IOException e) {
			logger.debug("[Albums] IOException while readValue");
			return albumList;
		} catch (Exception e) {
			logger.debug("[Albums] IOException while readValue");
			return albumList;
		}  

		if(null == root)
			return albumList;
		JsonNode results = root.get("results");
		int idx = 0;
		JsonNode oneResult = null;

		
		if (results != null)
		{
			// Iterating over the Json nodes to fetch the required data nodes
			// and creating response entities.
			while ((oneResult = results.get(idx)) != null)
			{
				String artistName = oneResult.get("artistName").asText();
				String collectionName = oneResult.get("collectionName").asText();
				List<String> artist = new ArrayList<String>();
				artist.add(artistName);
				RespEntity entity = new RespEntity();
				entity.setArtist(artist);
				entity.setTitle(collectionName);
				entity.setKind("Album");
				albumList.add(entity);
				idx++;
			}
		}
		logger.debug("[Albums] albumList size : {}", albumList.size());
		return albumList;
	}

}
