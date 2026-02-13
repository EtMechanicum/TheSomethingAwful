package it.uniroma3.siw.tsa.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tsa.model.Post;

public interface PostRepo extends CrudRepository<Post, Long>{

}
