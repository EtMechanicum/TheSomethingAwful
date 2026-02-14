package it.uniroma3.siw.the_something_awful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.the_something_awful.model.Creature;
import it.uniroma3.siw.the_something_awful.model.CreatureStatus;
import it.uniroma3.siw.the_something_awful.model.ThreatLevel;

@Controller
public class CreatureController {
	
	@GetMapping("/mono/creature/new")
	public String newCreature(Model model) {
		model.addAttribute("newCreature", new Creature());
		model.addAttribute("creatureStatuses", CreatureStatus.values());
		model.addAttribute("threatLevels", ThreatLevel.values());
		return "newCreature";
	}
}
