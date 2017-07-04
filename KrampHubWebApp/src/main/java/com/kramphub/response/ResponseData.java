/**
 * Response data that contains list of result albums and books.
 */
package com.kramphub.response;

import java.util.List;

import com.kramphub.entity.RespEntity;

public class ResponseData {
	List<RespEntity> albums;
	List<RespEntity> books;


	public List<RespEntity> getAlbums() {
		return albums;
	}
	public void setAlbums(List<RespEntity> albums) {
		this.albums = albums;
	}
	public List<RespEntity> getBooks() {
		return books;
	}
	public void setBooks(List<RespEntity> books) {
		this.books = books;
	}

}
