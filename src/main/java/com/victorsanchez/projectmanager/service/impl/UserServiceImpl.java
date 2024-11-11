package com.victorsanchez.projectmanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.exceptions.GeneralServiceException;
import com.victorsanchez.projectmanager.exceptions.NoDataFoundExeception;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;
import com.victorsanchez.projectmanager.repository.UserRepository;
import com.victorsanchez.projectmanager.service.UserService;
import com.victorsanchez.projectmanager.validator.UserValidator;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	// @Autowired
	// private PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByName(String name, Pageable page) {
		try {
			return repository.findByNameContaining(name, page);
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(int id) {
		try {
			User record = repository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No record exists with that ID"));
			return record;
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public User save(User user) {
		try {
			UserValidator.save(user);
			// user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setActive(true);
			User record = repository.save(user);
			return record;
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional()
	public User update(User user) {
		try {
			UserValidator.save(user);
			User record = repository.findById(user.getId())
					.orElseThrow(() -> new NoDataFoundExeception("No record exists with that ID"));

			validateUniqueField("name", user.getName(), record);
			validateUniqueField("email", user.getEmail(), record);

			record.setName(user.getName());
			record.setEmail(user.getEmail());
			record.setPassword(user.getPassword());
			if (user.getActive() != null) {
				record.setActive(user.getActive());
			}
			// Encripta la contraseña solo si se ha cambiado
			// if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			// record.setPassword(passwordEncoder.encode(user.getPassword()));
			// }
			repository.save(record);
			return record;
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	// En tu servicio o en una clase auxiliar
	private void validateUniqueField(String field, String value, User record) {
		// Mapa de validadores para cada campo
		Map<String, BiConsumer<String, User>> validators = new HashMap<>();

		// Validación para el nombre
		validators.put("name", (val, rec) -> {
			User existingUserByName = repository.findByName(val);
			if (existingUserByName != null && existingUserByName.getId() != rec.getId()) {
				throw new ValidateServiceException("A record with the name " + val + " already exists.");
			}
		});

		// Validación para el correo electrónico
		validators.put("email", (val, rec) -> {
			User existingUserByEmail = repository.findByEmail(val);
			if (existingUserByEmail != null && existingUserByEmail.getId() != rec.getId()) {
				throw new ValidateServiceException("A record with the email " + val + " already exists.");
			}
		});

		// Obtener el validador correspondiente al campo
		BiConsumer<String, User> validator = validators.get(field.toLowerCase());

		if (validator != null) {
			// Ejecutar la validación
			validator.accept(value, record);
		} else {
			throw new IllegalArgumentException("Unknown field: " + field);
		}
	}

	@Override
	@Transactional()
	public void delete(int id) {
		try {
			User record = repository.findById(id)
					.orElseThrow(() -> new NoDataFoundExeception("No record exists with that ID"));
			repository.delete(record);
		} catch (ValidateServiceException | NoDataFoundExeception e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
