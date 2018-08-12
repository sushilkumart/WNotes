package com.wander.notes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author sushil Security Configuration class is used to override the default
 *         configurations of spring security.
 */
@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userService;

	/**
	 * Method is to define the custom login and logout. It also defines the url
	 * which needs authentication and authorization.
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().formLogin();

		http.authorizeRequests().antMatchers("/secured/*").authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/secured/notes", true).permitAll().and().logout().logoutSuccessUrl("/login")
				.permitAll();

		http.exceptionHandling().accessDeniedPage("/403");

	}

	/**
	 * This meothod is used to configure the url that can be bypassed with user
	 * authentication.
	 */

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/add");
	}

	/**
	 * Method defines Encryption mechanism that is used to encrypt or decrypt
	 * password.
	 * 
	 * @return
	 */

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 */

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userService);
	}

}
