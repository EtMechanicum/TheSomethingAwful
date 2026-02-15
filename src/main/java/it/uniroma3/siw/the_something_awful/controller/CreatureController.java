package it.uniroma3.siw.the_something_awful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.the_something_awful.model.Creature;
import it.uniroma3.siw.the_something_awful.model.CreatureStatus;
import it.uniroma3.siw.the_something_awful.model.ThreatLevel;
import it.uniroma3.siw.the_something_awful.service.CreatureService;

@Controller
public class CreatureController {
	
	@Autowired
	private CreatureService cs;
	
	@GetMapping("/mono/anomaly/new")
	public String newCreature(Model model) {
		model.addAttribute("newCreature", new Creature());
		model.addAttribute("creatureStatuses", CreatureStatus.values());
		model.addAttribute("threatLevels", ThreatLevel.values());
		return "/mono/newCreature";
	}
	
	@GetMapping("/mono/anomalies")
	public String anomalies(Model model) {
		model.addAttribute("anomalies", cs.getAllCreatures());
		return "/mono/anomalies";
	}
}
