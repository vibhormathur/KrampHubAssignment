package com.kramphub.entity;

import java.util.List;

public class RespEntity {
	List<String> artist;
	String title;
	String kind;

	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public List<String> getArtist() {
		return artist;
	}
	public void setArtist(List<String> artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
}
