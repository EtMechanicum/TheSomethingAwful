package it.uniroma3.siw.the_something_awful.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.the_something_awful.model.Category;
import it.uniroma3.siw.the_something_awful.service.CategoryService;
import it.uniroma3.siw.the_something_awful.service.CredentialsService;
import it.uniroma3.siw.the_something_awful.service.ThreadService;
import jakarta.validation.Valid;
import it.uniroma3.siw.the_something_awful.model.Thread;
import it.uniroma3.siw.the_something_awful.model.dto.ThreadPostDTO;
import it.uniroma3.siw.the_something_awful.model.Credentials;
import it.uniroma3.siw.the_something_awful.model.Post;
import static it.uniroma3.siw.the_something_awful.model.Post.OP;
import static it.uniroma3.siw.the_something_awful.model.Post.REPLY;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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
	public String newThread(
	        @PathVariable("id") Long categoryId,
	        @Valid @ModelAttribute("threadPostDTO") ThreadPostDTO form,
	        BindingResult bindingResult, Model model) {

	    if(bindingResult.hasErrors()) {
			model.addAttribute("category", cs.getCategoryById(categoryId));
			model.addAttribute("threadPostDTO", form);
	        return "category";
	    }

	    Category category = cs.getCategoryById(categoryId);

	    UserDetails userDetails =
	        (UserDetails) SecurityContextHolder.getContext()
	        .getAuthentication().getPrincipal();

	    Credentials creatorCredentials =
	        credServ.getCredentialsByUsername(userDetails.getUsername());

	    Thread thread = new Thread();
	    thread.setTitle(form.getThreadTitle());
	    thread.setCategory(category);
	    thread.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
	    thread.setOfficial("ADMIN".equals(creatorCredentials.getRole()));
	    thread.setCreatedBy(creatorCredentials.getUser());

	    Post post = new Post();
	    post.setContent(form.getPostContent());
	    post.setCreatedAt(thread.getCreatedAt());
	    post.setAuthor(thread.getCreatedBy());
	    post.setThread(thread);
	    post.setPostType(OP);
	    post.setImageFileName(form.getPostImageFileName());

	    thread.getPosts().add(post);
	    ts.saveThread(thread);

	    return "redirect:/category/" + categoryId + "/threads";
	}

	
	@GetMapping("/threads/{id}")
	public String singleThread(@PathVariable ("id") Long threadId, Model model) {
		model.addAttribute("thread", ts.getThreadById(threadId));
		model.addAttribute("newPost", new Post());
		return "singleThread";
	}
	
	@PostMapping("/threads/{id}/posts/new") 
	public String newPost(@PathVariable("id") Long threadId,@Valid @ModelAttribute Post newPost, 
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("newPost", newPost);
			model.addAttribute("thread", ts.getThreadById(threadId));
			return "/singleThread";
		}
		/*Per ottenere l'user*/
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Per ottenere il thread*/
		Thread thread = ts.getThreadById(threadId);
		Post post = new Post();
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
		model.addAttribute("threadPostDTO", new ThreadPostDTO());
		return "/mono/threads";
	}
	 
	/*Per nuovo official thread*/
	@PostMapping("/mono/official_threads/new")
	public String newOfficialThread(@Valid @ModelAttribute("threadPostDTO") ThreadPostDTO form,
	        BindingResult bindingResult, Model model) {
		Category category = cs.getCategoryByName("MONO");
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Thread fields*/
		if(bindingResult.hasErrors()) {
			model.addAttribute("category", category);
			model.addAttribute("threadPostDTO", form);
	        return "/mono/threads";
	    }
		
	    Thread thread = new Thread();
	    thread.setTitle(form.getThreadTitle());
	    thread.setCategory(category);
	    thread.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
	    thread.setOfficial("ADMIN".equals(creatorCredentials.getRole()));
	    thread.setCreatedBy(creatorCredentials.getUser());

	    Post post = new Post();
	    post.setContent(form.getPostContent());
	    post.setCreatedAt(thread.getCreatedAt());
	    post.setAuthor(thread.getCreatedBy());
	    post.setThread(thread);
	    post.setPostType(OP);
	    post.setImageFileName(form.getPostImageFileName());

	    thread.getPosts().add(post);
	    ts.saveThread(thread);
	    
		return "redirect:/mono/official_threads/" + thread.getId();
	}
	/*Nuovo post*/
	@PostMapping("/mono/official_threads/{id}/posts/new") 
	public String newOfficialThreadPost(@PathVariable("id") Long threadId,@Valid @ModelAttribute Post newPost, 
				BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("newPost", newPost);
			model.addAttribute("thread", ts.getThreadById(threadId));
			return "/mono/monoSingleThread";
		}
		/*Per ottenere l'user*/
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Credentials creatorCredentials = credServ.getCredentialsByUsername(userDetails.getUsername());
		/*Per ottenere il thread*/
		Thread thread = ts.getThreadById(threadId);
		Post post = new Post();
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
		model.addAttribute("newPost", new Post());
		return "/mono/monoSingleThread";
	}
	
}
