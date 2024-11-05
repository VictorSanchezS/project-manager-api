package com.victorsanchez.projectmanager.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.victorsanchez.projectmanager.entity.User;

public interface UserService {
	public List<User> findAll(Pageable page);
	public List<User> findByName(String name, Pageable page);
	public User findById(int id);
	public User save(User user);
	public User update(User user);
	public void delete(int id);
}
