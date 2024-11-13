package com.victorsanchez.projectmanager.controller;

import java.util.Date;
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

import com.victorsanchez.projectmanager.config.ErrorHandlerConfig;
import com.victorsanchez.projectmanager.converter.TaskConverter;
import com.victorsanchez.projectmanager.dto.TaskDTO;
import com.victorsanchez.projectmanager.entity.Task;
import com.victorsanchez.projectmanager.service.TaskService;
import com.victorsanchez.projectmanager.utils.WrapperResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

	@Autowired
	private TaskService service;

	@Autowired
	private TaskConverter converter;

	@GetMapping()
	public ResponseEntity<List<TaskDTO>> findAll(
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Task> tasks = service.findAll(page);
		List<TaskDTO> taskDTOs = converter.fromEntity(tasks);
		return new WrapperResponse(true, "success", taskDTOs).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<WrapperResponse<TaskDTO>> findById(@PathVariable("id") int id) {
		Task task = service.findById(id);
		TaskDTO taskDTO = converter.fromEntity(task);
		return new WrapperResponse<TaskDTO>(true, "success", taskDTO).createResponse(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
		Task record = service.save(converter.fromDTO(taskDTO));
		TaskDTO recordDTO = converter.fromEntity(record);
		return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TaskDTO> update(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {
		Task record = service.update(converter.fromDTO(taskDTO));
		TaskDTO recordDTO = converter.fromEntity(record);
		return new WrapperResponse(true, "success", recordDTO).createResponse(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TaskDTO> delete(@PathVariable("id") int id) {
		service.delete(id);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/user/{userId}")
	public ResponseEntity<List<TaskDTO>> findByAssignedUserId(@PathVariable("userId") int userId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Task> tasks = service.findByAssignedUserId(userId, page);
		List<TaskDTO> taskDTOs = converter.fromEntity(tasks);
		return new WrapperResponse(true, "success", taskDTOs).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/project/{projectId}")
	public ResponseEntity<List<TaskDTO>> findByProjectId(@PathVariable("projectId") int projectId,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Task> tasks = service.findByProjectId(projectId, page);
		List<TaskDTO> taskDTOs = converter.fromEntity(tasks);
		return new WrapperResponse(true, "success", taskDTOs).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/due-date")
	public ResponseEntity<List<TaskDTO>> findByDueDate(@RequestParam("date") String date,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);

		Date dueDate = java.sql.Date.valueOf(date);

		List<Task> tasks = service.findByDueDate(dueDate, page);

		List<TaskDTO> taskDTOs = converter.fromEntity(tasks);
		return new WrapperResponse(true, "success", taskDTOs).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/due-date-range")
	public ResponseEntity<List<TaskDTO>> findByDueDateBetween(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		Date start = java.sql.Date.valueOf(startDate);
		Date end = java.sql.Date.valueOf(endDate);
		List<Task> tasks = service.findByDueDateBetween(start, end, page);
		List<TaskDTO> taskDTOs = converter.fromEntity(tasks);
		return new WrapperResponse(true, "success", taskDTOs).createResponse(HttpStatus.OK);
	}

}
