package com.adoptacat.service;

import com.adoptacat.model.Gato;
import com.adoptacat.model.User;
import java.util.HashMap;
import java.util.List;


public interface UserService {
	
	public void save(User user);

	public User findByUsername(String username);

	public List<HashMap<String, String>> findAll();

	void deleteUserByUserName(String username);

	
}
