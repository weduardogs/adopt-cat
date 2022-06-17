package com.adoptacat.repository;

import com.adoptacat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUserName(String userName);

	public List<User> findAll();

	@Transactional
	public void deleteByUserName(String userName);

}
