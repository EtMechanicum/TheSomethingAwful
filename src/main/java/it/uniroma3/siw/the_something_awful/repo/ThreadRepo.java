package it.uniroma3.siw.the_something_awful.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.the_something_awful.model.Thread;
public interface ThreadRepo extends CrudRepository<Thread, Long>{

}
