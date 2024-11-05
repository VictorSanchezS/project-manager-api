package com.victorsanchez.projectmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.repository.UserRepository;
import com.victorsanchez.projectmanager.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder; descomentar las dependecias de security en el build.gradle
/*
 * Crear un paquete config
 * Crear una clase dentro del paquete SecurityConfig
 * import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	
	@Configuration
	public class SecurityConfig {
	
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // Utiliza BCrypt para encriptar
	    }
	}
 * */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;
	
	//@Autowired
    //private PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByName(String name, Pageable page) {
		try {
			return repository.findByNameContaining(name, page);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(int id) {
		try {
			User record = repository.findById(id).orElseThrow();
			return record;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User save(User user) {
		try {
			//user.setPassword(passwordEncoder.encode(user.getPassword()));
			User record = repository.save(user);
			return record;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User update(User user) {
		try {
			User record = repository.findById(user.getId()).orElseThrow();
			record.setName(user.getName());
			record.setEmail(user.getEmail());
			record.setPassword(user.getPassword());
			// Encripta la contraseña solo si se ha cambiado
            //if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            //    record.setPassword(passwordEncoder.encode(user.getPassword()));
            //}
			repository.save(record);
			return record;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void delete(int id) {
		try {
			User record = repository.findById(id).orElseThrow();
			repository.delete(record);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
