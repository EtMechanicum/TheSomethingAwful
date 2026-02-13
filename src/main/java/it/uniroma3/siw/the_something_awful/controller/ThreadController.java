package it.uniroma3.siw.the_something_awful.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.the_something_awful.model.Category;
import it.uniroma3.siw.the_something_awful.service.CategoryService;
import it.uniroma3.siw.the_something_awful.service.CredentialsService;
import it.uniroma3.siw.the_something_awful.service.ThreadService;
import it.uniroma3.siw.the_something_awful.model.Thread;
import it.uniroma3.siw.the_something_awful.model.Credentials;
import it.uniroma3.siw.the_something_awful.model.Post;
import static it.uniroma3.siw.the_something_awful.model.Post.OP;

import org.springframework.stereotype.Controller;

@Controller
public class ThreadController {

	
	@Autowired 
	private ThreadService ts;
	@Autowired
	private CategoryService cs;
	@Autowired
	private CredentialsService credServ;
	
	/*Questo viene eseguito solo quando viene crato un nuovo thread*/
	/*Per i post successivi c'è un altro metodo*/
	@PostMapping("/category/{id}/thread/new")
	public String newThread(@PathVariable("id") Long categoryId, @RequestParam String threadTitle, @RequestParam String postContent) {
		Category category = cs.getCategoryById(categoryId);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Thread fields*/
		Thread thread = new Thread();
		thread.setTitle(threadTitle);
		thread.setCategory(category);
		thread.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
		if(creatorCredentials.getRole().equals("ADMIN")) thread.setOfficial(true);
		thread.setOfficial(false);
		thread.setCreatedBy(creatorCredentials.getUser());
		/*Post fields*/
		Post post = new Post();
		post.setContent(postContent);
		post.setCreatedAt(thread.getCreatedAt());
		post.setAuthor(thread.getCreatedBy());
		post.setThread(thread);
		post.setPostType(OP);
		/*Save*/
		thread.getPosts().add(post);
		ts.saveThread(thread);
		return "redirect:/category/" + categoryId + "/threads";
	}
	
	
	
	
}
