package it.uniroma3.siw.the_something_awful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.the_something_awful.model.Creature;
import it.uniroma3.siw.the_something_awful.repo.CreatureRepo;

@Service
public class CreatureService {
	
	@Autowired
	private CreatureRepo cr;
	
	public void saveCreature(Creature creature) {
		cr.save(creature);
	}
	
	public Creature getCreatureById(Long id) {
		return cr.findById(id).get();
	}
	
	public Iterable<Creature> getAllCreatures() {
		return cr.findAll();
	}
	
	public boolean ExistsByCodeNameAndName(String codeName, String name) {
		return cr.existsByCodeNameAndName(codeName, name);
	}
	
	public void deleteCreature(Creature creature) {
		cr.delete(creature);
	}
}
