package it.uniroma3.siw.the_something_awful.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") //"user" is a keyword in postgres
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private String email;
	@OneToOne
	private Credentials credentials;
	@OneToMany(mappedBy = "author")
	private List<Post> posts = new ArrayList<Post>();
	@OneToMany(mappedBy = "createdBy")
	private List<Thread> threads = new ArrayList<Thread>();
	@OneToMany(mappedBy = "certifiedBy")
	private List<Creature> certifiedCreatures = new ArrayList<>();
	
	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
}
