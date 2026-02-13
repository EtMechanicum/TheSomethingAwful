package it.uniroma3.siw.the_something_awful.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.the_something_awful.model.User;

public interface UserRepo extends CrudRepository<User, Long>{

}
