package com.adoptacat.service;

import com.adoptacat.model.Role;
import com.adoptacat.model.User;
import com.adoptacat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.User loadUserByUsername(String userName) {

    	try {
			User user = userRepository.findByUserName(userName);

			Set<GrantedAuthority> authorities = new HashSet<>();
			for (Role role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}

			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);

		} catch (Exception ex) {
			throw new UsernameNotFoundException(userName + " doesn't exist");
		}

    }


}
