package it.uniroma3.siw.the_something_awful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.the_something_awful.model.Category;
import it.uniroma3.siw.the_something_awful.repo.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo cr;
	
	public Iterable<Category> getAllCategories() {
		return cr.findAll();
	}
	
	public Category getCategoryById(Long id) {
		return cr.findById(id).get();
	}
}
