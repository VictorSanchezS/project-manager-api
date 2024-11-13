package com.victorsanchez.projectmanager.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {
	private Integer id;
	private String name;
	private String description;
	private Date dueDate;
	private String status;
	private String imagePath;
	private Integer createdById;
	private String createdByName;
	private Integer updatedById;
    private String updatedByName;
}
