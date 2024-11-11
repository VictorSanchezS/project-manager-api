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

import com.victorsanchez.projectmanager.converter.ProjectConverter;
import com.victorsanchez.projectmanager.dto.ProjectDTO;
import com.victorsanchez.projectmanager.entity.Project;
import com.victorsanchez.projectmanager.service.ProjectService;
import com.victorsanchez.projectmanager.utils.WrapperResponse;

@RestController
@RequestMapping("/v1/projects")
public class ProjectController {
	@Autowired
	private ProjectService service;
	
	@Autowired
	private ProjectConverter converter;
	
	@GetMapping()
	public ResponseEntity<List<ProjectDTO>> findAll(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Project> projects;
        if (name == null || name.isEmpty()) {
            projects = service.findAll(page);
        } else {
            projects = service.findByName(name, page);
        }
        
        List<ProjectDTO> projectsDTO = converter.fromEntity(projects);
        return new WrapperResponse(true, "success", projectsDTO).createResponse(HttpStatus.OK);
    }
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<WrapperResponse<ProjectDTO>> findById(@PathVariable("id") int id) {
        Project project = service.findById(id);
        ProjectDTO projectDTO = converter.fromEntity(project);
        return new WrapperResponse<ProjectDTO>(true, "success", projectDTO).createResponse(HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {
        Project record = service.save(converter.fromDTO(projectDTO));
        ProjectDTO recordDTO = converter.fromEntity(record);
        return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.CREATED);
    }
	
	@PutMapping(value = "/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {
        Project record = service.update(converter.fromDTO(projectDTO));
        ProjectDTO recordDTO = converter.fromEntity(record);
        return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProjectDTO> delete(@PathVariable("id") int id) {
        service.delete(id);
        return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
    }
    
    @GetMapping(value = "/created/{userId}")
    public ResponseEntity<List<ProjectDTO>> findByCreatedById(
            @PathVariable("userId") int userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Project> projects = service.findByCreatedById(userId, page);
        List<ProjectDTO> projectDTOs = converter.fromEntity(projects);
        return new WrapperResponse(true, "success", projectDTOs).createResponse(HttpStatus.OK);
    }
    
    @GetMapping(value = "/updated/{userId}")
    public ResponseEntity<List<ProjectDTO>> findByUpdatedById(
            @PathVariable("userId") int userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Project> projects = service.findByUpdatedById(userId, page);
        List<ProjectDTO> projectDTOs = converter.fromEntity(projects);
        return new WrapperResponse(true, "success", projectDTOs).createResponse(HttpStatus.OK);
    }
}
