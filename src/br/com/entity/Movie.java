package br.com.entity;

import java.nio.file.Path;

public class Movie {
	
	private String movieName;
	private String fileName;
	private String type;
	private Path path;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String name) {
		this.fileName = name;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
}
