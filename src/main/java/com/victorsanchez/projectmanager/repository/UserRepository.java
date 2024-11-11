package com.victorsanchez.projectmanager.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victorsanchez.projectmanager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByNameContaining(String name, Pageable page);

	User findByName(String name);

	User findByEmail(String email);
}
