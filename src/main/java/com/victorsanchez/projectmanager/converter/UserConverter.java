package com.victorsanchez.projectmanager.converter;

import org.springframework.stereotype.Component;

import com.victorsanchez.projectmanager.dto.UserDTO;
import com.victorsanchez.projectmanager.entity.User;

@Component
public class UserConverter extends AbstractConverter<User, UserDTO>{

	@Override
	public UserDTO fromEntity(User entity) {
		if(entity == null) return null;
		return UserDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.email(entity.getEmail())
				.password(entity.getPassword())
				.build();
	}

	@Override
	public User fromDTO(UserDTO dto) {
		if(dto == null) return null;
		return User.builder()
				.id(dto.getId())
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.build();
	}

}
