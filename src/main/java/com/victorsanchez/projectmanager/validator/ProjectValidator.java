package com.victorsanchez.projectmanager.validator;

import java.util.Date;

import com.victorsanchez.projectmanager.entity.Project;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;

public class ProjectValidator {
	public static void save(Project project) {
		// Validar el nombre del proyecto
		if (project.getName() == null || project.getName().isEmpty()) {
			throw new ValidateServiceException("The project name is required");
		}

		// Validar la descripción del proyecto (opcional, pero podría ser importante
		// tener un límite de longitud)
		if (project.getDescription() != null && project.getDescription().length() > 255) {
			throw new ValidateServiceException("The project description cannot exceed 255 characters");
		}

		// Validar la fecha de vencimiento (puede ser opcional, dependiendo de la lógica
		// del negocio)
		if (project.getDueDate() == null) {
			throw new ValidateServiceException("The due date is required");
		}

		Date currentDate = new Date();
		if (project.getDueDate().before(currentDate)) {
			throw new ValidateServiceException("The due date cannot be earlier than the current date");
		}

		// Validar el estado del proyecto
		if (project.getStatus() == null || project.getStatus().isEmpty()) {
			throw new ValidateServiceException("The project status is required");
		}

		// Validar que el estado sea uno de los valores predefinidos
		String[] validStatuses = { "pending", "in_progress", "completed" };
		boolean validStatus = false;
		for (String validStatusOption : validStatuses) {
			if (validStatusOption.equals(project.getStatus())) {
				validStatus = true;
				break;
			}
		}

		if (!validStatus) {
			throw new ValidateServiceException("Invalid status. Valid options are: pending, in_progress, completed");
		}

		// Validar que la imagen (si existe) no tenga más de 255 caracteres
		if (project.getImagePath() != null && project.getImagePath().length() > 255) {
			throw new ValidateServiceException("The image path cannot exceed 255 characters");
		}
	}
}
