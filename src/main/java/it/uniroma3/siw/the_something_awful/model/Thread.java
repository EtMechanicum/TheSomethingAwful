package it.uniroma3.siw.the_something_awful.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Thread {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private LocalDateTime createdAt;
	private boolean isOfficial;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<Post>();
	@Enumerated(EnumType.STRING)
	private ThreadStatus status = ThreadStatus.OPEN; //aperto di default. Owner può chiuderlo, ma non riaprirlo. 
	
	public Thread() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isOfficial() {
		return isOfficial;
	}

	public void setOfficial(boolean isOfficial) {
		this.isOfficial = isOfficial;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public ThreadStatus getStatus() {
		return status;
	}

	public void setStatus(ThreadStatus status) {
		this.status = status;
	}
	
}
