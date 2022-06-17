package com.adoptacat;

import com.adoptacat.model.Role;
import com.adoptacat.model.User;
import com.adoptacat.service.RoleService;
import com.adoptacat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * EventListenerBean class - Class for Spring boot startup logic
 */
@Component
@PropertySource("classpath:application.properties")
public class EventListenerBean {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private Environment env;

	/** Method to register administrator info */
	@EventListener
	public void onApplicationEventRegisterAdministrator(ContextRefreshedEvent event) {

		try {
			String adminName = env.getProperty("gato.defaultAdminUserName");
			String adminEmail = env.getProperty("gato.defaultAdminUserEmail");
			String adminPassword = env.getProperty("gato.defaultAdminUserPassword");

			String applicantName = env.getProperty("gato.defaultApplicantUserName");
			String applicantEmail = env.getProperty("gato.defaultApplicantUserEmail");
			String applicantPassword = env.getProperty("gato.defaultApplicantUserPassword");

			String administrator = env.getProperty("gato.administrator");
			String applicant = env.getProperty("gato.applicant");

			if (roleService.findAll().size() == 0) {
				Role role = new Role();
				role.setName(administrator);
				roleService.save(role);

				role = new Role();
				role.setName(applicant);
				roleService.save(role);
			}

			addUsers(adminName, adminEmail, adminPassword, administrator);
			addUsers(applicantName, applicantEmail, applicantPassword, applicant);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addUsers(String name, String email, String pwd, String role) {

		if (userService.findByUsername(email) == null) {
			User user = new User();
			user.setName(name);
			user.setUserName(email);
			user.setPassword(pwd);
			user.setPasswordConfirm(pwd);

			Set<Role> roles = new HashSet<>();

			Role defineRole = roleService.findByName(role);
			roles.add(defineRole);
			user.setRoles(roles);

			userService.save(user);
		}
	}
}
