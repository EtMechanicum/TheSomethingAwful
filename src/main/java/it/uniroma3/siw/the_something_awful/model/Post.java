package it.uniroma3.siw.the_something_awful.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {
	public static final String OP = "op";
	public static final String REPLY = "reply";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String content;
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	@ManyToOne
	@JoinColumn(name = "thread_id")
	private Thread thread;
	private String postType;
	private boolean deleted = false;
	
	public Post() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
