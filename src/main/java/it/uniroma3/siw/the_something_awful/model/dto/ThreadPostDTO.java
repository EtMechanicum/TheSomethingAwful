package it.uniroma3.siw.the_something_awful.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ThreadPostDTO {
	
	@NotBlank
	private String threadTitle;
	@NotBlank
	private String postContent;
	private String postImageFileName;
	
	public String getThreadTitle() {
		return threadTitle;
	}
	public void setThreadTitle(String threadTitle) {
		this.threadTitle = threadTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostImageFileName() {
		return postImageFileName;
	}
	public void setPostImageFileName(String postImageFileName) {
		this.postImageFileName = postImageFileName;
	}
	
	
}

