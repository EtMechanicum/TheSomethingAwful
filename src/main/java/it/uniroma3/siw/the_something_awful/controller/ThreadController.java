package it.uniroma3.siw.the_something_awful.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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
import static it.uniroma3.siw.the_something_awful.model.Post.REPLY;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
	public String newThread(@PathVariable("id") Long categoryId, @RequestParam String imageFileName,
			@RequestParam String threadTitle, @RequestParam String postContent) {
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
		post.setImageFileName(imageFileName);
		/*Save*/
		thread.getPosts().add(post);
		ts.saveThread(thread);
		return "redirect:/category/" + categoryId + "/threads";
	}
	
	@GetMapping("/threads/{id}")
	public String singleThread(@PathVariable ("id") Long threadId, Model model) {
		model.addAttribute("thread", ts.getThreadById(threadId));
		return "singleThread";
	}
	
	@PostMapping("/threads/{id}/posts/new") 
	public String newPost(@PathVariable("id") Long threadId, @RequestParam String postContent) {
		/*Per ottenere l'user*/
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Per ottenere il thread*/
		Thread thread = ts.getThreadById(threadId);
		Post post = new Post();
		post.setContent(postContent);
		post.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
		post.setAuthor(creatorCredentials.getUser());
		post.setPostType(REPLY);
		post.setThread(thread);
		/*Aggiungo il post al thread*/
		thread.getPosts().add(post);
		/*Salvo Thread perché il cascade per Post*/
		ts.saveThread(thread);
		return "redirect:/threads/" + threadId;
	}
	
	/*Metodi relativi a MONO*/
	
	@GetMapping("/mono-files")
	public String monoFiles() {
		return "monoFiles";
	}
	
	@GetMapping("/mono/official_threads") //Solo i MONO Agents possono scrivere qui
	public String officialThreads(Model model) {
		model.addAttribute("threads", ts.getAllOfficialThreads());
		model.addAttribute("category", cs.getCategoryByName("MONO THREADS"));
		return "/mono/threads";
	}
	
	@PostMapping("/mono/official_threads/new")
	public String newOfficialThread(@RequestParam String imageFileName,
			@RequestParam String threadTitle, @RequestParam String postContent) {
		Category category = cs.getCategoryByName("MONO");
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Thread fields*/
		Thread thread = new Thread();
		thread.setTitle(threadTitle);
		thread.setCategory(category);
		thread.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
		thread.setOfficial(true);
		thread.setCreatedBy(creatorCredentials.getUser());
		/*Post fields*/
		Post post = new Post();
		post.setContent(postContent);
		post.setCreatedAt(thread.getCreatedAt());
		post.setAuthor(thread.getCreatedBy());
		post.setThread(thread);
		post.setPostType(OP);
		post.setImageFileName(imageFileName);
		/*Save*/
		thread.getPosts().add(post);
		ts.saveThread(thread);
		return "redirect:/mono/official_threads/" + thread.getId();
	}
	
	@PostMapping("/mono/official_threads/{id}/posts/new") 
	public String newOfficialThreadPost(@PathVariable("id") Long threadId, @RequestParam String postContent) {
		/*Per ottenere l'user*/
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Per ottenere il thread*/
		Thread thread = ts.getThreadById(threadId);
		Post post = new Post();
		post.setContent(postContent);
		post.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
		post.setAuthor(creatorCredentials.getUser());
		post.setPostType(REPLY);
		post.setThread(thread);
		/*Aggiungo il post al thread*/
		thread.getPosts().add(post);
		/*Salvo Thread perché il cascade per Post*/
		ts.saveThread(thread);
		return "redirect:/mono/official_threads/" + threadId;
	}
	
	@GetMapping("/mono/official_threads/{id}")
	public String singleOfficialThread(@PathVariable ("id") Long threadId, Model model) {
		model.addAttribute("thread", ts.getThreadById(threadId));
		return "singleThread";
	}
	
}
