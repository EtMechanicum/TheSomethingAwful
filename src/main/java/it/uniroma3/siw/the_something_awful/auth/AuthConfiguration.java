package it.uniroma3.siw.the_something_awful.auth;

import static it.uniroma3.siw.the_something_awful.model.Credentials.ADMIN_ROLE;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
public class AuthConfiguration {

	/*
	 * @Autowired private DataSource dataSource;
	 * 
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception { auth.jdbcAuthentication() .dataSource(dataSource)
	 * .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?"
	 * )
	 * .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?"
	 * ); }
	 */

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
            .csrf(withDefaults())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET,
                    "/", "/home","/register","/login","/css/**", "/images/**", "/favicon.ico"
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/mono/mono_agents/**").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/mono/**").hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
            );

        return httpSecurity.build();
    }

}
