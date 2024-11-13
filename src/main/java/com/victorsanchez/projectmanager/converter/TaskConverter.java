package com.victorsanchez.projectmanager.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsanchez.projectmanager.dto.TaskDTO;
import com.victorsanchez.projectmanager.entity.Project;
import com.victorsanchez.projectmanager.entity.Task;
import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.repository.ProjectRepository;
import com.victorsanchez.projectmanager.repository.UserRepository;

@Component
public class TaskConverter extends AbstractConverter<Task, TaskDTO> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public TaskDTO fromEntity(Task entity) {
	    if (entity == null)
	        return null;

	    Integer assignedUserId = entity.getAssignedUser() != null ? entity.getAssignedUser().getId() : null;
	    String assignedUserName = entity.getAssignedUser() != null ? entity.getAssignedUser().getName() : null;

	    Integer projectId = entity.getProject() != null ? entity.getProject().getId() : null;
	    String projectName = entity.getProject() != null ? entity.getProject().getName() : null;

	    return TaskDTO.builder()
	            .id(entity.getId())
	            .name(entity.getName())
	            .description(entity.getDescription())
	            .priority(entity.getPriority())
	            .dueDate(entity.getDueDate())
	            .status(entity.getStatus())
	            .assignedUserId(assignedUserId)    // ID del usuario asignado
	            .assignedUserName(assignedUserName) // Nombre del usuario asignado
	            .projectId(projectId)              // ID del proyecto
	            .projectName(projectName)          // Nombre del proyecto
	            .build();
	}



	@Override
	public Task fromDTO(TaskDTO dto) {
	    if (dto == null)
	        return null;

	    User assignedUser = null;
	    if (dto.getAssignedUserId() != null) {
	        // Buscar el usuario por ID
	        assignedUser = userRepository.findById(dto.getAssignedUserId())
	                .orElseThrow(() -> new RuntimeException("Assigned user not found"));
	    }

	    Project project = null;
	    if (dto.getProjectId() != null) {
	        // Buscar el proyecto por ID
	        project = projectRepository.findById(dto.getProjectId())
	                .orElseThrow(() -> new RuntimeException("Project not found"));
	    }

	    return Task.builder()
	            .id(dto.getId())
	            .name(dto.getName())
	            .description(dto.getDescription())
	            .priority(dto.getPriority())
	            .dueDate(dto.getDueDate())
	            .status(dto.getStatus())
	            .assignedUser(assignedUser)
	            .project(project)
	            .build();
	}
}
