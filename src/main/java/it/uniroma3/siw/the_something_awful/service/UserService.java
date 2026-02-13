package it.uniroma3.siw.the_something_awful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.the_something_awful.model.User;
import it.uniroma3.siw.the_something_awful.repo.UserRepo;


@Service
public class UserService {
	
	@Autowired
	private UserRepo ur;
	
	public User getUserById(Long id) {
		return ur.findById(id).get();
	}
	
	public User saveUser(User user) {
		return ur.save(user);
	}
	
	public Iterable<User> getAllUsers() {
		return ur.findAll();
	}
}
