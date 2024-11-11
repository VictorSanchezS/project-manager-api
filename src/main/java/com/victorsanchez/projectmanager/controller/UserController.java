	package com.victorsanchez.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorsanchez.projectmanager.converter.UserConverter;
import com.victorsanchez.projectmanager.dto.UserDTO;
import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.service.UserService;
import com.victorsanchez.projectmanager.utils.WrapperResponse;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	@Autowired
	private UserService service;
	
	@Autowired
	private UserConverter converter;

	@GetMapping()
	public ResponseEntity<List<UserDTO>> findAll(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<User> users;
		if (name == null) {
			users = service.findAll(page);
		} else {
			users = service.findByName(name, page);
		}
		
		List<UserDTO> usersDTO = converter.fromEntity(users);
		return new WrapperResponse(true, "success",usersDTO).createResponse(HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<WrapperResponse<UserDTO>> findById(@PathVariable("id") int id){
		User user = service.findById(id);
		UserDTO userDTO = converter.fromEntity(user);
		return new WrapperResponse<UserDTO>(true, "success", userDTO).createResponse(HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
		User record = service.save(converter.fromDTO(userDTO));
		UserDTO recordDTO = converter.fromEntity(record);
		return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") int id, @RequestBody UserDTO userDTO){
		User record = service.update(converter.fromDTO(userDTO));
		UserDTO recordDTO = converter.fromEntity(record);
		return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable("id") int id ){
		service.delete(id);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
	}
}
