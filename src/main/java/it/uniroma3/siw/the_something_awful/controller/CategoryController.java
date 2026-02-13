package it.uniroma3.siw.the_something_awful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.the_something_awful.service.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService cs;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("categories", cs.getAllCategories());
		return "home";
	}
	
	@GetMapping("/category/{id}/threads")
	public String category(@PathVariable Long id, Model model) {
		model.addAttribute("category", cs.getCategoryById(id));
		return "category";
	}
	
	/*
	 * @GetMapping("/test") public String test(Model model) {
	 * model.addAttribute("categories", cs.getAllCategories()); return "home"; }
	 */
}
