package it.uniroma3.siw.the_something_awful.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.the_something_awful.model.Credentials;
import it.uniroma3.siw.the_something_awful.model.User;
import it.uniroma3.siw.the_something_awful.service.CredentialsService;
import it.uniroma3.siw.the_something_awful.service.UserService;
import static it.uniroma3.siw.the_something_awful.model.Credentials.DEFAULT_ROLE;
import jakarta.validation.Valid;


@Controller
public class AuthController {
	
	@Autowired
	private CredentialsService cs;
	@Autowired
	private UserService us;
	@Autowired
    private PasswordEncoder passwordEncoder; 
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newCredentials", new Credentials());
		return "registrationForm";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "loginForm";
	}
	
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = cs.getCredentialsByUsername(userDetails.getUsername());
    	if (credentials.getRole().equals("ADMIN")) {
            return "/mono/mono_home";
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("newUser") User user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("newCredentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            us.saveUser(user);

            // 🔥 encode raw password before saving
            user.setCredentials(credentials);
            credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
            credentials.setUser(user);
            credentials.setRole(DEFAULT_ROLE);
            cs.saveCredentials(credentials);

            model.addAttribute("newUser", user);
            return "registrationSuccessful";
        }
        return "registrationForm";
    }}
