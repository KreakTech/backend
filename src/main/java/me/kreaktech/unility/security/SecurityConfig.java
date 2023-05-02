package me.kreaktech.unility.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private BCryptPasswordEncoder passwordEncoder;

	public SecurityConfig() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.authorizeHttpRequests(authorize -> authorize
						.antMatchers(HttpMethod.GET).permitAll()
						.antMatchers(HttpMethod.POST).hasRole("SCRAPER")
						.antMatchers(HttpMethod.PUT).hasRole("SCRAPER")
						.antMatchers(HttpMethod.DELETE).hasRole("SCRAPER")
						.anyRequest().authenticated())
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	public UserDetailsService users() {
		UserDetails scraper = User.builder()
				.username("scraper")
				.password(passwordEncoder.encode(SecurityConstants.SECRET_KEY))
				.roles("SCRAPER")
				.build();

		return new InMemoryUserDetailsManager(scraper);
	}


	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(users());
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

}
