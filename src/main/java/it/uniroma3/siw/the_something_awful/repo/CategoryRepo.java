package it.uniroma3.siw.the_something_awful.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.the_something_awful.model.Category;

public interface CategoryRepo extends CrudRepository<Category, Long>{
	
	public Category findByName(String name);
	
	public Iterable<Category> findAllExceptMONO();

}
