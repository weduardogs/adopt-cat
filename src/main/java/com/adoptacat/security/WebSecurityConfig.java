package com.adoptacat.security;

import com.adoptacat.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JavaMailSender javaMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("eduardogs.1030@gmail.com");
        mailSender.setPassword("phtjvtjhepvoqksd");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/login", "/createUser", "/applicantsReport").permitAll()
				.antMatchers("/createUser", "/saveUser").permitAll()
				.antMatchers("/index").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/applyGato").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/showGatos").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/updatePost").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/updatePost/**").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/createPost").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/deletePost/**").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/uploads/**").hasAnyAuthority("ADMIN", "APPLY")

				.antMatchers("/showCatsByWeight/**").hasAnyAuthority("ADMIN", "APPLY")
				.antMatchers("/showUsers").hasAnyAuthority("ADMIN")
				.antMatchers("/editUser/**").hasAnyAuthority("ADMIN")
				.antMatchers("/deleteUser/**").hasAnyAuthority("ADMIN")
				.antMatchers("/showPostulaciones/**").hasAnyAuthority("ADMIN")
				.antMatchers("/showPostulacionesByPreviousCats").hasAnyAuthority("ADMIN")
				.antMatchers("/showPostulacionesBySpace").hasAnyAuthority("ADMIN")
				.antMatchers("/acceptAdoption").hasAnyAuthority("ADMIN")
				.antMatchers("/catsReport").hasAnyAuthority("ADMIN")
				.antMatchers("/applicantsReport/**").hasAnyAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.usernameParameter("userName").passwordParameter("password").permitAll()
				.failureUrl("/login?error")
				.defaultSuccessUrl("/index").permitAll()
				.and()
				.logout().logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout").permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/index");
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
		auth.eraseCredentials(false);
		auth.authenticationProvider(authenticationProvider());
	}


}
