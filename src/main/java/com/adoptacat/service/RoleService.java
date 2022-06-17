package com.adoptacat.service;

import com.adoptacat.model.Role;

import java.util.List;

public interface RoleService {
	Role save(Role role);
	Role findByName(String name);
	List<Role> findAll();
}
