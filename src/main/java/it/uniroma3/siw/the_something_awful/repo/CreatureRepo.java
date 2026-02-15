package it.uniroma3.siw.the_something_awful.repo;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.the_something_awful.model.Creature;

public interface CreatureRepo extends CrudRepository<Creature, Long>{
	
	public boolean existsByCodeNameAndName(String codeName, String name);
}
