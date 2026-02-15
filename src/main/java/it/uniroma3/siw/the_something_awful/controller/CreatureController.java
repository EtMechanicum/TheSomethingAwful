package it.uniroma3.siw.the_something_awful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.the_something_awful.controller.validator.CreatureValidator;
import it.uniroma3.siw.the_something_awful.model.Creature;
import it.uniroma3.siw.the_something_awful.model.CreatureStatus;
import it.uniroma3.siw.the_something_awful.model.ThreatLevel;
import it.uniroma3.siw.the_something_awful.service.CreatureService;
import it.uniroma3.siw.the_something_awful.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class CreatureController {
	
	@Autowired
	private CreatureService cs;
	@Autowired
	private CredentialsService credServ;
	@Autowired
	private CreatureValidator creatureValidator;
	
	@GetMapping("/mono/anomaly/new")
	public String newCreature(Model model) {
		model.addAttribute("newCreature", new Creature());
		model.addAttribute("creatureStatuses", CreatureStatus.values());
		model.addAttribute("threatLevels", ThreatLevel.values());
		return "/mono/newCreature";
	}
	
	@PostMapping("/mono/anomaly/new")
	public String singleAnomaly(@Valid @ModelAttribute Creature newCreature,
			BindingResult bindingResult, Model model) {
		/*Collegamento anomaly - user*/
		this.creatureValidator.validate(newCreature, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("newCreature", newCreature);
			return "/mono/newCreature";
		}
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		newCreature.setCertifiedBy(credServ.getCredentialsByUsername(userDetails.getUsername()).getUser());
		cs.saveCreature(newCreature);
		return "redirect:/mono/anomaly/" + newCreature.getId();
		
	}
	
	@GetMapping("/mono/anomaly/{id}")
	public String singleAnomaly(@PathVariable Long id, Model model) {
		Creature creature = cs.getCreatureById(id);
		model.addAttribute("creature", creature);
		return "/mono/creature";
	}
	
	
	@GetMapping("/mono/anomalies")
	public String anomalies(Model model) {
		model.addAttribute("anomalies", cs.getAllCreatures());
		return "/mono/anomalies";
	}
	
	@GetMapping("/about") 
	public String about() {
		return "about";
	}
	
	@PostMapping("/mono/anomaly/{id}/delete")
	public String deleteCreature(@PathVariable Long id) {
		cs.deleteCreature(cs.getCreatureById(id));
		return "redirect:/mono/anomalies";
	}
	
}
