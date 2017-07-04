package com.kramphub.controller;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.component.Component;
import com.kramphub.component.impl.Albums;
import com.kramphub.component.impl.Books;
import com.kramphub.entity.RespEntity;
import com.kramphub.response.Response;
import com.kramphub.response.ResponseData;

public class ControllerHelper {
	public String callComponents(String term, Integer limit) throws JsonProcessingException{
		Response response = new Response();
		ResponseData respData = new ResponseData();
		// Creating component instances
		Component albumComponent = new Albums();
		Component bookComponent = new Books();
		// Executing the components to get album and books data from upstream services
		List<RespEntity> albums = albumComponent.execute(term, limit);
		List<RespEntity> books = bookComponent.execute(term, limit);
		respData.setAlbums(albums);
		respData.setBooks(books);

		if((null == albums && null == books) || (albums.size() <= 0 && books.size() <= 0 )){
			response.setStatus(404);
		}else{
			response.setData(respData);
			response.setStatus(200);
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(response);
		return jsonString;
	}
}
