package it.uniroma3.siw.the_something_awful.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.the_something_awful.model.Creature;
import it.uniroma3.siw.the_something_awful.service.CreatureService;

@Component
public class CreatureValidator implements Validator {
	
	@Autowired
	private CreatureService cs;
	
	@Override
	public void validate(Object o, Errors errors) {
		Creature creature = (Creature) o;
		if(creature.getCodeName() != null &&
				creature.getName() != null && 
				cs.ExistsByCodeNameAndName(creature.getCodeName(), creature.getName())) {
			errors.reject("creature.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Creature.class.equals(clazz);
	}
}
