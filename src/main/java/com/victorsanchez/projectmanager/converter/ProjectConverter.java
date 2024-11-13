package com.victorsanchez.projectmanager.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victorsanchez.projectmanager.dto.ProjectDTO;
import com.victorsanchez.projectmanager.entity.Project;
import com.victorsanchez.projectmanager.entity.User;
import com.victorsanchez.projectmanager.repository.UserRepository;

@Component
public class ProjectConverter extends AbstractConverter<Project, ProjectDTO> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public ProjectDTO fromEntity(Project entity) {
		if (entity == null)
			return null;
		
		Integer createdByUserId = entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null;
	    String createdByUserName = entity.getCreatedBy() != null ? entity.getCreatedBy().getName() : null;
	    
	    Integer updatedByUserId = entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null;
	    String updatedByUserName = entity.getUpdatedBy() != null ? entity.getUpdatedBy().getName() : null;
		
		return ProjectDTO.builder().id(entity.getId()).name(entity.getName()).description(entity.getDescription())
				.dueDate(entity.getDueDate()).status(entity.getStatus()).imagePath(entity.getImagePath())
				.createdById(entity.getCreatedBy().getId())
				.createdByName(createdByUserName)
				.updatedById(entity.getUpdatedBy().getId())
				.updatedByName(updatedByUserName)
				.build();
	}

	@Override
	public Project fromDTO(ProjectDTO dto) {
		if (dto == null)
			return null;

		// Buscar los usuarios correspondientes a los IDs
		User createdBy = null;
		if (dto.getCreatedById() != null) {
			createdBy = userRepository.findById(dto.getCreatedById())
					.orElseThrow(() -> new RuntimeException("User not found"));
		}

		User updatedBy = null;
		if (dto.getUpdatedById() != null) {
			updatedBy = userRepository.findById(dto.getUpdatedById())
					.orElseThrow(() -> new RuntimeException("User not found"));
		}

		return Project.builder().id(dto.getId()).name(dto.getName()).description(dto.getDescription())
				.dueDate(dto.getDueDate()).status(dto.getStatus()).imagePath(dto.getImagePath()).createdBy(createdBy).build();
	}

}
