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
public class TaskDTO {
	private Integer id;
    private String name;
    private String description;
    private String priority;
    private Date dueDate;
    private String status;
    private Integer assignedUserId;
    private String assignedUserName;
    private Integer projectId;
    private String projectName;
}
