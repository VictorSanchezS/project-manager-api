package com.victorsanchez.projectmanager.validator;

import java.util.Date;

import com.victorsanchez.projectmanager.entity.Task;
import com.victorsanchez.projectmanager.exceptions.ValidateServiceException;

public class TaskValidator {
	public static void save(Task task) {
		// Validar el nombre de la tarea
		if (task.getName() == null || task.getName().isEmpty()) {
			throw new ValidateServiceException("The task name is required");
		}

		// Validar la descripción de la tarea (opcional, pero con límite de longitud)
		if (task.getDescription() != null && task.getDescription().length() > 255) {
			throw new ValidateServiceException("The task description cannot exceed 255 characters");
		}

		// Validar la fecha de vencimiento (puede ser opcional, dependiendo de la lógica
		// del negocio)
		if (task.getDueDate() == null) {
			throw new ValidateServiceException("The due date is required");
		}

		Date currentDate = new Date();
		if (task.getDueDate().before(currentDate)) {
			throw new ValidateServiceException("The due date cannot be earlier than the current date");
		}

		// Validar el estado de la tarea
		if (task.getStatus() == null || task.getStatus().isEmpty()) {
			throw new ValidateServiceException("The task status is required");
		}

		// Validar que el estado sea uno de los valores predefinidos
		String[] validStatuses = { "pending", "in_progress", "completed" };
		boolean validStatus = false;
		for (String validStatusOption : validStatuses) {
			if (validStatusOption.equals(task.getStatus())) {
				validStatus = true;
				break;
			}
		}

		if (!validStatus) {
			throw new ValidateServiceException("Invalid status. Valid options are: pending, in_progress, completed");
		}

		// Validar la prioridad de la tarea
		if (task.getPriority() == null || task.getPriority().isEmpty()) {
			throw new ValidateServiceException("The task priority is required");
		}

		// Validar que la prioridad sea uno de los valores predefinidos
		String[] validPriorities = { "low", "medium", "high" };
		boolean validPriority = false;
		for (String validPriorityOption : validPriorities) {
			if (validPriorityOption.equals(task.getPriority())) {
				validPriority = true;
				break;
			}
		}

		if (!validPriority) {
			throw new ValidateServiceException("Invalid priority. Valid options are: low, medium, high");
		}

		// Validar que la imagen (si existe) no tenga más de 255 caracteres
        if (task.getImagePath() != null) {
            if (task.getImagePath().length() > 255) {
                throw new ValidateServiceException("The image path cannot exceed 255 characters");
            }

            // Validar que la imagen tenga una extensión válida
            String[] validExtensions = { "png", "jpg", "jpeg" };
            String imagePath = task.getImagePath();
            String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();

            boolean validExtension = false;
            for (String validExt : validExtensions) {
                if (validExt.equals(fileExtension)) {
                    validExtension = true;
                    break;
                }
            }

            if (!validExtension) {
                throw new ValidateServiceException("Invalid image format. Only png, jpg, and jpeg are allowed");
            }
        }
		
		

		// Validar que la tarea tenga un usuario asignado
		if (task.getAssignedUser() == null || task.getAssignedUser().getId() == null) {
			throw new ValidateServiceException("The task must have an assigned user");
		}

		// Validar que la tarea pertenezca a un proyecto
		if (task.getProject() == null || task.getProject().getId() == null) {
			throw new ValidateServiceException("The task must belong to a project");
		}
	}
}
