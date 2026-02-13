package it.uniroma3.siw.the_something_awful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.the_something_awful.model.Category;
import it.uniroma3.siw.the_something_awful.repo.ThreadRepo;
import it.uniroma3.siw.the_something_awful.model.Thread;
@Service
public class ThreadService {
	
	@Autowired
	private ThreadRepo tr;
	
	public Iterable<Thread> getThreadsByCategory(Category category) {
		return tr.findAllByCategoryOrderByCreatedAt(category);
	}
	
	public void saveThread(Thread thread) {
		tr.save(thread);
	}
	
	public Thread getThreadById(Long id) {
		return tr.findById(id).get();
	}
	
	public Iterable<Thread> getAllOfficialThreads() {
		return tr.findAllByIsOfficialTrueOrderByCreatedAt();
	}
}
