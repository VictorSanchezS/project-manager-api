package com.victorsanchez.projectmanager.validator;

import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;

public class UserValidator {
	public static void save(User user) {
		if(user.getName() == null || user.getName().isEmpty()) {
			throw new ValidateServiceException("The name field is required");
		}
		if(user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new ValidateServiceException("The email field is required");
		}
		if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new ValidateServiceException("The email format is invalid");
		}
		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new ValidateServiceException("The password field is required");
		}
		if(user.getPassword().length() <= 4) {
			throw new ValidateServiceException("The password field must be greater than 4 characters");
		}
	}
}
