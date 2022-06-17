package com.adoptacat.service;

import com.adoptacat.model.Role;
import com.adoptacat.model.User;
import com.adoptacat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
		
	
	@Override
	public void save(User user) {
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));	
         user.setRoles(new HashSet<>(user.getRoles()));
         userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
    	   return userRepository.findByUserName(username);
	}


	@Transactional(readOnly = true)
	public List<HashMap<String, String>> findAll() throws UsernameNotFoundException {

		List<HashMap<String, String>> userDetails = new ArrayList<>();
		HashMap<String, String> currUser;
		String details;

		try {

			List<User> users = userRepository.findAll();
			StringBuilder roles = new StringBuilder();

			for(User user: users) {

				for (Role role : user.getRoles()) {
					roles.append(role.getName());
				}

				details = "Nombre: " + user.getName() + "-  Usuario: " + user.getUserName() + "-  Rol:  " + roles.toString();
				roles.replace(0, roles.length(), "");

				currUser = new HashMap<>();

				currUser.put("userName", user.getUserName());
				currUser.put("details", details);


				userDetails.add(currUser);
			}


		} catch (Exception ex) {
			throw new UsernameNotFoundException("User doesn't exist");
		}

		return userDetails;
	}

	@Override
	public void deleteUserByUserName(String userName) {
		userRepository.deleteByUserName(userName);
	}


}
